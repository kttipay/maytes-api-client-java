/*
 * Bundled with the generated SDK via bin/postprocess-java.sh.
 *
 * Lives at build/java-client/src/main/java/co/maytes/api/webhook/WebhookVerifier.java
 * after a `make generate-java`. The published artifact (co.maytes:maytes-api-client)
 * exposes co.maytes.api.webhook.WebhookVerifier.
 *
 * Verifies the `X-Maytes-Signature` header on an inbound webhook delivery and
 * returns the parsed event. Merchants must never hand-roll this — see the four
 * sharp edges below, each of which silently produces either a security hole or
 * an outage:
 *
 *   1. RAW BODY. The signature covers the exact bytes we sent. Verifying
 *      against re-serialised JSON fails for any body whose key order or number
 *      formatting differs after a parse/emit round-trip. This is the single
 *      most common webhook integration bug.
 *   2. MULTIPLE `v1=` ENTRIES. During a signing-secret rotation grace window
 *      the header carries TWO signatures (current secret first, previous
 *      second) and the delivery is valid if EITHER matches. A verifier that
 *      reads only the first `v1=` works perfectly until the first rotation,
 *      then rejects live traffic. See §7 of the webhook spec.
 *   3. CONSTANT-TIME COMPARISON. String.equals on digests leaks how much of a
 *      forged signature was correct, which is enough to recover a valid
 *      signature one byte at a time.
 *   4. REPLAY WINDOW. The `t=` timestamp is the anti-replay anchor; a captured
 *      delivery stays valid forever without a freshness check.
 *
 * Verification and JSON parsing are deliberately fused into ONE call: the
 * method takes raw bytes and hands back the parsed event, so there is no
 * intermediate state in which a caller can parse first and verify second.
 *
 * Algorithm (webhook spec §6.1):
 *   signed_payload = "<t>.<raw body>"
 *   signature      = lowercase_hex( HMAC_SHA256(signing_secret, signed_payload) )
 */

package co.maytes.api.webhook;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class WebhookVerifier {

    /** Default anti-replay window, in seconds (webhook spec §6.1 advises ~5 min). */
    public static final int DEFAULT_TOLERANCE_SECONDS = 300;

    private static final Gson GSON = new Gson();

    private WebhookVerifier() {
    }

    /** Why a delivery failed verification. Log it; never echo it to the caller. */
    public enum Reason {
        /** Header absent, or missing its {@code t=} / {@code v1=} components. */
        MALFORMED_HEADER,
        /** {@code t=} is further from now than the tolerance allows — possible replay. */
        TIMESTAMP_OUT_OF_TOLERANCE,
        /** No {@code v1=} entry matched. Wrong secret, or the body was modified. */
        NO_MATCHING_SIGNATURE,
        /** Signature was valid but the body wasn't JSON (should never happen). */
        INVALID_JSON
    }

    /** Thrown when an inbound delivery is not authentic. Respond 400 and do NOT act on the body. */
    public static class WebhookSignatureException extends Exception {
        private static final long serialVersionUID = 1L;

        private final Reason reason;

        public WebhookSignatureException(Reason reason, String message) {
            super(message);
            this.reason = reason;
        }

        public Reason getReason() {
            return reason;
        }
    }

    /**
     * A verified webhook event (webhook spec §6).
     *
     * <p>{@code data} is left as an open map because its shape varies per
     * {@code type} — branch on {@code type}, then read the fields that event
     * documents. Ignore unrecognised fields: additive changes do NOT bump
     * {@code apiVersion}.
     */
    public static final class MaytesWebhookEvent {
        private final String id;
        private final String type;
        private final String apiVersion;
        private final String createdAt;
        private final Map<String, Object> data;

        MaytesWebhookEvent(String id, String type, String apiVersion, String createdAt,
                           Map<String, Object> data) {
            this.id = id;
            this.type = type;
            this.apiVersion = apiVersion;
            this.createdAt = createdAt;
            this.data = data;
        }

        /** Stable event id — identical across every retry. Deduplicate on this. */
        public String getId() {
            return id;
        }

        /** e.g. {@code checkout.authorized}, {@code checkout.voided}, {@code webhook.test}. */
        public String getType() {
            return type;
        }

        /** Envelope schema version, e.g. {@code 2026-06}. Bumped only on breaking changes. */
        public String getApiVersion() {
            return apiVersion;
        }

        /** When the event occurred. */
        public String getCreatedAt() {
            return createdAt;
        }

        /** Per-event payload. */
        public Map<String, Object> getData() {
            return data;
        }
    }

    /**
     * Verifies an inbound Maytes webhook delivery and returns the parsed event.
     *
     * <p>Pass the <b>raw</b> request body — the exact bytes, before any JSON parsing.
     *
     * <pre>{@code
     * byte[] rawBody = request.getInputStream().readAllBytes();
     * try {
     *     MaytesWebhookEvent event = WebhookVerifier.verify(
     *         rawBody,
     *         request.getHeader("X-Maytes-Signature"),
     *         System.getenv("MAYTES_WEBHOOK_SECRET"));
     *     // ... handle event.getType()
     *     response.setStatus(200);
     * } catch (WebhookVerifier.WebhookSignatureException e) {
     *     response.setStatus(400);
     * }
     * }</pre>
     *
     * @throws WebhookSignatureException if the delivery is not authentic.
     */
    public static MaytesWebhookEvent verify(byte[] rawBody, String signatureHeader, String signingSecret)
            throws WebhookSignatureException {
        return verify(rawBody, signatureHeader, signingSecret, DEFAULT_TOLERANCE_SECONDS);
    }

    /**
     * {@link #verify(byte[], String, String)} with an explicit anti-replay window.
     *
     * <p>{@code toleranceSeconds = 0} skips the freshness check entirely — TEST
     * ONLY, for replaying a stored delivery in a fixture. Never 0 in production:
     * it makes any captured delivery replayable forever.
     */
    public static MaytesWebhookEvent verify(byte[] rawBody, String signatureHeader, String signingSecret,
                                            int toleranceSeconds) throws WebhookSignatureException {
        String timestamp = null;
        List<String> signatures = new ArrayList<>();

        if (signatureHeader == null || signatureHeader.trim().isEmpty()) {
            throw new WebhookSignatureException(Reason.MALFORMED_HEADER,
                    "Missing X-Maytes-Signature header.");
        }

        for (String part : signatureHeader.split(",")) {
            String trimmed = part.trim();
            if (trimmed.startsWith("t=")) {
                timestamp = trimmed.substring(2);
            } else if (trimmed.startsWith("v1=")) {
                signatures.add(trimmed.substring(3));
            }
            // Unknown schemes (a future v2=) are ignored, not fatal — forward
            // compatibility: we may add a scheme alongside v1 before removing v1.
        }

        if (timestamp == null || timestamp.isEmpty() || signatures.isEmpty()) {
            throw new WebhookSignatureException(Reason.MALFORMED_HEADER,
                    "X-Maytes-Signature is malformed — expected `t=<unix>,v1=<hex>`.");
        }

        if (toleranceSeconds > 0) {
            final long sentAt;
            try {
                sentAt = Long.parseLong(timestamp);
            } catch (NumberFormatException e) {
                throw new WebhookSignatureException(Reason.MALFORMED_HEADER,
                        "X-Maytes-Signature carries a non-numeric t= value.");
            }
            long ageSeconds = Math.abs(System.currentTimeMillis() / 1000L - sentAt);
            if (ageSeconds > toleranceSeconds) {
                throw new WebhookSignatureException(Reason.TIMESTAMP_OUT_OF_TOLERANCE,
                        "Webhook timestamp is outside the " + toleranceSeconds
                                + "s tolerance — possible replay.");
            }
        }

        String expected = hmacSha256Hex(signingSecret, signedPayload(timestamp, rawBody));

        // ANY match wins — during a rotation grace window the current secret's
        // signature and the previous secret's signature are both present.
        //
        // Compared as ASCII hex text via MessageDigest.isEqual (constant-time)
        // rather than decoded bytes: a malformed v1= has no valid decoding.
        boolean matched = false;
        for (String candidate : signatures) {
            byte[] a = candidate.getBytes(StandardCharsets.UTF_8);
            byte[] b = expected.getBytes(StandardCharsets.UTF_8);
            if (MessageDigest.isEqual(a, b)) {
                matched = true;
                break;
            }
        }
        if (!matched) {
            throw new WebhookSignatureException(Reason.NO_MATCHING_SIGNATURE,
                    "No v1 signature matched the computed HMAC — wrong signing secret, "
                            + "or the body was modified in transit.");
        }

        return parseEnvelope(rawBody);
    }

    private static byte[] signedPayload(String timestamp, byte[] rawBody) {
        byte[] prefix = (timestamp + ".").getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream out = new ByteArrayOutputStream(prefix.length + rawBody.length);
        out.write(prefix, 0, prefix.length);
        out.write(rawBody, 0, rawBody.length);
        return out.toByteArray();
    }

    private static String hmacSha256Hex(String secret, byte[] payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] digest = mac.doFinal(payload);
            StringBuilder hex = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                hex.append(Character.forDigit((b >> 4) & 0xF, 16));
                hex.append(Character.forDigit(b & 0xF, 16));
            }
            return hex.toString();
        } catch (java.security.GeneralSecurityException e) {
            // HmacSHA256 is mandated by the JCA spec — unreachable on any JVM.
            throw new IllegalStateException("HmacSHA256 unavailable", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static MaytesWebhookEvent parseEnvelope(byte[] rawBody) throws WebhookSignatureException {
        try {
            JsonObject parsed = GSON.fromJson(new String(rawBody, StandardCharsets.UTF_8), JsonObject.class);
            if (parsed == null) {
                throw new JsonSyntaxException("empty body");
            }
            Map<String, Object> data = parsed.has("data") && parsed.get("data").isJsonObject()
                    ? (Map<String, Object>) GSON.fromJson(parsed.getAsJsonObject("data"), Map.class)
                    : Collections.<String, Object>emptyMap();
            return new MaytesWebhookEvent(
                    asString(parsed, "id"),
                    asString(parsed, "type"),
                    asString(parsed, "api_version"),
                    asString(parsed, "created_at"),
                    data);
        } catch (JsonSyntaxException | IllegalStateException e) {
            throw new WebhookSignatureException(Reason.INVALID_JSON,
                    "Signature verified but the body is not valid JSON.");
        }
    }

    private static String asString(JsonObject object, String field) {
        return object.has(field) && object.get(field).isJsonPrimitive()
                ? object.get(field).getAsString()
                : "";
    }
}
