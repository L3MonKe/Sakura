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
    private volatile CloudMusicService.LyricPreciseData preciseData;
    private volatile boolean loadingPrecise = false;
    private final Value<Boolean> preciseLyric = new BoolValue("PreciseLyric", "逐词高亮", true);
    private final Value<Boolean> mainTextShadow = new BoolValue("MainShadow", "主歌词阴影", true);
    private final Value<Double> mainShadowDistance = new NumberValue<>("ShadowDist", "阴影间距", 1.5, 0.0, 6.0, 0.1, mainTextShadow::get);
    private final Value<Boolean> preciseStrict = new BoolValue("PreciseStrict", "逐词严格", true);
    private float animatedW = -1f;
    private float animatedH = -1f;
    private float anchorCenterX = Float.NaN;
    private float revealMainW = 0f;
    private long revealMainStartMs = Long.MIN_VALUE;
    private float revealTransW = 0f;
    private long revealTransStartMs = Long.MIN_VALUE;

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
            preciseData = null;
            loadingPrecise = false;
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
        if (preciseLyric.get() && preciseData == null && !loadingPrecise) {
            loadingPrecise = true;
            service.loadLyricPrecise(songId).whenComplete((data2, ex2) -> {
                if (songId != currentSongId) {
                    return;
                }
                if (ex2 == null) {
                    preciseData = data2;
                }
                loadingPrecise = false;
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
        {
            float dw = targetW - animatedW;
            float wFactor = dw > 0f ? 0.45f : 0.18f;
            animatedW += dw * wFactor;
        }
        {
            float dh = targetH - animatedH;
            float hFactor = dh > 0f ? 0.28f : 0.22f;
            animatedH += dh * hFactor;
        }

        float contentW = animatedW;
        float contentH = animatedH;
        float textAreaW = Math.max(0f, contentW - padX * 2f);
        float targetTextAreaW = Math.max(0f, targetW - padX * 2f);

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
            boolean usePrecise = preciseLyric.get();
            if (!usePrecise) {
                drawScrollingCentered(main, cx, mainY, mainFont, mainSize, Color.WHITE, textAreaW, targetTextAreaW);
            } else {
                List<CloudMusicService.LyricLine> lines = data.lyric();
                int idx = lines.indexOf(mainLine);
                long startMs = mainLine.timeMs();
                if (startMs != revealMainStartMs) {
                    revealMainStartMs = startMs;
                    revealMainW = 0f;
                }
                CloudMusicService.LyricPreciseData precise = preciseData;
                CloudMusicService.PreciseLine pLine = null;
                if (precise != null && precise.lyric() != null && !precise.lyric().isEmpty()) {
                    pLine = findPreciseLine(precise.lyric(), startMs);
                }
                if (pLine != null && pLine.words() != null && !pLine.words().isEmpty()) {
                    float targetReveal = computePreciseRevealWidth(main, pLine.words(), startMs, posMs, mainFont, mainSize);
                    revealMainW += (targetReveal - revealMainW) * 0.35f;
                float revealWClamped = Math.max(0f, Math.min(revealMainW, NanoVGHelper.getTextWidth(main, mainFont, mainSize)));
                if (mainTextShadow.get() && revealWClamped > 0f) {
                    float dist = mainShadowDistance.get().floatValue();
                    drawMaskScrollingCentered(main, cx + dist, mainY + dist, mainFont, mainSize,
                            new Color(0, 0, 0, 120), new Color(0, 0, 0, 120),
                            textAreaW, targetTextAreaW, revealWClamped);
                }
                drawMaskScrollingCentered(main, cx, mainY, mainFont, mainSize,
                        new Color(255, 255, 255, 160), Color.WHITE,
                        textAreaW, targetTextAreaW, revealWClamped);
                } else {
                    long endMs = startMs + 3000L;
                    if (idx >= 0 && (idx + 1) < lines.size()) {
                        endMs = lines.get(idx + 1).timeMs();
                    } else {
                        CloudMusicService.SongItem si = player.getCurrentSong();
                        if (si != null && si.durationMs() > 0) {
                            endMs = Math.max(startMs + 800L, si.durationMs());
                        }
                    }
                    long durMs = Math.max(800L, endMs - startMs);
                    float revealRatio = (float) Math.max(0.0, Math.min(1.0, (posMs - startMs) / (double) durMs));
                    float textWFull = NanoVGHelper.getTextWidth(main, mainFont, mainSize);
                    float targetReveal = textWFull * revealRatio;
                    revealMainW += (targetReveal - revealMainW) * 0.35f;
                    drawMaskScrollingCentered(main, cx, mainY, mainFont, mainSize,
                            new Color(255, 255, 255, 160), Color.WHITE,
                            textAreaW, targetTextAreaW, Math.max(0f, Math.min(revealMainW, textWFull)));
                }
            }

            if (showTrans) {
                float transY = mainY + (3f * s) + transH;
                boolean usePreciseTrans = preciseLyric.get();
                if (!usePreciseTrans) {
                    drawScrollingCentered(trans, cx, transY, transFont, transSize, new Color(180, 180, 200), textAreaW, targetTextAreaW);
                } else {
                    CloudMusicService.LyricPreciseData precise2 = preciseData;
                    CloudMusicService.PreciseLine pTrans = null;
                    long tStart = transLine != null ? transLine.timeMs() : mainLine.timeMs();
                    if (tStart != revealTransStartMs) {
                        revealTransStartMs = tStart;
                        revealTransW = 0f;
                    }
                    if (precise2 != null && precise2.translation() != null && !precise2.translation().isEmpty()) {
                        pTrans = findPreciseLine(precise2.translation(), tStart);
                    }
                    if (pTrans != null && pTrans.words() != null && !pTrans.words().isEmpty()) {
                        float targetRevealT = computePreciseRevealWidth(trans, pTrans.words(), tStart, posMs, transFont, transSize);
                        revealTransW += (targetRevealT - revealTransW) * 0.35f;
                        float revealWTClamped = Math.max(0f, Math.min(revealTransW, NanoVGHelper.getTextWidth(trans, transFont, transSize)));
                        drawMaskScrollingCentered(trans, cx, transY, transFont, transSize,
                                new Color(180, 180, 200, 140), new Color(180, 180, 200),
                                textAreaW, targetTextAreaW, revealWTClamped);
                    } else {
                        List<CloudMusicService.LyricLine> tLines = data.translation();
                        int tIdx = tLines == null ? -1 : tLines.indexOf(transLine);
                        long tEnd = tStart + 3000L;
                        if (tIdx >= 0 && (tIdx + 1) < tLines.size()) {
                            tEnd = tLines.get(tIdx + 1).timeMs();
                        }
                        long tDur = Math.max(800L, tEnd - tStart);
                        float transReveal = (float) Math.max(0.0, Math.min(1.0, (posMs - tStart) / (double) tDur));
                        float textWFullT = NanoVGHelper.getTextWidth(trans, transFont, transSize);
                        float targetRevealT = textWFullT * transReveal;
                        revealTransW += (targetRevealT - revealTransW) * 0.35f;
                        drawMaskScrollingCentered(trans, cx, transY, transFont, transSize,
                                new Color(180, 180, 200, 140), new Color(180, 180, 200),
                                textAreaW, targetTextAreaW, Math.max(0f, Math.min(revealTransW, textWFullT)));
                    }
                }
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

    private CloudMusicService.PreciseLine findPreciseLine(List<CloudMusicService.PreciseLine> lines, long posMs) {
        if (lines == null || lines.isEmpty()) return null;
        int lo = 0;
        int hi = lines.size() - 1;
        int best = -1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            long t = lines.get(mid).startMs();
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

    private void drawScrollingCentered(String text, float centerX, float baselineY, int font, float size, Color color, float visibleMaxWidth, float scrollBaseWidth) {
        if (text == null || text.isBlank()) {
            return;
        }
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float textW = NanoVGHelper.getTextWidth(text, font, size);

        if (visibleMaxWidth <= 0 || textW <= visibleMaxWidth) {
            NanoVGHelper.drawString(text, centerX, baselineY, font, size, NanoVG.NVG_ALIGN_CENTER | NanoVG.NVG_ALIGN_BASELINE, color);
            return;
        }

        float leftX = centerX - visibleMaxWidth * 0.5f;
        float baseWidth = Math.max(1f, scrollBaseWidth);
        float maxScroll = Math.max(0f, textW - baseWidth);
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
        NanoVG.nvgIntersectScissor(vg, leftX, baselineY - size * 1.3f, visibleMaxWidth, size * 2.2f);
        NanoVG.nvgTranslate(vg, -scrollX, 0);
        NanoVGHelper.drawString(text, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, color);
        NanoVG.nvgRestore(vg);
    }

    private void drawProgressiveScrollingCentered(String text, float centerX, float baselineY, int font, float size, Color baseColor, Color revealColor, float visibleMaxWidth, float scrollBaseWidth, float revealRatio) {
        if (text == null || text.isBlank()) {
            return;
        }
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float textW = NanoVGHelper.getTextWidth(text, font, size);
        float targetRevealW = Math.max(0f, Math.min(textW, textW * revealRatio));
        int lo = 0, hi = text.length();
        int revealChars = 0;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            float w = mid <= 0 ? 0f : NanoVGHelper.getTextWidth(text.substring(0, mid), font, size);
            if (w <= targetRevealW) {
                revealChars = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        String revealText = revealChars <= 0 ? "" : text.substring(0, revealChars);
        if (visibleMaxWidth <= 0 || textW <= visibleMaxWidth) {
            float leftX = centerX - textW * 0.5f;
            NanoVGHelper.drawString(text, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, baseColor);
            if (!revealText.isEmpty()) {
                NanoVGHelper.drawString(revealText, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, revealColor);
            }
            return;
        }
        float leftX = centerX - visibleMaxWidth * 0.5f;
        float baseWidth = Math.max(1f, scrollBaseWidth);
        float maxScroll = Math.max(0f, textW - baseWidth);
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
        NanoVG.nvgIntersectScissor(vg, leftX, baselineY - size * 1.3f, visibleMaxWidth, size * 2.2f);
        NanoVG.nvgTranslate(vg, -scrollX, 0);
        NanoVGHelper.drawString(text, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, baseColor);
        if (!revealText.isEmpty()) {
            NanoVGHelper.drawString(revealText, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, revealColor);
        }
        NanoVG.nvgRestore(vg);
    }

    private float computePreciseRevealWidth(String text, List<CloudMusicService.WordFragment> words, long lineStartMs, long posMs, int font, float size) {
        if (text == null || text.isBlank() || words == null || words.isEmpty()) {
            return 0f;
        }
        long elapsed = Math.max(0L, posMs - lineStartMs);
        float revealWidth = 0f;
        for (int i = 0; i < words.size(); i++) {
            CloudMusicService.WordFragment wf = words.get(i);
            long st = Math.max(0L, wf.startMs());
            long du = Math.max(0L, wf.durationMs());
            if (du == 0L && !preciseStrict.get()) {
                long nextStart = (i + 1) < words.size() ? Math.max(0L, words.get(i + 1).startMs()) : st;
                long approx = Math.max(0L, nextStart - st);
                long cap = 1200L;
                du = Math.min(approx, cap);
            }
            String seg = wf.text();
            if (seg == null || seg.isEmpty()) {
                continue;
            }
            float segW = NanoVGHelper.getTextWidth(seg, font, size);
            if (du > 0L && elapsed >= st + du) {
                revealWidth += segW;
                continue;
            }
            if (elapsed >= st) {
                float ratio = du > 0L ? Math.max(0f, Math.min(1f, (elapsed - st) / (float) du)) : 1f;
                revealWidth += segW * ratio;
                break;
            }
            break;
        }
        return revealWidth;
    }

    private void drawMaskScrollingCentered(String text, float centerX, float baselineY, int font, float size, Color baseColor, Color revealColor, float visibleMaxWidth, float scrollBaseWidth, float revealWidth) {
        if (text == null || text.isBlank()) {
            return;
        }
        long vg = NanoVGRenderer.INSTANCE.getContext();
        float textW = NanoVGHelper.getTextWidth(text, font, size);
        if (visibleMaxWidth <= 0 || textW <= visibleMaxWidth) {
            float leftX = centerX - textW * 0.5f;
            NanoVGHelper.drawString(text, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, baseColor);
            if (revealWidth > 0f) {
                NanoVG.nvgSave(vg);
                NanoVG.nvgIntersectScissor(vg, leftX, baselineY - size * 1.3f, revealWidth, size * 2.2f);
                NanoVGHelper.drawString(text, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, revealColor);
                NanoVG.nvgRestore(vg);
            }
            return;
        }
        float leftX = centerX - visibleMaxWidth * 0.5f;
        float baseWidth = Math.max(1f, scrollBaseWidth);
        float maxScroll = Math.max(0f, textW - baseWidth);
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
        NanoVG.nvgIntersectScissor(vg, leftX, baselineY - size * 1.3f, visibleMaxWidth, size * 2.2f);
        NanoVG.nvgTranslate(vg, -scrollX, 0);
        NanoVGHelper.drawString(text, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, baseColor);
        if (revealWidth > 0f) {
            NanoVG.nvgIntersectScissor(vg, leftX, baselineY - size * 1.3f, revealWidth, size * 2.2f);
            NanoVGHelper.drawString(text, leftX, baselineY, font, size, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_BASELINE, revealColor);
        }
        NanoVG.nvgRestore(vg);
    }
}
