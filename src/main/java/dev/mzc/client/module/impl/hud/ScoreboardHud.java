package dev.mzc.client.module.impl.hud;

import dev.mzc.client.module.HudModule;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.font.FontLoader;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.animations.Direction;
import dev.mzc.client.utils.animations.impl.EaseInOutQuad;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardHud extends HudModule {
    private final java.util.Map<String, EaseInOutQuad> animations = new java.util.HashMap<>();
    private final java.util.Map<String, Row> rowCache = new java.util.HashMap<>();
    private java.util.Set<String> previousKeys = new java.util.HashSet<>();

    private final BoolValue enableBloom = new BoolValue("EnableBloom", "光晕", true);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);
    private final NumberValue<Double> hudScale = new NumberValue<>("Scale", "缩放", 1.0, 0.5, 2.0, 0.1);
    private final ColorValue backgroundColor = new ColorValue("Background", "背景颜色", new Color(0, 0, 0, 110));
    private final NumberValue<Integer> animDuration = new NumberValue<>("AnimDuration", "动画时长", 250, 50, 1000, 50);
    private final NumberValue<Double> cardPadding = new NumberValue<>("CardPadding", "卡片内边距", 8.0, 4.0, 16.0, 1.0);
    private final NumberValue<Double> cardRowHeight = new NumberValue<>("CardRowHeight", "卡片高度", 22.0, 14.0, 30.0, 1.0);
    private final NumberValue<Double> cardIconGap = new NumberValue<>("CardIconGap", "卡片图标间距", 6.0, 2.0, 12.0, 0.5);
    private final NumberValue<Integer> maxItems = new NumberValue<>("MaxItems", "最大显示条目", 10, 3, 20, 1);
    private final BoolValue showTitle = new BoolValue("ShowTitle", "显示标题", true);
    private final ColorValue accentColor = new ColorValue("Accent", "强调颜色", new Color(255, 255, 255, 180));

    public ScoreboardHud() {
        super("ScoreboardHud", "记分板HUD", 8, 120);
        this.width = 170;
        this.height = 120;
    }

    @Override
    public void onRender(DrawContext context) {
        if (mc.world == null) return;

        ScoreboardObjective objective = getSidebarObjective(mc.world.getScoreboard());
        if (objective == null) return;

        java.util.List<Row> currentRows = collectRows(objective);
        java.util.Map<String, Row> currentMap = new java.util.HashMap<>();
        for (Row r : currentRows) currentMap.put(r.key, r);
        rowCache.putAll(currentMap);

        java.util.Set<String> currentKeys = currentMap.keySet();
        for (String k : currentKeys) {
            boolean isNew = !previousKeys.contains(k);
            EaseInOutQuad a = animations.computeIfAbsent(k, kk -> new EaseInOutQuad(animDuration.get(), 1.0));
            a.setDirection(Direction.FORWARDS);
            if (isNew) a.reset();
        }
        for (String k : new java.util.ArrayList<>(previousKeys)) {
            if (!currentKeys.contains(k)) {
                EaseInOutQuad a = animations.get(k);
                if (a != null) {
                    a.setDirection(Direction.BACKWARDS);
                    a.reset();
                }
            }
        }
        animations.entrySet().removeIf(e -> e.getValue().finished(Direction.BACKWARDS));
        previousKeys = new java.util.HashSet<>(currentKeys);

        java.util.List<Row> renderRows = new java.util.ArrayList<>(currentRows);

        double scale = hudScale.get();
        float pad = cardPadding.get().floatValue();
        float rh = cardRowHeight.get().floatValue();

        float gap = pad * 0.5f;
        float lineH = rh;
        int maxTextW = 0;
        if (showTitle.get()) {
            maxTextW = Math.max(maxTextW, mc.textRenderer.getWidth(objective.getDisplayName().asOrderedText()));
        }
        for (Row r : renderRows) {
            int w = mc.textRenderer.getWidth(r.left.asOrderedText());
            if (w > maxTextW) maxTextW = w;
        }
        float contentW = pad + maxTextW + pad;
        this.width = contentW;
        float drawW = (float) (contentW * scale);
        float totalH = pad;
        if (showTitle.get()) totalH += (lineH + gap);
        for (Row r : renderRows) {
            EaseInOutQuad a = animations.get(r.key);
            float v = a == null ? 1f : a.getOutput().floatValue();
            totalH += (lineH + gap) * v;
        }
        totalH += pad - gap;
        float drawH = (float) (totalH * scale);
        this.height = drawH;
        if (!showTitle.get() && renderRows.isEmpty()) return;

        Color bg = backgroundColor.get();
        NanoVGRenderer.INSTANCE.draw(vg -> {
            if (enableBloom.get()) {
                NanoVGHelper.drawRoundRectBloom(x, y, drawW, drawH, radius.get().floatValue(), bg);
            } else {
                NanoVGHelper.drawRoundRect(x, y, drawW, drawH, radius.get().floatValue(), bg);
            }
        });

        float baseY = (float) (y + pad * scale);
        float textLeft = (float) (x + pad * scale);
        if (showTitle.get()) {
            int th = mc.textRenderer.fontHeight;
            int ty = (int) (baseY + (lineH * scale - th) / 2f);
            context.drawText(mc.textRenderer, objective.getDisplayName(), (int) textLeft, ty, 0xFFFFFFFF, false);
            baseY += (lineH + gap) * scale;
        }
        for (Row r : renderRows) {
            EaseInOutQuad a = animations.get(r.key);
            float v = a == null ? 1f : a.getOutput().floatValue();
            float slideOffset = (float) ((1f - v) * lineH * scale * 0.4f);
            float lineY = baseY + slideOffset;
            int th = mc.textRenderer.fontHeight;
            int ty = (int) (lineY + (lineH * scale - th) / 2f);
            context.drawText(mc.textRenderer, r.left, (int) textLeft, ty, 0xFFFFFFFF, false);
            baseY += (lineH + gap) * scale * v;
        }
    }

    private ScoreboardObjective getSidebarObjective(Scoreboard scoreboard) {
        return scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
    }

    private List<Row> collectRows(ScoreboardObjective objective) {
        List<Row> rows = new ArrayList<>();
        Scoreboard scoreboard = objective.getScoreboard();
        
        // Use standard 1.21 API to get scores
        List<ScoreboardEntry> entries = new ArrayList<>(scoreboard.getScoreboardEntries(objective));
        
        entries.sort(Comparator.comparingInt(ScoreboardEntry::value).reversed());
        
        int limit = Math.min(maxItems.get(), entries.size());
        for (int i = 0; i < limit; i++) {
            ScoreboardEntry entry = entries.get(i);
            Text name = entry.name();
            int score = entry.value();
            
            // Generate a stable key for animation
            String key = objective.getName() + "#" + entry.owner();
            rows.add(new Row(key, name, score));
        }
        return rows;
    }

    private static class Row {
        final String key;
        final Text left;
        final int score;

        Row(String key, Text left, int score) {
            this.key = key;
            this.left = left;
            this.score = score;
        }
    }
}
