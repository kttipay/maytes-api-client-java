# Maytes Java SDK

Official Java client for the [Maytes API](https://developers.maytes.co) — create,
capture and cancel checkouts, and verify inbound webhook signatures.

> **Generated package.** This repository is a build artefact, published from the
> Maytes API service definition. Pull requests here are not accepted — file
> issues instead, and fixes land upstream in the source model.

## Install

```xml
<dependency>
    <groupId>co.maytes</groupId>
    <artifactId>maytes-api-client</artifactId>
    <version>0.1.5</version>
</dependency>
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

## Gradle

```groovy
implementation "co.maytes:maytes-api-client:0.1.5"
```

Every Maytes SDK shares one version number, so the tags in this repository match
the releases of the PHP, Go, Ruby, Python, TypeScript and .NET clients.

### Verifying webhooks

Verify **before** parsing the body, and pass the raw request bytes — not a
re-encoded object. Re-serialising changes the bytes and the signature will not
match. The event id is stable across retries, so deduplicate on it.

## Documentation

Full integration docs: <https://developers.maytes.co>

## License

MIT — see [LICENSE](LICENSE).
