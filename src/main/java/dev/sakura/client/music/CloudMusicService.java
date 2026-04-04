package dev.sakura.client.music;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CloudMusicService {
    public record UserProfile(long userId, String nickname, String avatarUrl) {
    }

    public record PlaylistCard(long id, String name, String coverUrl, String creator, int trackCount) {
    }

    public record SongItem(long id, String name, String artist, String album, String coverUrl, int durationMs) {
    }

    public record PlaylistDetail(long id, String name, String coverUrl, String creator, List<SongItem> songs) {
    }

    public record QrLoginState(String key, String qrBase64, int code, String message) {
    }

    public record LyricLine(long timeMs, String text) {
    }

    public record LyricData(List<LyricLine> lyric, List<LyricLine> translation) {
    }

    public record WordFragment(long startMs, long durationMs, String text) {
    }

    public record PreciseLine(long startMs, List<WordFragment> words, String text) {
    }

    public record LyricPreciseData(List<PreciseLine> lyric, List<PreciseLine> translation) {
    }

    private static final String API_BASE = "https://music.163.com";
    private static final String PLAY_BASE = "https://interface3.music.163.com";
    private final ExecutorService executor = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r, "Sakura-CloudMusic-Service");
        thread.setDaemon(true);
        return thread;
    });
    private final Map<String, String> headers = new HashMap<>();
    private volatile String cookie = "appver=2.7.1.198277; os=pc;";
    private volatile UserProfile profile;
    private volatile QrLoginState qrLoginState;
    private final Map<Long, LyricData> lyricCache = new ConcurrentHashMap<>();
    private final Map<Long, LyricPreciseData> lyricPreciseCache = new ConcurrentHashMap<>();
    private final Path cookieStorePath = Paths.get(System.getProperty("user.home"), ".sakura_cloudmusic_cookie");

    public CloudMusicService() {
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,gl;q=0.6,zh-TW;q=0.4");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Referer", "https://music.163.com");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)");
        restoreCookieFromDisk();
    }

    public String getCookie() {
        return cookie;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public QrLoginState getQrLoginState() {
        return qrLoginState;
    }

    public CompletableFuture<QrLoginState> refreshQrCode() {
        return CompletableFuture.supplyAsync(() -> {
            JsonObject keyResult = post(API_BASE, "/api/login/qrcode/unikey", Map.of("type", "1"), false).body;
            String key = extractUnikey(keyResult);
            if (key == null || key.isBlank()) {
                throw new IllegalStateException("二维码 key 解析失败");
            }
            String qrBase64 = generateQrBase64("https://music.163.com/login?codekey=" + key, 256, 256);
            QrLoginState state = new QrLoginState(key, qrBase64, 801, "等待扫码");
            qrLoginState = state;
            return state;
        }, executor);
    }

    public CompletableFuture<QrLoginState> checkQrLogin() {
        return CompletableFuture.supplyAsync(() -> {
            QrLoginState current = qrLoginState;
            if (current == null || current.key().isBlank()) {
                throw new IllegalStateException("二维码尚未创建");
            }
            ApiResponse response = post(API_BASE, "/api/login/qrcode/client/login", Map.of(
                    "key", current.key(),
                    "type", "1",
                    "timestamp", String.valueOf(System.currentTimeMillis())
            ), false);
            JsonObject body = response.body;
            int code = body.has("code") ? body.get("code").getAsInt() : 800;
            String message = switch (code) {
                case 801 -> "等待扫码";
                case 802 -> "等待确认";
                case 803 -> "登录成功";
                case 800 -> "二维码过期";
                default -> body.has("message") ? body.get("message").getAsString() : "未知状态";
            };
            if (code == 803) {
                String bodyCookie = body.has("cookie") ? body.get("cookie").getAsString() : "";
                String headerCookie = mergeSetCookie(response.setCookies);
                String finalCookie = !bodyCookie.isBlank() ? bodyCookie : headerCookie;
                if (!finalCookie.isBlank()) {
                    cookie = "appver=2.7.1.198277; os=pc; " + finalCookie;
                    persistCookieToDisk(cookie);
                }
                refreshProfileSync();
            }
            QrLoginState state = new QrLoginState(current.key(), current.qrBase64(), code, message);
            qrLoginState = state;
            return state;
        }, executor);
    }

    public CompletableFuture<UserProfile> refreshProfile() {
        return CompletableFuture.supplyAsync(this::refreshProfileSync, executor);
    }

    public void logout() {
        cookie = "appver=2.7.1.198277; os=pc;";
        profile = null;
        try {
            Files.deleteIfExists(cookieStorePath);
        } catch (IOException ignored) {
        }
    }

    public CompletableFuture<List<PlaylistCard>> loadRecommendPlaylists() {
        return CompletableFuture.supplyAsync(() -> {
            JsonObject result = postAuthed(API_BASE, "/api/v1/discovery/recommend/resource", Map.of()).body;
            JsonArray array = result.getAsJsonArray("recommend");
            List<PlaylistCard> cards = new ArrayList<>();
            if (array == null) {
                return cards;
            }
            for (JsonElement element : array) {
                JsonObject item = element.getAsJsonObject();
                cards.add(new PlaylistCard(
                        item.get("id").getAsLong(),
                        getString(item, "name"),
                        getString(item, "picUrl"),
                        getString(item, "copywriter"),
                        0
                ));
            }
            return cards;
        }, executor);
    }

    public CompletableFuture<List<PlaylistCard>> loadUserPlaylists() {
        return CompletableFuture.supplyAsync(() -> {
            UserProfile user = ensureProfileSync();
            JsonObject result = postAuthed(API_BASE, "/api/user/playlist", Map.of(
                    "uid", String.valueOf(user.userId()),
                    "limit", "1000",
                    "offset", "0",
                    "timestamp", String.valueOf(System.currentTimeMillis())
            )).body;
            JsonArray array = result.getAsJsonArray("playlist");
            if ((array == null || array.isEmpty()) && result.has("code") && result.get("code").getAsInt() == 200) {
                result = postAuthed(API_BASE, "/api/v1/user/playlist", Map.of(
                        "uid", String.valueOf(user.userId()),
                        "limit", "1000",
                        "offset", "0",
                        "timestamp", String.valueOf(System.currentTimeMillis())
                )).body;
                array = result.getAsJsonArray("playlist");
            }
            List<PlaylistCard> cards = new ArrayList<>();
            if (array == null) {
                return cards;
            }
            for (JsonElement element : array) {
                JsonObject item = element.getAsJsonObject();
                JsonObject creatorObj = item.has("creator") ? item.getAsJsonObject("creator") : null;
                cards.add(new PlaylistCard(
                        item.get("id").getAsLong(),
                        getString(item, "name"),
                        getString(item, "coverImgUrl"),
                        creatorObj == null ? "" : getString(creatorObj, "nickname"),
                        item.has("trackCount") ? item.get("trackCount").getAsInt() : 0
                ));
            }
            return cards;
        }, executor);
    }

    public CompletableFuture<List<SongItem>> loadMyLikedSongs() {
        return CompletableFuture.supplyAsync(() -> {
            UserProfile user = ensureProfileSync();
            JsonObject result = postAuthed(API_BASE, "/api/user/playlist", Map.of("uid", String.valueOf(user.userId()))).body;
            JsonArray playlists = result.getAsJsonArray("playlist");
            if (playlists == null || playlists.isEmpty()) {
                return List.of();
            }
            long likedPlaylistId = -1L;
            for (JsonElement element : playlists) {
                JsonObject p = element.getAsJsonObject();
                if (p.has("specialType") && p.get("specialType").getAsInt() == 5) {
                    likedPlaylistId = p.get("id").getAsLong();
                    break;
                }
            }
            if (likedPlaylistId == -1L) {
                likedPlaylistId = playlists.get(0).getAsJsonObject().get("id").getAsLong();
            }
            return loadPlaylistDetail(likedPlaylistId).join().songs();
        }, executor);
    }

    public CompletableFuture<PlaylistDetail> loadPlaylistDetail(long playlistId) {
        return CompletableFuture.supplyAsync(() -> {
            JsonObject result = postAuthed(API_BASE, "/api/v6/playlist/detail", Map.of(
                    "id", String.valueOf(playlistId),
                    "n", "100000"
            )).body;
            JsonObject playlist = result.getAsJsonObject("playlist");
            if (playlist == null) {
                throw new IllegalStateException("歌单不存在");
            }
            JsonObject creatorObj = playlist.has("creator") ? playlist.getAsJsonObject("creator") : null;
            List<SongItem> songs = parseSongs(playlist.getAsJsonArray("tracks"));
            return new PlaylistDetail(
                    playlistId,
                    getString(playlist, "name"),
                    getString(playlist, "coverImgUrl"),
                    creatorObj == null ? "" : getString(creatorObj, "nickname"),
                    songs
            );
        }, executor);
    }

    public CompletableFuture<LyricData> loadLyric(long songId) {
        LyricData cached = lyricCache.get(songId);
        if (cached != null) {
            return CompletableFuture.completedFuture(cached);
        }
        return CompletableFuture.supplyAsync(() -> {
            JsonObject result = post(API_BASE, "/api/song/lyric", Map.of(
                    "id", String.valueOf(songId),
                    "tv", "-1",
                    "lv", "-1",
                    "rv", "-1",
                    "kv", "-1",
                    "_nmclfl", "1"
            ), false).body;

            String lrc = "";
            String tlyric = "";
            if (result.has("lrc") && result.get("lrc").isJsonObject()) {
                JsonObject obj = result.getAsJsonObject("lrc");
                if (obj.has("lyric") && !obj.get("lyric").isJsonNull()) {
                    lrc = obj.get("lyric").getAsString();
                }
            }
            if (result.has("tlyric") && result.get("tlyric").isJsonObject()) {
                JsonObject obj = result.getAsJsonObject("tlyric");
                if (obj.has("lyric") && !obj.get("lyric").isJsonNull()) {
                    tlyric = obj.get("lyric").getAsString();
                }
            }

            LyricData data = new LyricData(parseLrc(lrc), parseLrc(tlyric));
            lyricCache.put(songId, data);
            return data;
        }, executor);
    }

    public CompletableFuture<String> resolveSongUrl(long songId) {
        return CompletableFuture.supplyAsync(() -> resolveSongUrlSync(songId), executor);
    }

    public CompletableFuture<List<SongItem>> searchSongs(String keywords, int limit, int offset) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, String> data = new LinkedHashMap<>();
            data.put("s", Objects.toString(keywords, ""));
            data.put("type", "1");
            data.put("limit", String.valueOf(Math.max(1, limit)));
            data.put("offset", String.valueOf(Math.max(0, offset)));
            JsonObject result = post(API_BASE, "/api/search/get", data, false).body;
            JsonObject res = result.has("result") && result.get("result").isJsonObject() ? result.getAsJsonObject("result") : null;
            if (res == null) {
                return List.of();
            }
            JsonArray songs = res.getAsJsonArray("songs");
            if (songs == null || songs.isEmpty()) {
                return List.of();
            }
            return parseSongs(songs);
        }, executor);
    }

    public CompletableFuture<List<SongItem>> fillMissingCovers(List<SongItem> items) {
        return CompletableFuture.supplyAsync(() -> {
            List<SongItem> list = items == null ? List.of() : items;
            if (list.isEmpty()) return list;
            String ids = list.stream().map(it -> String.valueOf(it.id())).collect(Collectors.joining(","));
            String idsParam = "[" + ids + "]";
            JsonObject detail = post(API_BASE, "/api/song/detail", Map.of("ids", idsParam), false).body;
            if (detail != null && detail.has("songs")) {
                JsonArray arr = detail.getAsJsonArray("songs");
                Map<Long, String> covers = new HashMap<>();
                for (JsonElement e : arr) {
                    JsonObject s = e.getAsJsonObject();
                    long id = s.has("id") ? s.get("id").getAsLong() : -1L;
                    String cv = "";
                    if (s.has("al") && s.get("al").isJsonObject()) {
                        JsonObject al = s.getAsJsonObject("al");
                        cv = al.has("picUrl") ? getString(al, "picUrl") : getString(al, "blurPicUrl");
                    }
                    if ((cv == null || cv.isBlank()) && s.has("album") && s.get("album").isJsonObject()) {
                        JsonObject al = s.getAsJsonObject("album");
                        cv = al.has("picUrl") ? getString(al, "picUrl") : getString(al, "blurPicUrl");
                    }
                    if (cv != null && !cv.isBlank()) {
                        if (!cv.contains("?param=")) {
                            cv = cv + "?param=100y100";
                        }
                        covers.put(id, cv);
                    }
                }
                if (!covers.isEmpty()) {
                    List<SongItem> updated = new ArrayList<>(list.size());
                    for (SongItem it : list) {
                        if (it == null) continue;
                        String cv = covers.get(it.id());
                        if (cv != null) {
                            updated.add(new SongItem(it.id(), it.name(), it.artist(), it.album(), cv, it.durationMs()));
                        } else {
                            updated.add(it);
                        }
                    }
                    return updated;
                }
            }
            return list;
        }, executor);
    }

    public String resolveSongUrlSync(long songId) {
        JsonObject result = postAuthed(PLAY_BASE, "/api/song/enhance/player/url/v1", Map.of(
                "ids", "[" + songId + "]",
                "level", "standard",
                "encodeType", "mp3"
        )).body;
        String url = extractPlayableUrl(result);
        if (url != null && !url.isBlank()) {
            return url;
        }
        result = postAuthed(API_BASE, "/api/song/enhance/player/url/v1", Map.of(
                "ids", "[" + songId + "]",
                "level", "standard",
                "encodeType", "mp3"
        )).body;
        url = extractPlayableUrl(result);
        if (url != null && !url.isBlank()) {
            return url;
        }
        result = postAuthed(API_BASE, "/api/song/enhance/player/url", Map.of(
                "ids", "[" + songId + "]",
                "br", "192000"
        )).body;
        url = extractPlayableUrl(result);
        if (url != null && !url.isBlank()) {
            return url;
        }
        throw new IllegalStateException("当前歌曲无可用播放链接");
    }

    private List<SongItem> parseSongs(JsonArray songs) {
        List<SongItem> list = new ArrayList<>();
        if (songs == null) {
            return list;
        }
        for (JsonElement element : songs) {
            JsonObject song = element.getAsJsonObject();
            long id = song.get("id").getAsLong();
            String name = getString(song, "name");
            String artist = "";
            JsonArray artists = song.has("ar") ? song.getAsJsonArray("ar") : song.getAsJsonArray("artists");
            if (artists != null && !artists.isEmpty()) {
                List<String> names = new ArrayList<>();
                for (JsonElement ar : artists) {
                    JsonObject arObj = ar.getAsJsonObject();
                    if (arObj.has("name")) {
                        names.add(arObj.get("name").getAsString());
                    }
                }
                artist = String.join(" / ", names);
            }
            String album = "";
            String cover = "";
            JsonObject albumObj = song.has("al") ? song.getAsJsonObject("al") : song.getAsJsonObject("album");
            if (albumObj != null) {
                album = getString(albumObj, "name");
                cover = albumObj.has("picUrl") ? getString(albumObj, "picUrl") : getString(albumObj, "blurPicUrl");
                if (cover != null && !cover.isBlank() && !cover.contains("?param=")) {
                    cover = cover + "?param=100y100";
                }
            }
            int duration = song.has("dt") ? song.get("dt").getAsInt() : song.has("duration") ? song.get("duration").getAsInt() : 0;
            list.add(new SongItem(id, name, artist, album, cover, duration));
        }
        return list;
    }

    private UserProfile ensureProfileSync() {
        UserProfile current = profile;
        if (current != null) {
            return current;
        }
        return refreshProfileSync();
    }

    private UserProfile refreshProfileSync() {
        JsonObject accountResult = postAuthed(API_BASE, "/api/w/nuser/account/get", Map.of()).body;
        JsonObject profileObj = accountResult.getAsJsonObject("profile");
        if (profileObj == null || !profileObj.has("userId")) {
            throw new IllegalStateException("未登录或登录状态失效");
        }
        long uid = profileObj.get("userId").getAsLong();
        JsonObject detailResult = postAuthed(API_BASE, "/api/v1/user/detail/" + uid, Map.of()).body;
        JsonObject userProfile = detailResult.getAsJsonObject("profile");
        if (userProfile == null) {
            userProfile = profileObj;
        }
        UserProfile next = new UserProfile(
                uid,
                getString(userProfile, "nickname"),
                getString(userProfile, "avatarUrl")
        );
        profile = next;
        return next;
    }

    private ApiResponse postAuthed(String base, String path, Map<String, String> data) {
        if (cookie == null || cookie.isBlank()) {
            throw new IllegalStateException("请先完成二维码登录");
        }
        return post(base, path, data, true);
    }

    private ApiResponse post(String base, String path, Map<String, String> data, boolean withCookie) {
        HttpURLConnection connection = null;
        try {
            URL url = URI.create(base + path).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            for (Map.Entry<String, String> header : headers.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }
            connection.setRequestProperty("Host", url.getHost());
            if (withCookie && cookie != null && !cookie.isBlank()) {
                connection.setRequestProperty("Cookie", cookie);
            } else {
                connection.setRequestProperty("Cookie", "appver=2.7.1.198277; os=pc;");
            }

            String payload = buildForm(data);
            connection.getOutputStream().write(payload.getBytes(StandardCharsets.UTF_8));
            connection.getOutputStream().flush();

            int code = connection.getResponseCode();
            InputStream stream = code >= 200 && code < 300 ? connection.getInputStream() : connection.getErrorStream();
            String bodyText = stream == null ? "{}" : new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            JsonObject body;
            try {
                body = JsonParser.parseString(bodyText).getAsJsonObject();
            } catch (Exception ignored) {
                body = new JsonObject();
            }
            List<String> setCookies = extractSetCookies(connection.getHeaderFields());
            int apiCode = body.has("code") ? body.get("code").getAsInt() : code;
            if (apiCode != 200 && apiCode != 801 && apiCode != 802 && apiCode != 803) {
                String message = body.has("message") ? body.get("message").getAsString() : body.has("msg") ? body.get("msg").getAsString() : ("HTTP " + code);
                throw new IllegalStateException(message);
            }
            return new ApiResponse(body, setCookies);
        } catch (Exception e) {
            throw new IllegalStateException("请求失败: " + (e.getMessage() == null ? "未知错误" : e.getMessage()), e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void restoreCookieFromDisk() {
        try {
            if (Files.exists(cookieStorePath)) {
                String saved = Files.readString(cookieStorePath, StandardCharsets.UTF_8);
                if (saved != null && !saved.isBlank()) {
                    cookie = saved.trim();
                }
            }
        } catch (IOException ignored) {
        }
    }

    private void persistCookieToDisk(String c) {
        if (c == null || c.isBlank()) return;
        try {
            Files.writeString(cookieStorePath, c, StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
    }

    private String buildForm(Map<String, String> data) {
        if (data == null || data.isEmpty()) {
            return "";
        }
        StringJoiner joiner = new StringJoiner("&");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8);
            String value = URLEncoder.encode(Objects.toString(entry.getValue(), ""), StandardCharsets.UTF_8);
            joiner.add(key + "=" + value);
        }
        return joiner.toString();
    }

    private String getString(JsonObject json, String key) {
        if (!json.has(key) || json.get(key).isJsonNull()) {
            return "";
        }
        return json.get(key).getAsString();
    }

    private List<LyricLine> parseLrc(String raw) {
        if (raw == null || raw.isBlank()) {
            return List.of();
        }
        List<LyricLine> out = new ArrayList<>();
        String[] lines = raw.split("\n");
        for (String line : lines) {
            if (line == null) continue;
            String l = line.trim();
            if (l.isEmpty()) continue;
            if (!l.startsWith("[")) continue;
            if (l.startsWith("[by:") || l.startsWith("[ar:") || l.startsWith("[ti:") || l.startsWith("[al:") || l.startsWith("[offset:")) {
                continue;
            }

            int idx = 0;
            List<Long> times = new ArrayList<>();
            while (idx < l.length() && l.charAt(idx) == '[') {
                int end = l.indexOf(']', idx);
                if (end == -1) break;
                String tag = l.substring(idx + 1, end);
                Long t = parseTimeTagMs(tag);
                if (t != null) {
                    times.add(t);
                }
                idx = end + 1;
            }
            if (times.isEmpty()) continue;
            String text = idx < l.length() ? l.substring(idx).trim() : "";
            if (text.isEmpty()) continue;
            for (Long t : times) {
                out.add(new LyricLine(t, text));
            }
        }
        out.sort((a, b) -> Long.compare(a.timeMs(), b.timeMs()));
        List<LyricLine> dedup = new ArrayList<>();
        long lastT = Long.MIN_VALUE;
        for (LyricLine line : out) {
            if (line.timeMs() == lastT) {
                continue;
            }
            dedup.add(line);
            lastT = line.timeMs();
        }
        return dedup;
    }

    private Long parseTimeTagMs(String tag) {
        if (tag == null) return null;
        String[] parts = tag.split(":", 2);
        if (parts.length != 2) return null;
        try {
            int min = Integer.parseInt(parts[0].trim());
            String secPart = parts[1].trim();
            int sec;
            int ms = 0;
            if (secPart.contains(".")) {
                String[] sp = secPart.split("\\.", 2);
                sec = Integer.parseInt(sp[0]);
                String frac = sp[1];
                if (frac.length() == 1) ms = Integer.parseInt(frac) * 100;
                else if (frac.length() == 2) ms = Integer.parseInt(frac) * 10;
                else if (frac.length() >= 3) ms = Integer.parseInt(frac.substring(0, 3));
            } else {
                sec = Integer.parseInt(secPart);
            }
            if (min < 0 || sec < 0) return null;
            return (min * 60L + sec) * 1000L + ms;
        } catch (Exception ignored) {
            return null;
        }
    }

    private String extractUnikey(JsonObject root) {
        JsonObject node = root;
        for (int i = 0; i < 3; i++) {
            if (node == null) {
                return null;
            }
            if (node.has("unikey") && !node.get("unikey").isJsonNull()) {
                return node.get("unikey").getAsString();
            }
            if (node.has("data") && node.get("data").isJsonObject()) {
                node = node.getAsJsonObject("data");
                continue;
            }
            return null;
        }
        return null;
    }

    private String mergeSetCookie(List<String> setCookies) {
        if (setCookies == null || setCookies.isEmpty()) {
            return "";
        }
        Map<String, String> map = new LinkedHashMap<>();
        for (String line : setCookies) {
            if (line == null || line.isBlank()) {
                continue;
            }
            String[] main = line.split(";", 2);
            String[] pair = main[0].split("=", 2);
            if (pair.length == 2) {
                map.put(pair[0], pair[1]);
            }
        }
        StringJoiner joiner = new StringJoiner("; ");
        map.forEach((k, v) -> joiner.add(k + "=" + v));
        return joiner.toString();
    }

    private List<String> extractSetCookies(Map<String, List<String>> headerFields) {
        if (headerFields == null || headerFields.isEmpty()) {
            return List.of();
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            String key = entry.getKey();
            if (key != null && "Set-Cookie".equalsIgnoreCase(key) && entry.getValue() != null) {
                result.addAll(entry.getValue());
            }
        }
        return result;
    }

    private String extractPlayableUrl(JsonObject result) {
        if (result == null) {
            return null;
        }
        JsonArray data = result.getAsJsonArray("data");
        if (data == null || data.isEmpty()) {
            return null;
        }
        JsonObject first = data.get(0).getAsJsonObject();
        if (!first.has("url") || first.get("url").isJsonNull()) {
            return null;
        }
        String url = first.get("url").getAsString();
        return url == null || url.isBlank() ? null : url;
    }

    private String generateQrBase64(String content, int width, int height) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);
            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("二维码生成失败: " + e.getMessage(), e);
        }
    }

    private record ApiResponse(JsonObject body, List<String> setCookies) {
    }

    public CompletableFuture<LyricPreciseData> loadLyricPrecise(long songId) {
        LyricPreciseData cached = lyricPreciseCache.get(songId);
        if (cached != null) {
            return CompletableFuture.completedFuture(cached);
        }
        return CompletableFuture.supplyAsync(() -> {
            JsonObject result = post(API_BASE, "/api/song/lyric/v1", Map.of(
                    "id", String.valueOf(songId),
                    "cp", "false",
                    "tv", "0",
                    "lv", "0",
                    "rv", "0",
                    "kv", "0",
                    "yv", "0",
                    "ytv", "0",
                    "yrv", "0"
            ), false).body;
            String yrc = "";
            String ytlrc = "";
            if (result.has("yrc") && result.get("yrc").isJsonObject()) {
                JsonObject obj = result.getAsJsonObject("yrc");
                if (obj.has("lyric") && !obj.get("lyric").isJsonNull()) {
                    yrc = obj.get("lyric").getAsString();
                }
            }
            if (result.has("ytlrc") && result.get("ytlrc").isJsonObject()) {
                JsonObject obj = result.getAsJsonObject("ytlrc");
                if (obj.has("lyric") && !obj.get("lyric").isJsonNull()) {
                    ytlrc = obj.get("lyric").getAsString();
                }
            } else if (result.has("tlyric") && result.get("tlyric").isJsonObject()) {
                JsonObject obj = result.getAsJsonObject("tlyric");
                if (obj.has("lyric") && !obj.get("lyric").isJsonNull()) {
                    ytlrc = obj.get("lyric").getAsString();
                }
            }
            LyricPreciseData data = new LyricPreciseData(parseYrc(yrc), parseYrc(ytlrc));
            lyricPreciseCache.put(songId, data);
            return data;
        }, executor);
    }

    private List<PreciseLine> parseYrc(String raw) {
        if (raw == null || raw.isBlank()) return List.of();
        List<PreciseLine> out = new ArrayList<>();
        String[] lines = raw.split("\n");
        for (String line : lines) {
            if (line == null) continue;
            String l = line.trim();
            if (l.isEmpty()) continue;
            if (!l.startsWith("[")) continue;
            int idx = 0;
            List<Long> times = new ArrayList<>();
            while (idx < l.length() && l.charAt(idx) == '[') {
                int end = l.indexOf(']', idx);
                if (end == -1) break;
                String tag = l.substring(idx + 1, end);
                Long t = parseTimeTagMs(tag);
                if (t != null) {
                    times.add(t);
                }
                idx = end + 1;
            }
            if (times.isEmpty()) continue;
            String text = idx < l.length() ? l.substring(idx) : "";
            if (text.isEmpty()) continue;
            int p = 0;
            List<WordFragment> wordsBase = new ArrayList<>();
            Long lastStartRel = null;
            Long lastDurRel = null;
            int segmentStart = -1;
            while (p < text.length()) {
                int lt = text.indexOf('<', p);
                if (lt == -1) {
                    if (lastStartRel != null && segmentStart >= 0 && segmentStart <= text.length()) {
                        String seg = text.substring(segmentStart);
                        if (!seg.isBlank()) {
                            long dur = lastDurRel != null ? lastDurRel : 0L;
                            wordsBase.add(new WordFragment(lastStartRel, dur, seg));
                        }
                    }
                    break;
                }
                int gt = text.indexOf('>', lt + 1);
                if (gt == -1) {
                    break;
                }
                String tag = text.substring(lt + 1, gt).trim();
                Long[] sd = parseWordTag(tag);
                Long startRel = sd[0];
                Long durRel = sd[1];
                long baseAbs = times.get(0);
                if (startRel != null && startRel >= baseAbs) {
                    startRel = startRel - baseAbs;
                }
                if (lastStartRel != null && segmentStart >= 0) {
                    String seg = text.substring(segmentStart, lt);
                    if (!seg.isBlank()) {
                        long dur = lastDurRel != null ? lastDurRel : Math.max(0L, (startRel != null ? startRel : 0L) - lastStartRel);
                        wordsBase.add(new WordFragment(lastStartRel, dur, seg));
                    }
                }
                lastStartRel = startRel != null ? startRel : lastStartRel;
                lastDurRel = durRel;
                segmentStart = gt + 1;
                p = gt + 1;
            }
            // 如果整行没有任何标签，放弃逐词，交由逐行或静态渲染
            if (segmentStart == -1 && lastStartRel == null && wordsBase.isEmpty()) {
                String plainNoTag = text.replaceAll("<[^>]+>", "").trim();
                if (plainNoTag.isEmpty()) {
                    continue;
                }
                for (Long t : times) {
                    out.add(new PreciseLine(t, List.of(), plainNoTag));
                }
                continue;
            }
            String plain = text.replaceAll("<[^>]+>", "");
            if (plain.isBlank()) {
                continue;
            }
            for (Long t : times) {
                long baseAbs = times.get(0);
                long offset = t - baseAbs;
                List<WordFragment> wordsForT = new ArrayList<>();
                for (WordFragment wf : wordsBase) {
                    long ns = Math.max(0L, wf.startMs() - offset);
                    long nd = wf.durationMs();
                    wordsForT.add(new WordFragment(ns, nd, wf.text()));
                }
                out.add(new PreciseLine(t, wordsForT, plain));
            }
        }
        out.sort((a, b) -> Long.compare(a.startMs(), b.startMs()));
        return out;
    }

    private Long[] parseWordTag(String tag) {
        if (tag == null || tag.isBlank()) return new Long[]{null, null};
        try {
            if (tag.contains(":")) {
                Long abs = parseTimeTagMs(tag);
                return abs == null ? new Long[]{null, null} : new Long[]{abs, null};
            }
            if (tag.contains(",")) {
                String[] p = tag.split(",", 2);
                Long start = Long.parseLong(p[0].trim()) * 10L;
                Long dur = Long.parseLong(p[1].trim()) * 10L;
                return new Long[]{start, dur};
            }
            Long ms = Long.parseLong(tag.trim()) * 10L;
            return new Long[]{ms, null};
        } catch (Exception ignored) {
            return new Long[]{null, null};
        }
    }
}
