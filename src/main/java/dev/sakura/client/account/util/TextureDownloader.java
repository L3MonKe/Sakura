package dev.sakura.client.account.util;

import dev.sakura.client.Sakura;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static dev.sakura.client.Sakura.mc;

public final class TextureDownloader {
    private final CloseableHttpClient client = HttpClients.createDefault();

    private final Map<String, Identifier> cache = new ConcurrentHashMap<>();
    private final Set<String> downloading = new HashSet<>();

    public void downloadTexture(final String id, final String url, final boolean force) {
        if (!downloading.add(id) || cache.containsKey(id)) return;

        Sakura.EXECUTOR.execute(() -> {
            final HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(request)) {
                final InputStream stream = response.getEntity().getContent();
                final NativeImage image = NativeImage.read(stream);
                mc.execute(() -> {
                    final Identifier textureIdentifier = Identifier.of("sakura", "dynamic/" + id);
                    mc.getTextureManager().registerTexture(textureIdentifier,
                            new net.minecraft.client.texture.NativeImageBackedTexture(image));
                    cache.put(id, textureIdentifier);
                });

            } catch (IOException e) {
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
