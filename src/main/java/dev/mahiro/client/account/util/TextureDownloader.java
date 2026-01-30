package dev.mahiro.client.account.util;

import dev.mahiro.client.Mahiro;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static dev.mahiro.client.Mahiro.mc;

public final class TextureDownloader {
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(8))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private final Map<String, Identifier> cache = new ConcurrentHashMap<>();
    private final Set<String> downloading = new HashSet<>();

    public void downloadTexture(final String id, final String url, final boolean force) {
        if (!downloading.add(id) || cache.containsKey(id)) return;

        Mahiro.EXECUTOR.execute(() -> {
            final HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();
            try {
                final HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                if (response.statusCode() < 200 || response.statusCode() >= 300) {
                    throw new IOException("HTTP_" + response.statusCode());
                }
                try (ByteArrayInputStream stream = new ByteArrayInputStream(response.body())) {
                    final NativeImage image = NativeImage.read(stream);
                    mc.execute(() -> {
                        final Identifier textureIdentifier = Identifier.of("mahiro", "dynamic/" + id);
                        mc.getTextureManager().registerTexture(textureIdentifier,
                                new net.minecraft.client.texture.NativeImageBackedTexture(image));
                        cache.put(id, textureIdentifier);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();

                if (force) {
                    downloading.remove(id);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (force) {
                    downloading.remove(id);
                }
            }
        });
    }

    public void removeTexture(final String id) {
        final Identifier identifier = cache.get(id);
        if (identifier != null) {
            mc.getTextureManager().destroyTexture(identifier);
            cache.remove(id);
        }
    }

    public Identifier get(final String id) {
        return cache.get(id);
    }

    public boolean exists(final String id) {
        return cache.containsKey(id);
    }

    public boolean isDownloading(final String id) {
        return downloading.contains(id);
    }
}
