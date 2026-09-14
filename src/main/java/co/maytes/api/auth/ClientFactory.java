/*
 * Bundled with the generated SDK via bin/postprocess-java.sh.
 *
 * Lives at build/java-client/src/main/java/co/maytes/api/auth/ClientFactory.java
 * after a `make generate-java`. The published artifact (co.maytes:maytes-api-client)
 * exposes co.maytes.api.auth.ClientFactory.
 *
 * Adds OAuth2 client_credentials credential management on top of the raw
 * DefaultApi using a **reactive 401-retry** pattern:
 *
 *   1. First authed call mints a token via GetOAuthToken (a @auth([]) op) and
 *      caches it.
 *   2. Subsequent calls send the cached token as-is — no client-side expiry
 *      math, no proactive refresh.
 *   3. If the server returns 401, an OkHttp interceptor refreshes the token
 *      and replays the request once with the new Authorization header.
 *
 * Concurrent 401s deduplicate via refreshIfStale(staleToken): only the first
 * thread to take the lock mints; others see the cache already updated and use
 * that token.
 */
package co.maytes.api.auth;

import co.maytes.api.DefaultApi;
import java.util.UUID;
import co.maytes.api.invoker.ApiClient;
import co.maytes.api.invoker.ApiException;
import co.maytes.api.model.OAuthTokenRequest;
import co.maytes.api.model.OAuthTokenResponse;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public final class ClientFactory {

    private ClientFactory() {}

    public static DefaultApi create(String endpoint, String clientId, String clientSecret) {
        return createInternal(endpoint, clientId, clientSecret, null);
    }

    /**
     * SDK-test-only variant that seeds the cache with a pre-minted JWT. When
     * the seeded token expires server-side, the wrapper's reactive 401-retry
     * mints a fresh full-TTL token via /oauth/token. Production callers should
     * use the three-argument {@link #create} overload.
     */
    public static DefaultApi create(String endpoint, String clientId, String clientSecret, String initialToken) {
        return createInternal(endpoint, clientId, clientSecret, initialToken);
    }

    private static DefaultApi createInternal(String endpoint, String clientId, String clientSecret, String initialToken) {
        OAuthTokenProvider provider = new OAuthTokenProvider(endpoint, clientId, clientSecret, initialToken);

        ApiClient client = new ApiClient();
        client.setBasePath(endpoint);
        OkHttpClient httpClient = client.getHttpClient().newBuilder()
            .addInterceptor(new BearerAuthInterceptor(provider))
            .build();
        client.setHttpClient(httpClient);

        return new DefaultApi(client);
    }

    /**
     * Caches an OAuth2 client_credentials token; refreshes only on demand.
     * Threadsafe — concurrent refreshes serialize behind a ReentrantLock and
     * dedup via the stale-token snapshot.
     */
    static final class OAuthTokenProvider {
        private final String clientId;
        private final String clientSecret;
        private final DefaultApi unauthedApi;
        private final ReentrantLock lock = new ReentrantLock();

        private volatile String token;

        OAuthTokenProvider(String endpoint, String clientId, String clientSecret, String initialToken) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.token = initialToken;

            ApiClient unauthed = new ApiClient();
            unauthed.setBasePath(endpoint);
            this.unauthedApi = new DefaultApi(unauthed);
        }

        String get() {
            String t = token;
            if (t != null) return t;
            lock.lock();
            try {
                if (token != null) return token;
                return mintLocked();
            } finally {
                lock.unlock();
            }
        }

        /**
         * Mint only if the cache still holds the stale token observed by the
         * caller. If another thread refreshed first, returns that newer token.
         */
        String refreshIfStale(String staleToken) {
            lock.lock();
            try {
                if (token != null && !token.equals(staleToken)) {
                    return token;
                }
                return mintLocked();
            } finally {
                lock.unlock();
            }
        }

        private String mintLocked() {
            // `client_id` is `format: uuid` in the spec, so the generated model types it as UUID.
            OAuthTokenRequest body = new OAuthTokenRequest()
                .grantType("client_credentials")
                .clientId(UUID.fromString(clientId))
                .clientSecret(clientSecret);
            try {
                OAuthTokenResponse resp = unauthedApi.getOAuthToken(body);
                this.token = resp.getAccessToken();
                return this.token;
            } catch (ApiException e) {
                throw new RuntimeException(
                    "Failed to mint OAuth token: " + e.getCode() + " " + e.getResponseBody(), e);
            }
        }
    }

    /**
     * OkHttp interceptor that attaches Bearer auth and reactively refreshes on
     * 401. The retry replays the request once with the new token; if the retry
     * also 401s, the second response propagates.
     */
    static final class BearerAuthInterceptor implements Interceptor {
        private final OAuthTokenProvider provider;

        BearerAuthInterceptor(OAuthTokenProvider provider) {
            this.provider = provider;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            String path = original.url().encodedPath();
            if ("/api/health".equals(path) || "/oauth/token".equals(path)) {
                return chain.proceed(original);
            }

            String token = provider.get();
            Request authed = original.newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();
            Response response = chain.proceed(authed);

            if (response.code() != 401) {
                return response;
            }

            // 401 — close response body, refresh, replay once with new token.
            response.close();
            String newToken = provider.refreshIfStale(token);
            Request retried = original.newBuilder()
                .header("Authorization", "Bearer " + newToken)
                .build();
            return chain.proceed(retried);
        }
    }
}
