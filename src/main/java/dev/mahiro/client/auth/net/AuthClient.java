package dev.mahiro.client.auth.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.mahiro.client.LemonClient;
import dev.mahiro.client.auth.AuthGate;
import dev.mahiro.client.auth.crypto.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public final class AuthClient {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private static final byte[] INFO = "lemon-auth-v1".getBytes(StandardCharsets.UTF_8);
    private static final String DEFAULT_BASE_URL = "http://127.0.0.1:8443";

    private final HttpClient http;
    private final String baseUrl;
    private final PublicKey serverSigningKey;

    public AuthClient() {
        String cfg = AuthGate.getServerBaseUrl();
        this.baseUrl = cfg == null || cfg.isBlank() ? DEFAULT_BASE_URL : normalizeBaseUrl(cfg);
        HttpClient.Builder b = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .followRedirects(HttpClient.Redirect.NEVER);
        SSLContext ssl = tryBuildTlsContextFromProps();
        if (ssl != null) {
            b = b.sslContext(ssl);
        }
        this.http = b.build();
        this.serverSigningKey = loadServerSigningKey(AuthGate.getServerSigningKeyX509Base64());
    }

    public CompletableFuture<AuthVerifyResult> login(String username, String password, String deviceId) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return CompletableFuture.completedFuture(AuthVerifyResult.fail("BAD_CREDENTIALS"));
        }
        if (deviceId == null || deviceId.isBlank()) {
            return CompletableFuture.completedFuture(AuthVerifyResult.fail("DEVICE_ID_EMPTY"));
        }

        return handshake()
                .thenCompose(session -> {
                    JsonObject payload = new JsonObject();
                    payload.addProperty("username", username.trim());
                    payload.addProperty("password", password);
                    payload.addProperty("deviceId", deviceId.trim());
                    payload.addProperty("clientVersion", LemonClient.MOD_VER);
                    payload.addProperty("ts", System.currentTimeMillis());

                    String plaintextJson = GSON.toJson(payload);
                    AesGcm.Encrypted enc = AesGcm.encrypt(session.key32(), plaintextJson.getBytes(StandardCharsets.UTF_8), session.aad());

                    JsonObject req = new JsonObject();
                    req.addProperty("sid", session.sessionId());
                    req.addProperty("iv", B64.enc(enc.iv()));
                    req.addProperty("ct", B64.enc(enc.ciphertext()));

                    return postJson("/api/v1/user/login", req)
                            .thenApply(resp -> decodeAuthTokenResponse(session, resp));
                })
                .exceptionally(e -> AuthVerifyResult.fail(normalizeThrowableMessage(e)));
    }

    public CompletableFuture<AuthVerifyResult> register(String username, String password, String licenseKey, String deviceId) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return CompletableFuture.completedFuture(AuthVerifyResult.fail("BAD_CREDENTIALS"));
        }
        if (licenseKey == null || licenseKey.isBlank()) {
            return CompletableFuture.completedFuture(AuthVerifyResult.fail("LICENSE_KEY_EMPTY"));
        }
        if (deviceId == null || deviceId.isBlank()) {
            return CompletableFuture.completedFuture(AuthVerifyResult.fail("DEVICE_ID_EMPTY"));
        }

        return handshake()
                .thenCompose(session -> {
                    JsonObject payload = new JsonObject();
                    payload.addProperty("username", username.trim());
                    payload.addProperty("password", password);
                    payload.addProperty("licenseKey", licenseKey.trim().toUpperCase(java.util.Locale.ROOT));
                    payload.addProperty("deviceId", deviceId.trim());
                    payload.addProperty("clientVersion", LemonClient.MOD_VER);
                    payload.addProperty("ts", System.currentTimeMillis());

                    String plaintextJson = GSON.toJson(payload);
                    AesGcm.Encrypted enc = AesGcm.encrypt(session.key32(), plaintextJson.getBytes(StandardCharsets.UTF_8), session.aad());

                    JsonObject req = new JsonObject();
                    req.addProperty("sid", session.sessionId());
                    req.addProperty("iv", B64.enc(enc.iv()));
                    req.addProperty("ct", B64.enc(enc.ciphertext()));

                    return postJson("/api/v1/user/register", req)
                            .thenApply(resp -> decodeAuthTokenResponse(session, resp));
                })
                .exceptionally(e -> AuthVerifyResult.fail(normalizeThrowableMessage(e)));
    }

    public CompletableFuture<AuthVerifyResult> verifyToken(String token, String deviceId) {
        if (token == null || token.isBlank()) {
            return CompletableFuture.completedFuture(AuthVerifyResult.fail("TOKEN_EMPTY"));
        }
        if (deviceId == null || deviceId.isBlank()) {
            return CompletableFuture.completedFuture(AuthVerifyResult.fail("DEVICE_ID_EMPTY"));
        }

        return handshake()
                .thenCompose(session -> {
                    JsonObject payload = new JsonObject();
                    payload.addProperty("token", token.trim());
                    payload.addProperty("deviceId", deviceId.trim());
                    payload.addProperty("ts", System.currentTimeMillis());

                    String plaintextJson = GSON.toJson(payload);
                    AesGcm.Encrypted enc = AesGcm.encrypt(session.key32(), plaintextJson.getBytes(StandardCharsets.UTF_8), session.aad());

                    JsonObject req = new JsonObject();
                    req.addProperty("sid", session.sessionId());
                    req.addProperty("iv", B64.enc(enc.iv()));
                    req.addProperty("ct", B64.enc(enc.ciphertext()));

                    return postJson("/api/v1/token/verify", req)
                            .thenApply(resp -> decodeTokenVerifyResponse(session, resp, token));
                })
                .exceptionally(e -> AuthVerifyResult.fail(normalizeThrowableMessage(e)));
    }

    private static SSLContext tryBuildTlsContextFromProps() {
        String keystorePath = getProp("lemon.auth.tls.keystorePath");
        String keystorePassword = getProp("lemon.auth.tls.keystorePassword");
        String truststorePath = getProp("lemon.auth.tls.truststorePath");
        String truststorePassword = getProp("lemon.auth.tls.truststorePassword");
        if (keystorePath.isBlank() || keystorePassword.isBlank() || truststorePath.isBlank() || truststorePassword.isBlank()) {
            return null;
        }
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            try (FileInputStream in = new FileInputStream(keystorePath)) {
                ks.load(in, keystorePassword.toCharArray());
            }
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, keystorePassword.toCharArray());

            KeyStore ts = KeyStore.getInstance("PKCS12");
            try (FileInputStream in = new FileInputStream(truststorePath)) {
                ts.load(in, truststorePassword.toCharArray());
            }
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ts);

            SSLContext ctx = SSLContext.getInstance("TLSv1.3");
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
            return ctx;
        } catch (Exception ignored) {
            return null;
        }
    }

    private static String getProp(String name) {
        String v = System.getProperty(name);
        if (v == null || v.isBlank()) v = System.getenv(name.toUpperCase().replace('.', '_'));
        return v == null ? "" : v.trim();
    }

    private AuthVerifyResult decodeTokenVerifyResponse(SecureSession session, JsonObject resp, String token) {
        try {
            if (resp == null) return AuthVerifyResult.fail("EMPTY_RESPONSE");
            if (resp.has("error")) return AuthVerifyResult.fail(resp.get("error").getAsString());
            if (!resp.has("iv") || !resp.has("ct")) return AuthVerifyResult.fail("INVALID_RESPONSE");

            byte[] iv = B64.dec(resp.get("iv").getAsString());
            byte[] ct = B64.dec(resp.get("ct").getAsString());
            byte[] pt = AesGcm.decrypt(session.key32(), iv, ct, session.aad());
            String json = new String(pt, StandardCharsets.UTF_8);
            JsonObject obj = GSON.fromJson(json, JsonObject.class);

            boolean ok = obj != null && obj.has("ok") && obj.get("ok").getAsBoolean();
            if (!ok) {
                String err = obj != null && obj.has("error") ? obj.get("error").getAsString() : "DENIED";
                return AuthVerifyResult.fail(err);
            }
            return AuthVerifyResult.ok(token);
        } catch (Exception e) {
            return AuthVerifyResult.fail("DECODE_FAILED");
        }
    }

    private static String normalizeThrowableMessage(Throwable e) {
        Throwable t = e;
        if (t instanceof CompletionException && t.getCause() != null) t = t.getCause();
        String msg = t.getMessage();
        if (msg != null && !msg.isBlank()) return msg;
        return t.getClass().getSimpleName();
    }

    private AuthVerifyResult decodeAuthTokenResponse(SecureSession session, JsonObject resp) {
        try {
            if (resp == null) return AuthVerifyResult.fail("EMPTY_RESPONSE");
            if (resp.has("error")) return AuthVerifyResult.fail(resp.get("error").getAsString());
            if (!resp.has("iv") || !resp.has("ct")) return AuthVerifyResult.fail("INVALID_RESPONSE");

            byte[] iv = B64.dec(resp.get("iv").getAsString());
            byte[] ct = B64.dec(resp.get("ct").getAsString());
            byte[] pt = AesGcm.decrypt(session.key32(), iv, ct, session.aad());
            String json = new String(pt, StandardCharsets.UTF_8);
            JsonObject obj = GSON.fromJson(json, JsonObject.class);

            boolean ok = obj != null && obj.has("ok") && obj.get("ok").getAsBoolean();
            if (!ok) {
                String err = obj != null && obj.has("error") ? obj.get("error").getAsString() : "DENIED";
                return AuthVerifyResult.fail(err);
            }
            String token = obj.has("token") ? obj.get("token").getAsString() : "";
            if (token == null || token.isBlank()) return AuthVerifyResult.fail("NO_TOKEN");
            return AuthVerifyResult.ok(token);
        } catch (Exception e) {
            return AuthVerifyResult.fail("DECODE_FAILED");
        }
    }

    private CompletableFuture<SecureSession> handshake() {
        KeyPair clientEphemeral = X25519.generate();
        byte[] nonce = Bytes.random(32);

        JsonObject req = new JsonObject();
        req.addProperty("clientEphemeralPub", B64.enc(clientEphemeral.getPublic().getEncoded()));
        req.addProperty("nonce", B64.enc(nonce));
        req.addProperty("v", "v1");

        return postJson("/api/v1/handshake", req).thenApply(resp -> {
            if (resp == null) throw new IllegalStateException("EMPTY_RESPONSE");
            String sid = resp.has("sid") ? resp.get("sid").getAsString() : "";
            String serverEphemeralPubB64 = resp.has("serverEphemeralPub") ? resp.get("serverEphemeralPub").getAsString() : "";
            String sigB64 = resp.has("sig") ? resp.get("sig").getAsString() : "";
            String nonceEchoB64 = resp.has("nonce") ? resp.get("nonce").getAsString() : "";

            if (sid.isBlank() || serverEphemeralPubB64.isBlank() || sigB64.isBlank() || nonceEchoB64.isBlank()) {
                throw new IllegalStateException("INVALID_RESPONSE");
            }

            byte[] serverPubX509 = B64.dec(serverEphemeralPubB64);
            byte[] sig = B64.dec(sigB64);
            byte[] nonceEcho = B64.dec(nonceEchoB64);

            if (!Objects.equals(B64.enc(nonce), B64.enc(nonceEcho))) {
                throw new IllegalStateException("NONCE_MISMATCH");
            }

            byte[] transcript = Bytes.concat(
                    clientEphemeral.getPublic().getEncoded(),
                    serverPubX509,
                    nonce
            );

            if (serverSigningKey != null) {
                boolean ok = Ed25519.verify(serverSigningKey, transcript, sig);
                if (!ok) throw new IllegalStateException("BAD_SIGNATURE");
            }

            PublicKey serverEphemeral = X25519.decodePublicX509(serverPubX509);
            byte[] shared = X25519.agree(clientEphemeral.getPrivate(), serverEphemeral);
            byte[] key32 = HkdfSha256.deriveKey(shared, nonce, INFO, 32);
            byte[] aad = Bytes.concat("sid=".getBytes(StandardCharsets.UTF_8), sid.getBytes(StandardCharsets.UTF_8));

            return new SecureSession(sid, key32, aad);
        });
    }

    private CompletableFuture<JsonObject> postJson(String path, JsonObject json) {
        String url = baseUrl + path;
        HttpRequest req = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(8))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(json)))
                .build();

        return http.sendAsync(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
                .thenApply(resp -> {
                    if (resp.statusCode() < 200 || resp.statusCode() >= 300) {
                        throw new IllegalStateException("HTTP_" + resp.statusCode());
                    }
                    return GSON.fromJson(resp.body(), JsonObject.class);
                });
    }

    private static String normalizeBaseUrl(String baseUrl) {
        String v = baseUrl == null ? "" : baseUrl.trim();
        if (v.endsWith("/")) v = v.substring(0, v.length() - 1);
        return v;
    }

    private static PublicKey loadServerSigningKey(String x509B64) {
        if (x509B64 == null || x509B64.isBlank()) return null;
        try {
            byte[] x509 = B64.dec(x509B64.trim());
            return Ed25519.decodePublicX509(x509);
        } catch (Exception ignored) {
            return null;
        }
    }
}
