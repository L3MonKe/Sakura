package dev.sakura.client.gui.music;

import dev.sakura.client.music.CloudMusicService;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.utils.TranslationManager;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.List;

public class CloudMusicLoginScreen extends Screen {
    private final Screen parent;
    private final CloudMusicService service;
    private final Runnable onLoginSuccess;

    private CloudMusicService.QrLoginState qrState;
    private boolean checkingQr = false;
    private long nextQrCheckAt = 0L;
    private String statusText = TranslationManager.get("cloudmusic.status.generating_qr");
    private String errorText = "";

    private final Map<String, Integer> imageTextures = new HashMap<>();
    private final Map<String, byte[]> pendingImageBytes = new HashMap<>();
    private String qrTextureKey = "";

    private float windowW = 300f;
    private float windowH = 360f;
    private float windowX = -1;
    private float windowY = -1;

    private float openAnimation = 0f;
    private long startTime = -1;
    private boolean closing = false;
    private long closeTime = -1;

    private double lastMouseX, lastMouseY;

    private final Map<String, Rect> staticRects = new HashMap<>();

    public CloudMusicLoginScreen(Screen parent, CloudMusicService service, Runnable onLoginSuccess) {
        super(Text.literal("CloudMusicLoginScreen"));
        this.parent = parent;
        this.service = service;
        this.onLoginSuccess = onLoginSuccess;
    }

    @Override
    protected void init() {
        if (startTime == -1) {
            startTime = System.currentTimeMillis();
        }
        if (windowX == -1 || windowY == -1) {
            windowX = (width - windowW) * 0.5f;
            windowY = (height - windowH) * 0.5f;
        }
        refreshQr();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        lastMouseX = mouseX;
        lastMouseY = mouseY;

        if (parent != null) {
            parent.render(context, mouseX, mouseY, delta);
            NanoVGRenderer.INSTANCE.endBatch();
            NanoVGRenderer.INSTANCE.flushScreenQueue();
            NanoVGRenderer.INSTANCE.beginBatch();
        }

        pollQrStatus();

        float ease;
        if (closing) {
            float progress = clamp((System.currentTimeMillis() - closeTime) / 260f, 0f, 1f);
            ease = (float) (1.0f - Math.pow(progress, 3));
            if (progress >= 1.0f) {
                if (client != null) {
                    client.setScreen(parent);
                }
                return;
            }
        } else {
            long elapsed = System.currentTimeMillis() - startTime;
            openAnimation = clamp(elapsed / 320f, 0f, 1f);
            ease = (float) (1.0 - Math.pow(1.0 - openAnimation, 3));
        }

        float x = windowX;
        float y = windowY + (1.0f - ease) * 20f;
        float w = windowW;
        float h = windowH;
        float r = 16f;

        BlurShader.drawRoundedBlur(x, y, w, h, r, new Color(0, 0, 0, 0), 110f, ease);

        NanoVGRenderer.INSTANCE.draw(vg -> {
            flushPendingImages();
            NanoVG.nvgGlobalAlpha(vg, ease);

            staticRects.clear();

            float headerH = 42f;
            NanoVGHelper.drawString("Sakura", x + 20f, y + headerH * 0.5f - 6f, FontLoader.bold(), 20f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, Color.WHITE);
            NanoVGHelper.drawString("Music", x + 20f, y + headerH * 0.5f + 12f, FontLoader.regular(), 11f, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE, new Color(180, 180, 200));

            float closeSize = 28f;
            float closeX = x + w - closeSize - 12f;
            float closeY = y + 10f;
            boolean closeHover = lastMouseX >= closeX && lastMouseX <= closeX + closeSize && lastMouseY >= closeY && lastMouseY <= closeY + closeSize;
            NanoVGHelper.drawCenteredString("×", closeX + closeSize * 0.5f, closeY + closeSize * 0.5f, FontLoader.bold(), 18f, closeHover ? new Color(255, 80, 80) : Color.WHITE);
            staticRects.put("close", new Rect(closeX, closeY, closeSize, closeSize));

            float qrSize = 160f;
            float qrX = x + (w - qrSize) * 0.5f;
            float qrY = y + headerH + 20f;

            int qrTex = requestQrTexture();
            if (qrTex > 0) {
                NanoVGHelper.drawImage(qrTex, qrX, qrY, qrSize, qrSize, 10f, 1f);
            } else {
                NanoVGHelper.drawRoundRect(qrX, qrY, qrSize, qrSize, 10f, new Color(255, 255, 255, 8));
                NanoVGHelper.drawCenteredString(qrTex == 0 ? TranslationManager.get("cloudmusic.qr.loading") : TranslationManager.get("cloudmusic.qr.parse_error"), qrX + qrSize * 0.5f, qrY + qrSize * 0.5f, FontLoader.regular(), 14f, new Color(180, 180, 200));
            }

            NanoVGHelper.drawCenteredString(statusText, x + w * 0.5f, qrY + qrSize + 26f, FontLoader.regular(), 14f, new Color(180, 180, 200));

            if (errorText != null && !errorText.isBlank()) {
                NanoVGHelper.drawCenteredString(errorText, x + w * 0.5f, qrY + qrSize + 46f, FontLoader.regular(), 12f, new Color(255, 67, 94));
            }

            float btnW = 130f;
            float btnH = 34f;
            float btnX = x + (w - btnW) * 0.5f;
            float btnY = y + h - btnH - 26f;

            boolean hovered = lastMouseX >= btnX && lastMouseX <= btnX + btnW && lastMouseY >= btnY && lastMouseY <= btnY + btnH;
            NanoVGHelper.drawRoundRect(btnX, btnY, btnW, btnH, 10f, hovered ? new Color(255, 255, 255, 18) : new Color(255, 255, 255, 10));
            NanoVGHelper.drawCenteredString(TranslationManager.get("cloudmusic.qr.refresh"), btnX + btnW * 0.5f, btnY + btnH * 0.5f, FontLoader.bold(), 14f, Color.WHITE);
            staticRects.put("refresh", new Rect(btnX, btnY, btnW, btnH));

            NanoVG.nvgGlobalAlpha(vg, 1.0f);
        });
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        lastMouseX = click.x();
        lastMouseY = click.y();

        if (click.button() != 0) {
            return super.mouseClicked(click, doubled);
        }

        float mx = (float) click.x();
        float my = (float) click.y();

        if (hit("close", mx, my)) {
            close();
            return true;
        }

        if (hit("refresh", mx, my)) {
            refreshQr();
            return true;
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public void close() {
        if (!closing) {
            closing = true;
            closeTime = System.currentTimeMillis();
        }
    }

    @Override
    public void removed() {
        imageTextures.values().forEach(NanoVGHelper::deleteTexture);
        imageTextures.clear();
        pendingImageBytes.clear();
        super.removed();
    }

    private void refreshQr() {
        statusText = TranslationManager.get("cloudmusic.status.generating_qr");
        errorText = "";
        service.refreshQrCode().whenComplete((state, ex) -> {
            if (ex != null) {
                statusText = TranslationManager.get("cloudmusic.status.qr_fetch_failed");
                errorText = ex.getMessage();
                return;
            }
            qrState = state;
            qrTextureKey = "";
            statusText = state.message();
            nextQrCheckAt = System.currentTimeMillis() + 1500L;
            errorText = "";
        });
    }

    private void pollQrStatus() {
        if (qrState == null || checkingQr) {
            return;
        }
        if (System.currentTimeMillis() < nextQrCheckAt) {
            return;
        }
        if (qrState.code() == 800) {
            return;
        }
        checkingQr = true;
        service.checkQrLogin().whenComplete((state, ex) -> {
            checkingQr = false;
            nextQrCheckAt = System.currentTimeMillis() + 1200L;
            if (ex != null) {
                errorText = ex.getMessage();
                return;
            }
            qrState = state;
            statusText = state.message();
            if (state.code() == 803) {
                statusText = TranslationManager.get("cloudmusic.status.login_success");
                service.refreshProfile().thenAccept(profile -> {
                    if (client != null) {
                        client.execute(() -> {
                            if (onLoginSuccess != null) {
                                onLoginSuccess.run();
                            }
                            client.setScreen(parent);
                        });
                    }
                });
            }
            if (state.code() == 800) {
                statusText = TranslationManager.get("cloudmusic.status.qr_expired");
            }
        });
    }

    private boolean hit(String id, float mx, float my) {
        Rect rect = staticRects.get(id);
        return rect != null && rect.contains(mx, my);
    }

    private int requestQrTexture() {
        if (qrState == null || qrState.qrBase64() == null || qrState.qrBase64().isBlank()) {
            return 0;
        }
        String key = "qr:" + qrState.key();
        if (!key.equals(qrTextureKey)) {
            try {
                String b64 = qrState.qrBase64();
                if (b64.contains(",")) {
                    b64 = b64.substring(b64.indexOf(",") + 1);
                }
                byte[] bytes = Base64.getDecoder().decode(b64);
                pendingImageBytes.put(key, bytes);
                qrTextureKey = key;
            } catch (Exception e) {
                return -1;
            }
        }
        return imageTextures.getOrDefault(key, 0);
    }

    private void flushPendingImages() {
        if (pendingImageBytes.isEmpty()) {
            return;
        }
        List<String> keys = new ArrayList<>(pendingImageBytes.keySet());
        for (String key : keys) {
            byte[] bytes = pendingImageBytes.remove(key);
            if (bytes == null || bytes.length == 0) {
                continue;
            }
            ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            int image = NanoVGHelper.loadTexture(buffer);
            if (image > 0) {
                Integer old = imageTextures.put(key, image);
                if (old != null && old != image) {
                    NanoVGHelper.deleteTexture(old);
                }
            }
        }
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    private record Rect(float x, float y, float w, float h) {
        boolean contains(float mx, float my) {
            return mx >= x && mx <= x + w && my >= y && my <= y + h;
        }
    }
}
