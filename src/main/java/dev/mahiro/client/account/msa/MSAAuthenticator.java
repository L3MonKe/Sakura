package dev.mahiro.client.account.msa;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dev.mahiro.client.Mahiro;
import dev.mahiro.client.account.msa.callback.BrowserLoginCallback;
import dev.mahiro.client.account.msa.exception.MSAAuthException;
import dev.mahiro.client.account.msa.model.MinecraftProfile;
import dev.mahiro.client.account.msa.model.OAuthResult;
import dev.mahiro.client.account.msa.model.XboxLiveData;
import dev.mahiro.client.account.msa.security.PKCEData;
import net.minecraft.client.session.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class MSAAuthenticator {
    private static final Logger LOGGER = LogManager.getLogger("MSA-Authenticator");
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(8))
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    private static final String CLIENT_ID = "d1bbd256-3323-4ab7-940e-e8a952ebdb83";
    private static final int PORT = 6969;

    private static final String REAL_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:107.0) Gecko/20100101 Firefox/107.0";
    private static final String OAUTH_AUTH_DESKTOP_URL = "https://login.live.com/oauth20_authorize.srf?client_id=000000004C12AE6F&redirect_uri=https://login.live.com/oauth20_desktop.srf&scope=service::user.auth.xboxlive.com::MBI_SSL&display=touch&response_type=token&locale=en";
    private static final String OAUTH_AUTHORIZE_URL = "https://login.live.com/oauth20_authorize.srf?response_type=code&client_id=%s&redirect_uri=http://localhost:%s/login&code_challenge=%s&code_challenge_method=S256&scope=XboxLive.signin+offline_access&state=NOT_NEEDED&prompt=select_account";
    private static final String OAUTH_TOKEN_URL = "https://login.live.com/oauth20_token.srf";
    private static final String XBOX_LIVE_AUTH_URL = "https://user.auth.xboxlive.com/user/authenticate";
    private static final String XBOX_XSTS_AUTH_URL = "https://xsts.auth.xboxlive.com/xsts/authorize";
    private static final String LOGIN_WITH_XBOX_URL = "https://api.minecraftservices.com/authentication/login_with_xbox";
    private static final String MINECRAFT_PROFILE_URL = "https://api.minecraftservices.com/minecraft/profile";
    private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private static final Pattern SFTT_TAG_PATTERN = Pattern.compile("value=\"(.+?)\"");
    private static final Pattern POST_URL_PATTERN = Pattern.compile("urlPost:'(.+?)'");
    private static final int MAX_REDIRECTS = 5;

    private HttpServer localServer;
    private String loginStage = "";
    private boolean serverOpen;

    private PKCEData pkceData;

    public Session loginWithCredentials(final String email, final String password) throws MSAAuthException {
        final OAuthResult result = getOAuth();
        if (result.getPostUrl() == null || result.getSfttTag() == null) {
            throw new MSAAuthException("Failed to retrieve SFTT tag & Post URL");
        }
        final String token = getOAuthLoginData(result, email, password);
        return loginWithToken(token, false);
    }

    public void loginWithBrowser(final BrowserLoginCallback callback)
            throws IOException, URISyntaxException, MSAAuthException {
        if (!serverOpen || localServer == null) {
            // TODO: 如果一分钟左右没有交互，自动关闭服务器
            localServer = HttpServer.create();
            localServer.createContext("/login", (ctx) ->
            {
                setLoginStage("Parsing access token from response");
                final Map<String, String> query = parseQueryString(ctx.getRequestURI().getQuery());

                if (query.containsKey("error")) {
                    final String errorDescription = query.get("error_description");
                    if (errorDescription != null && !errorDescription.isEmpty()) {
                        LOGGER.error("Failed to get token from browser login: {}", errorDescription);
                        writeToWebpage("Failed to get token: " + errorDescription, ctx);
                        setLoginStage(errorDescription);
                    }
                } else {
                    final String code = query.get("code");
                    if (code != null) {
                        callback.callback(code);
                        writeToWebpage("Successfully got code. You may now close this window", ctx);
                    } else {
                        writeToWebpage("Failed to get code. Please try again.", ctx);
                    }
                }
                serverOpen = false;
                localServer.stop(0);
            });
        }

        pkceData = generateKeys();
        if (pkceData == null) {
            throw new MSAAuthException("Failed to generate PKCE keys");
        }

        final String url = String.format(OAUTH_AUTHORIZE_URL, CLIENT_ID, PORT, pkceData.challenge());
        if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
            setLoginStage("Waiting user response...");
        } else {
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(url), null);
            LOGGER.warn("BROWSE action not supported on Desktop Environment, copied to clipboard instead.");
            setLoginStage("Link copied to clipboard!");
        }

        if (!serverOpen) {
            localServer.bind(new InetSocketAddress(PORT), 1);
            localServer.start();
            serverOpen = true;
        }
    }

    public Session loginWithToken(final String token, final boolean browser) throws MSAAuthException {
        setLoginStage("Logging in with Xbox Live...");
        final XboxLiveData data = authWithXboxLive(token, browser);
        requestTokenFromXboxLive(data);
        final String accessToken = loginWithXboxLive(data);
        setLoginStage("Fetching MC profile...");
        final MinecraftProfile profile = fetchMinecraftProfile(accessToken);
        pkceData = null;
        return new Session(profile.username(), parseUuid(profile.id()), accessToken, Optional.empty(), Optional.empty(), Session.AccountType.MSA);
    }

    public String getLoginToken(final String oauthToken) throws MSAAuthException {
        final String body = makeQueryString(new String[][]{
                new String[]{"client_id", CLIENT_ID},
                new String[]{"code_verifier", pkceData.verifier()},
                new String[]{"code", oauthToken},
                new String[]{"grant_type", "authorization_code"},
                new String[]{"redirect_uri", "http://localhost:" + PORT + "/login"}
        });
        try {
            final HttpRequest request = HttpRequest.newBuilder(URI.create(OAUTH_TOKEN_URL))
                    .timeout(Duration.ofSeconds(8))
                    .header("Content-Type", CONTENT_TYPE_FORM)
                    .header("Accept", CONTENT_TYPE_JSON)
                    .header("Origin", "http://localhost:" + PORT + "/")
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();
            final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            final String content = response.body();
            if (content == null || content.isEmpty()) {
                throw new MSAAuthException("Failed to get login token from MSA OAuth");
            }
            final JsonObject obj = JsonParser.parseString(content).getAsJsonObject();
            if (obj.has("error")) {
                throw new MSAAuthException(obj.get("error").getAsString() + ": " + obj.get("error_description").getAsString());
            }
            return obj.get("access_token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MSAAuthException("Failed to get login token");
        }
    }

    private OAuthResult getOAuth() throws MSAAuthException {
        try {
            final OAuthResult result = new OAuthResult();
            final Map<String, String> cookieJar = new LinkedHashMap<>();
            URI current = URI.create(OAUTH_AUTH_DESKTOP_URL);
            for (int i = 0; i < MAX_REDIRECTS; i++) {
                final HttpRequest request = HttpRequest.newBuilder(current)
                        .timeout(Duration.ofSeconds(8))
                        .header("User-Agent", REAL_USER_AGENT)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                        .header("Cookie", formatCookies(cookieJar))
                        .GET()
                        .build();
                final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
                absorbCookies(response.headers().allValues("Set-Cookie"), cookieJar);

                if (isRedirect(response.statusCode())) {
                    final Optional<String> location = response.headers().firstValue("Location");
                    if (location.isPresent()) {
                        current = current.resolve(location.get());
                        continue;
                    }
                }

                final String content = response.body();
                Matcher matcher = SFTT_TAG_PATTERN.matcher(content);
                if (matcher.find()) {
                    result.setSfttTag(matcher.group(1));
                }
                if ((matcher = POST_URL_PATTERN.matcher(content)).find()) {
                    result.setPostUrl(matcher.group(1));
                }
                result.setCookie(formatCookies(cookieJar));
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new MSAAuthException("Failed to login with email & password.");
    }

    @SuppressWarnings("deprecation")
    private String getOAuthLoginData(final OAuthResult result, final String email, final String password) throws MSAAuthException {
        final String contentTypeRaw = CONTENT_TYPE_FORM;

        String encodedEmail = URLEncoder.encode(email);
        String encodedPassword = URLEncoder.encode(password);
        final String body = makeQueryString(new String[][]{
                new String[]{"login", encodedEmail},
                new String[]{"loginfmt", encodedEmail},
                new String[]{"passwd", encodedPassword},
                new String[]{"PPFT", result.getSfttTag()}
        });

        try {
            final HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(result.getPostUrl()))
                    .timeout(Duration.ofSeconds(8))
                    .header("Content-Type", contentTypeRaw)
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8));
            if (result.getCookie() != null && !result.getCookie().isBlank()) {
                builder.header("Cookie", result.getCookie());
            }
            final HttpResponse<String> response = HTTP_CLIENT.send(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (isRedirect(response.statusCode())) {
                final Optional<String> location = response.headers().firstValue("Location");
                if (location.isPresent()) {
                    final String accessToken = extractAccessToken(location.get());
                    if (accessToken != null) {
                        return accessToken;
                    }
                }
            }

            final String content = response.body();
            if (content != null && !content.isEmpty()) {
                if (content.contains("Sign in to")) {
                    throw new MSAAuthException("The provided credentials were incorrect");
                } else if (content.contains("Help us protect your account")) {
                    throw new MSAAuthException("2FA has been enabled on this account");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new MSAAuthException("Failed to get access token");
    }

    private XboxLiveData authWithXboxLive(final String accessToken, final boolean browser) throws MSAAuthException {
        final String body = "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\""
                + (browser ? "d=" : "") + accessToken + "\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}";
        final String content = makePostRequest(XBOX_LIVE_AUTH_URL, body, CONTENT_TYPE_JSON);
        if (content != null && !content.isEmpty()) {
            final JsonObject object = JsonParser.parseString(content).getAsJsonObject();

            final XboxLiveData data = new XboxLiveData();
            data.setToken(object.get("Token").getAsString());
            data.setUserHash(object.get("DisplayClaims").getAsJsonObject()
                    .get("xui").getAsJsonArray()
                    .get(0).getAsJsonObject()
                    .get("uhs").getAsString());

            return data;
        }
        throw new MSAAuthException("Failed to authenticate with Xbox Live account");
    }

    private void requestTokenFromXboxLive(XboxLiveData xboxLiveData) throws MSAAuthException {
        final String body = "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\""
                + xboxLiveData.getToken() + "\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}";
        final String content = makePostRequest(XBOX_XSTS_AUTH_URL, body, CONTENT_TYPE_JSON);
        if (content != null && !content.isEmpty()) {
            final JsonObject object = JsonParser.parseString(content).getAsJsonObject();
            if (object.has("XErr")) {
                throw new MSAAuthException("Xbox Live Error: " + object.get("XErr").getAsString());
            } else {
                xboxLiveData.setToken(object.get("Token").getAsString());
            }
        }
    }

    private String loginWithXboxLive(final XboxLiveData data) throws MSAAuthException {
        try {
            final String body = "{\"ensureLegacyEnabled\":true,\"identityToken\":\"XBL3.0 x=" + data.getUserHash() + ";" + data.getToken() + "\"}";
            final String content = makePostRequest(LOGIN_WITH_XBOX_URL, body, CONTENT_TYPE_JSON);
            if (content != null && !content.isEmpty()) {
                final JsonObject object = JsonParser.parseString(content).getAsJsonObject();
                if (object.has("errorMessage")) {
                    throw new MSAAuthException(object.get("errorMessage").getAsString());
                }
                if (object.has("access_token")) {
                    return object.get("access_token").getAsString();
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MinecraftProfile fetchMinecraftProfile(final String accessToken) throws MSAAuthException {
        try {
            final HttpRequest request = HttpRequest.newBuilder(URI.create(MINECRAFT_PROFILE_URL))
                    .timeout(Duration.ofSeconds(8))
                    .header("Accept", CONTENT_TYPE_JSON)
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();
            final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                throw new MSAAuthException("Failed to fetch MC profile: Status code != 200, sc=" + response.statusCode());
            }
            final String rawJSON = response.body();
            final JsonObject object = JsonParser.parseString(rawJSON).getAsJsonObject();
            if (object.has("error")) {
                throw new MSAAuthException("Failed to fetch MC profile: " + object.get("error").getAsString() + " -> " + object.get("errorMessage").getAsString());
            }
            return new MinecraftProfile(object.get("name").getAsString(),
                    object.get("id").getAsString());
        } catch (Exception e) {
            throw new MSAAuthException(e.getMessage());
        }
    }

    private String makePostRequest(final String url, final String body, final String contentType) {
        try {
            final HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(8))
                    .header("Content-Type", contentType)
                    .header("Accept", CONTENT_TYPE_JSON)
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();
            final HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                return null;
            }
            return response.body();
        } catch (Exception e) {
            Mahiro.LOGGER.error("Failed to make POST request to {}", url);
            e.printStackTrace();
        }
        return null;
    }

    private void writeToWebpage(final String message, final HttpExchange ext) throws IOException {
        final byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        ext.sendResponseHeaders(200, message.length());
        // 写入输出流（这将允许用户看到消息）
        final OutputStream outputStream = ext.getResponseBody();
        outputStream.write(bytes, 0, bytes.length);
        outputStream.close();
    }

    private String makeQueryString(final String[][] parameters) {
        final StringJoiner joiner = new StringJoiner("&");
        for (final String[] parameter : parameters) {
            joiner.add(parameter[0] + "=" + parameter[1]);
        }
        return joiner.toString();
    }

    private Map<String, String> parseQueryString(final String query) {
        final Map<String, String> parameterMap = new LinkedHashMap<>();
        for (final String part : query.split("&")) {
            final String[] kv = part.split("=");
            parameterMap.put(kv[0], kv.length == 1 ? null : kv[1]);
        }
        return parameterMap;
    }

    private PKCEData generateKeys() {
        try {
            final byte[] randomBytes = new byte[32];
            new SecureRandom().nextBytes(randomBytes);

            final String verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
            final byte[] verifierBytes = verifier.getBytes(StandardCharsets.US_ASCII);
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(verifierBytes, 0, verifierBytes.length);

            final byte[] d = digest.digest();
            final String challenge = Base64.getUrlEncoder().withoutPadding().encodeToString(d);
            return new PKCEData(challenge, verifier);
        } catch (Exception ignored) {
        }
        return null;
    }

    public void setLoginStage(String loginStage) {
        this.loginStage = loginStage;
    }

    public String getLoginStage() {
        return loginStage;
    }

    private static UUID parseUuid(final String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        if (value.length() == 32) {
            final String withDashes = value.substring(0, 8) + "-" +
                    value.substring(8, 12) + "-" +
                    value.substring(12, 16) + "-" +
                    value.substring(16, 20) + "-" +
                    value.substring(20);
            return UUID.fromString(withDashes);
        }
        return UUID.fromString(value);
    }

    private static boolean isRedirect(final int status) {
        return status == 301 || status == 302 || status == 303 || status == 307 || status == 308;
    }

    private static String extractAccessToken(final String location) {
        try {
            final URI uri = URI.create(location);
            final String fragment = uri.getFragment();
            if (fragment == null || fragment.isBlank()) {
                return null;
            }
            for (final String param : fragment.split("&")) {
                final String[] parameter = param.split("=");
                if (parameter.length == 2 && parameter[0].equals("access_token")) {
                    return parameter[1];
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static void absorbCookies(final List<String> setCookies, final Map<String, String> cookieJar) {
        for (final String setCookie : setCookies) {
            final String[] parts = setCookie.split(";", 2);
            final String[] kv = parts[0].split("=", 2);
            if (kv.length == 2) {
                cookieJar.put(kv[0], kv[1]);
            }
        }
    }

    private static String formatCookies(final Map<String, String> cookieJar) {
        if (cookieJar.isEmpty()) {
            return "";
        }
        return cookieJar.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("; "));
    }
}
