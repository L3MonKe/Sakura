package dev.sakura.client.module.impl.hud;

import dev.sakura.client.module.HudModule;
import dev.sakura.client.module.impl.client.ClickGui;
import dev.sakura.client.module.impl.client.CloudMusicGui;
import dev.sakura.client.music.CloudMusicPlayer;
import dev.sakura.client.music.CloudMusicService;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.shaders.BlurShader;
import dev.sakura.client.shaders.ShadowShader;
import dev.sakura.client.values.Value;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;
import java.util.List;

public class LyricsHud extends HudModule {
    private final Value<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 3.0, 0.1);
    private final Value<Double> maxWidth = new NumberValue<>("MaxWidth", "最大宽度", 280.0, 120.0, 700.0, 10.0);
    private final Value<Boolean> background = new BoolValue("Background", "背景", true);
    private final Value<Color> backgroundColor = new ColorValue("BackgroundColor", "背景颜色", new Color(0, 0, 0, 120), background::get);
    private final Value<Double> radius = new NumberValue<>("Radius", "圆角", 10.0, 0.0, 30.0, 0.5, background::get);
    private final Value<Boolean> blur = new BoolValue("Blur", "模糊", true, background::get);
    private final Value<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 30.0, 0.5, () -> background.get() && blur.get());
    private final Value<Boolean> shadow = new BoolValue("Shadow", "阴影", true, background::get);
    private final Value<Double> shadowRange = new NumberValue<>("ShadowRange", "阴影范围", 10.0, 0.0, 30.0, 0.5, () -> background.get() && shadow.get());
    private final Value<Double> shadowStrength = new NumberValue<>("ShadowStrength", "阴影强度", 0.6, 0.0, 1.0, 0.01, () -> background.get() && shadow.get());

    private volatile long currentSongId = -1L;
    private volatile CloudMusicService.LyricData lyricData;
    private volatile boolean loading = false;
    private float animatedW = -1f;
    private float animatedH = -1f;
    private float anchorCenterX = Float.NaN;

    public LyricsHud() {
        super("Lyrics", "歌词", 10, 120);
        setInitialState(false);
    }

    @Override
    public void onRender(net.minecraft.client.gui.DrawContext context) {
        CloudMusicPlayer player = CloudMusicGui.getPlayer();
        CloudMusicService service = CloudMusicGui.getService();
        CloudMusicService.SongItem song = player.getCurrentSong();
        if (song == null) {
            return;
        }

        long songId = song.id();
        if (songId != currentSongId) {
            currentSongId = songId;
            lyricData = null;
            loading = false;
        }

        if (lyricData == null && !loading) {
            loading = true;
            service.loadLyric(songId).whenComplete((data, ex) -> {
                if (songId != currentSongId) {
                    return;
                }
                if (ex == null) {
                    lyricData = data;
                }
                loading = false;
            });
        }

        CloudMusicService.LyricData data = lyricData;
        if (data == null || data.lyric() == null || data.lyric().isEmpty()) {
            return;
        }

        long posMs = Math.max(0L, player.getPositionMs());
        CloudMusicService.LyricLine mainLine = findLine(data.lyric(), posMs);
        if (mainLine == null || mainLine.text() == null || mainLine.text().isBlank()) {
            return;
        }
        String main = mainLine.text();

        CloudMusicService.LyricLine transLine = findNearestLine(data.translation(), mainLine.timeMs(), 3000L);
        String trans = transLine == null ? null : transLine.text();
        boolean showTrans = trans != null && !trans.isBlank() && !trans.equals(main) && (containsKana(main) || containsLatin(main) || !containsChinese(main));

        float s = hudScale.get().floatValue();
        float padX = 10f * s;
        float padY = 8f * s;
        float r = radius.get().floatValue() * s;

        float mainSize = 14f * s;
        float transSize = 11f * s;
        int mainFont = FontLoader.bold();
        int transFont = FontLoader.regular();

        float mainH = NanoVGHelper.getFontHeight(mainFont, mainSize);
        float transH = NanoVGHelper.getFontHeight(transFont, transSize);
        float targetH = padY * 2f + mainH + (showTrans ? (3f * s + transH) : 0f);

        float maxW = maxWidth.get().floatValue() * s;
        float minW = 120f * s;
        float mainW = NanoVGHelper.getTextWidth(main, mainFont, mainSize);
        float transW = showTrans ? NanoVGHelper.getTextWidth(trans, transFont, transSize) : 0f;
        float targetW = Math.max(mainW, transW) + padX * 2f;
        targetW = Math.max(minW, Math.min(maxW, targetW));

        if (animatedW < 0f) animatedW = targetW;
        if (animatedH < 0f) animatedH = targetH;
        animatedW += (targetW - animatedW) * 0.18f;
        animatedH += (targetH - animatedH) * 0.22f;

        float contentW = animatedW;
        float contentH = animatedH;
        float textAreaW = Math.max(0f, contentW - padX * 2f);

        int gameW = mc.getWindow().getScaledWidth();
        if (Float.isNaN(anchorCenterX)) {
            anchorCenterX = x + width * 0.5f;
        }
        if (dragging) {
            anchorCenterX = x + width * 0.5f;
        } else {
            float desiredX = anchorCenterX - contentW * 0.5f;
            desiredX = Math.max(0f, Math.min(desiredX, gameW - contentW));
            x = desiredX;
        }

        this.width = contentW;
        this.height = contentH;

        if (background.get()) {
            if (shadow.get() && shadowRange.get().floatValue() > 0f) {
                int tick = (int) (System.currentTimeMillis() / 20);
                Color c1 = ClickGui.color(tick);
                Color c2 = ClickGui.color2(tick);
                float[] rects = new float[]{x, y, contentW, contentH};
                float[] radii = new float[]{r};
                ShadowShader.drawStairShadowGradient(
                        x, y, contentW, contentH,
                        shadowRange.get().floatValue() * s,
                        shadowStrength.get().floatValue(),
                        new Color(c1.getRed(), c1.getGreen(), c1.getBlue(), 255),
                        new Color(c2.getRed(), c2.getGreen(), c2.getBlue(), 255),
                        rects, radii, 1
                );
            }
            if (blur.get() && blurStrength.get().floatValue() > 0f) {
                BlurShader.drawRoundedBlur(x, y, contentW, contentH, r, new Color(0, 0, 0, 0), blurStrength.get().floatValue(), 1.0f);
            }
        }

        NanoVGRenderer.INSTANCE.draw(vg -> {
            if (background.get()) {
                NanoVGHelper.drawRoundRect(x, y, contentW, contentH, r, backgroundColor.get());
            }

            float cx = x + contentW * 0.5f;
            float mainY = y + padY + mainH;
            drawScrollingCentered(main, cx, mainY, mainFont, mainSize, Color.WHITE, textAreaW);

            if (showTrans) {
                float transY = mainY + (3f * s) + transH;
                drawScrollingCentered(trans, cx, transY, transFont, transSize, new Color(180, 180, 200), textAreaW);
            }
        });
    }

    private CloudMusicService.LyricLine findLine(List<CloudMusicService.LyricLine> lines, long posMs) {
        if (lines == null || lines.isEmpty()) return null;
        int lo = 0;
        int hi = lines.size() - 1;
        int best = -1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            long t = lines.get(mid).timeMs();
            if (t <= posMs) {
                best = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        if (best < 0) return null;
        return lines.get(best);
    }

    private CloudMusicService.LyricLine findNearestLine(List<CloudMusicService.LyricLine> lines, long targetMs, long toleranceMs) {
        if (lines == null || lines.isEmpty()) return null;
        int lo = 0;
        int hi = lines.size() - 1;
        int best = -1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            long t = lines.get(mid).timeMs();
            if (t <= targetMs) {
                best = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        CloudMusicService.LyricLine left = best >= 0 ? lines.get(best) : null;
        CloudMusicService.LyricLine right = (best + 1) >= 0 && (best + 1) < lines.size() ? lines.get(best + 1) : null;
        CloudMusicService.LyricLine candidate = null;
        long bestDiff = Long.MAX_VALUE;
        if (left != null) {
            long diff = Math.abs(left.timeMs() - targetMs);
            if (diff < bestDiff) {
                bestDiff = diff;
                candidate = left;
            }
        }
        if (right != null) {
            long diff = Math.abs(right.timeMs() - targetMs);
            if (diff < bestDiff) {
                bestDiff = diff;
                candidate = right;
            }
        }
        if (candidate == null) return null;
        if (bestDiff > toleranceMs) return null;
        return candidate;
    }

    private boolean containsChinese(String s) {
        if (s == null || s.isBlank()) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0x4E00 && c <= 0x9FFF) {
                return true;
            }
        }
        return false;
    }

    private boolean containsKana(String s) {
        if (s == null || s.isBlank()) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 0x3040 && c <= 0x309F) || (c >= 0x30A0 && c <= 0x30FF) || (c >= 0x31F0 && c <= 0x31FF) || (c >= 0xFF66 && c <= 0xFF9D)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsLatin(String s) {
        if (s == null || s.isBlank()) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                return true;
            }
        }
        return false;
    }

    private void drawScrollingCentered(String text, float centerX, float baselineY, int font, float size, Color color, float maxWidth) {
        if (text == null || text.isBlank()) {
            return;
        }
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float textW = NanoVGHelper.getTextWidth(text, font, size);

        if (maxWidth <= 0 || textW <= maxWidth) {
            NanoVGHelper.drawString(text, centerX, baselineY, font, size, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_BASELINE, color);
            return;
        }

        float leftX = centerX - maxWidth * 0.5f;
        float maxScroll = textW - maxWidth;
        double scrollDuration = (maxScroll / 25f) * 1000.0;
        double pauseDuration = 900.0;
        double cycle = scrollDuration * 2 + pauseDuration * 2;
        double t = System.currentTimeMillis() % cycle;

        float scrollX;
        if (t < pauseDuration) {
            scrollX = 0;
        } else if (t < pauseDuration + scrollDuration) {
            scrollX = (float) ((t - pauseDuration) / scrollDuration * maxScroll);
        } else if (t < pauseDuration * 2 + scrollDuration) {
            scrollX = maxScroll;
        } else {
            scrollX = (float) (maxScroll - ((t - (pauseDuration * 2 + scrollDuration)) / scrollDuration * maxScroll));
        }

        NanoVG.nvgSave(vg);
        NanoVG.nvgIntersectScissor(vg, leftX, baselineY - size * 1.3f, maxWidth, size * 2.2f);
        NanoVG.nvgTranslate(vg, -scrollX, 0);
        NanoVGHelper.drawString(text, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, color);
        NanoVG.nvgRestore(vg);
    }
}
