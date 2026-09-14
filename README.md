# Maytes Java SDK

Official Java client for the [Maytes API](https://developers.maytes.co) — create,
capture and cancel checkouts, and verify inbound webhook signatures.

> **Generated package.** This repository is a build artefact, published from the
> Maytes API service definition. Pull requests here are not accepted — file
> issues instead, and fixes land upstream in the source model.

## Install

```bash
Not published to a registry — see below.
```

## Usage

The client mints an OAuth2 `client_credentials` token on first use, caches it,
and — on a `401` — refreshes and retries the request once. There is no
client-side expiry maths to get wrong, and no proactive refresh to schedule.

```java
import co.maytes.api.DefaultApi;
import co.maytes.api.auth.ClientFactory;
import co.maytes.api.webhook.WebhookVerifier;

DefaultApi api = ClientFactory.create(endpoint, clientId, clientSecret);
CreateCheckoutResponse created = api.createCheckout(request);

WebhookVerifier.MaytesWebhookEvent event =
    WebhookVerifier.verify(rawBody, signatureHeader, signingSecret);
```

## Installing

This artifact is **not on Maven Central**. Publishing there needs a Sonatype
namespace verification for `co.maytes` (a DNS TXT record on the domain, or a
move to `io.github.kttipay`) plus GPG-signed artifacts, none of which is in
place yet.

Until then, consume it from source — clone at the tag you want and
`mvn install`, or point a JitPack dependency at this repository. The tags here
are the same version numbers the whole Maytes SDK family shares.'

### Verifying webhooks

Verify **before** parsing the body, and pass the raw request bytes — not a
re-encoded object. Re-serialising changes the bytes and the signature will not
match. The event id is stable across retries, so deduplicate on it.

## Documentation

Full integration docs: <https://developers.maytes.co>

## License

MIT — see [LICENSE](LICENSE).
