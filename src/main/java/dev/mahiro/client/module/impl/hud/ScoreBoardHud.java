package dev.mahiro.client.module.impl.hud;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.HudModule;
import dev.mahiro.client.module.impl.client.TextReplacer;
import dev.mahiro.client.module.impl.render.NameProtect;
import dev.mahiro.client.module.impl.render.NoRender;
import dev.mahiro.client.nanovg.NanoVGRenderer;
import dev.mahiro.client.nanovg.font.FontLoader;
import dev.mahiro.client.nanovg.util.NanoVGHelper;
import dev.mahiro.client.utils.render.Shader2DUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreBoardHud extends HudModule {

    private final NumberValue<Double> scale = new NumberValue<>("Scale", "大小", 1.0, 0.5, 2.0, 0.1);
    private final NumberValue<Double> radius = new NumberValue<>("Radius", "圆角半径", 6.0, 0.0, 15.0, 1.0);
    private final BoolValue blur = new BoolValue("Blur", "背景模糊", true);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 20.0, 0.5, blur::get);
    private final NumberValue<Double> blurShrink = new NumberValue<>("BlurShrink", "模糊缩减", 2.0, 0.0, 10.0, 0.5);
    private final NumberValue<Double> blurOffsetX = new NumberValue<>("BlurOffsetX", "模糊X偏移", 0.0, -10.0, 10.0, 0.5);
    private final NumberValue<Double> blurOffsetY = new NumberValue<>("BlurOffsetY", "模糊Y偏移", 0.0, -10.0, 10.0, 0.5);
    private final ColorValue backgroundColor = new ColorValue("Background", "背景颜色", new Color(0, 0, 0, 100));
    private final ColorValue titleColor = new ColorValue("TitleColor", "标题颜色", new Color(255, 255, 255));
    private final ColorValue textColor = new ColorValue("TextColor", "文本颜色", new Color(255, 255, 255));
    private final ColorValue scoreColor = new ColorValue("ScoreColor", "分数颜色", new Color(255, 85, 85));

    private static final Map<Character, Color> COLOR_CODES = new HashMap<>();

    static {
        COLOR_CODES.put('0', new Color(0, 0, 0));
        COLOR_CODES.put('1', new Color(0, 0, 170));
        COLOR_CODES.put('2', new Color(0, 170, 0));
        COLOR_CODES.put('3', new Color(0, 170, 170));
        COLOR_CODES.put('4', new Color(170, 0, 0));
        COLOR_CODES.put('5', new Color(170, 0, 170));
        COLOR_CODES.put('6', new Color(255, 170, 0));
        COLOR_CODES.put('7', new Color(170, 170, 170));
        COLOR_CODES.put('8', new Color(85, 85, 85));
        COLOR_CODES.put('9', new Color(85, 85, 255));
        COLOR_CODES.put('a', new Color(85, 255, 85));
        COLOR_CODES.put('b', new Color(85, 255, 255));
        COLOR_CODES.put('c', new Color(255, 85, 85));
        COLOR_CODES.put('d', new Color(255, 85, 255));
        COLOR_CODES.put('e', new Color(255, 255, 85));
        COLOR_CODES.put('f', new Color(255, 255, 255));
        COLOR_CODES.put('r', new Color(255, 255, 255));
    }

    public ScoreBoardHud() {
        super("ScoreBoard", "记分板", 100, 100);
    }

    @Override
    public void onRender(DrawContext context) {
        if (mc.world == null || mc.player == null) return;

        ScoreboardObjective objective = mc.world.getScoreboard().getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
        if (objective == null) return;

        List<ScoreboardEntry> scores = new ArrayList<>(mc.world.getScoreboard().getScoreboardEntries(objective));
        if (scores.isEmpty()) return;

        scores = scores.stream()
                .filter(score -> score.name() != null && !score.hidden())
                .sorted(Comparator.comparingInt(ScoreboardEntry::value).reversed()
                        .thenComparing(score -> score.owner(), String.CASE_INSENSITIVE_ORDER))
                .limit(15)
                .collect(Collectors.toList());

        if (scores.isEmpty()) return;

        Text title = objective.getDisplayName();
        List<LineData> lines = new ArrayList<>();

        int titleFont = FontLoader.bold(18);
        int textFont = FontLoader.medium(16);

        Text processedTitle = processText(title);
        float titleW = getNanoVGTextWidth(processedTitle, titleFont, 18);
        float maxW = titleW;

        for (ScoreboardEntry score : scores) {
            Team team = mc.world.getScoreboard().getScoreHolderTeam(score.owner());
            Text nameText = score.name();
            
            if (team != null) {
                MutableText mutableText = Text.empty();
                mutableText.append(team.getPrefix());
                mutableText.append(nameText);
                mutableText.append(team.getSuffix());
                nameText = mutableText;
            }

            Text processedName = processText(nameText);
            String scoreStr = String.valueOf(score.value());
            
            float nameW = getNanoVGTextWidth(processedName, textFont, 16);
            float scoreW = NanoVGHelper.getTextWidth(scoreStr, textFont, 16);
            float totalW = nameW + 10 + scoreW;

            if (totalW > maxW) maxW = totalW;

            lines.add(new LineData(processedName, scoreStr, nameW, scoreW));
        }

        float padding = 6;
        float headerH = 20;
        float itemH = 16;
        
        float baseWidth = maxW + padding * 2;
        float baseHeight = headerH + lines.size() * itemH + padding;
        
        float scaleVal = scale.get().floatValue();
        this.width = baseWidth * scaleVal;
        this.height = baseHeight * scaleVal;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            NanoVGHelper.save();
            NanoVGHelper.translate(x, y);
            NanoVGHelper.scale(scaleVal, scaleVal);
            NanoVGHelper.translate(-x, -y);
            
            float r = radius.get().floatValue();

            if (blur.get()) {
                MatrixStack matrices = context.getMatrices();
                matrices.push();
                matrices.translate(x, y, 0);
                matrices.scale(scaleVal, scaleVal, 1.0f);
                matrices.translate(-x, -y, 0);
                
                float shrink = blurShrink.get().floatValue();
                float offX = blurOffsetX.get().floatValue();
                float offY = blurOffsetY.get().floatValue();
                
                Shader2DUtil.drawRoundedBlur(
                        matrices, x + shrink + offX, y + shrink + offY, baseWidth - shrink * 2, baseHeight - shrink * 2, Math.max(0, r - shrink),
                        new Color(0, 0, 0, 0), blurStrength.get().floatValue(), 1.0f
                );
                
                matrices.pop();
            }

            NanoVGHelper.drawRoundRect(x, y, baseWidth, baseHeight, r, backgroundColor.get());

            float titleX = x + (baseWidth - titleW) / 2;
            drawNanoVGText(processedTitle, titleX, y + 4 + 9, titleFont, 18, titleColor.get());

            float currentY = y + headerH;
            for (LineData line : lines) {
                drawNanoVGText(line.name, x + padding, currentY + 8, textFont, 16, textColor.get());
                NanoVGHelper.drawString(line.score, x + baseWidth - padding - line.scoreW, currentY + 8, textFont, 16, scoreColor.get());
                currentY += itemH;
            }
            
            NanoVGHelper.restore();
        });
    }

    private Text processText(Text original) {
        String textStr = original.getString();
        
        if (NameProtect.shouldReplace(textStr)) {
            return NameProtect.getGradientReplacement(textStr);
        }
        
        if (TextReplacer.containsTarget(textStr)) {
            return TextReplacer.replace(textStr);
        }
        
        return original;
    }

    private float getNanoVGTextWidth(Text text, int font, float size) {
        final float[] width = {0};
        text.visit((style, asString) -> {
            width[0] += getTextWidth(asString, font, size);
            return Optional.empty();
        }, Style.EMPTY);
        return width[0];
    }
    
    private float getTextWidth(String text, int font, float size) {
        String plainText = text.replaceAll("§[0-9a-fk-or]", "");
        return NanoVGHelper.getTextWidth(plainText, font, size);
    }

    private void drawNanoVGText(Text text, float x, float y, int font, float size, Color baseColor) {
        // 使用单元素数组来存储当前 X 坐标和当前字符索引
        final float[] currentX = {x};
        final int[] charIndex = {0};
        
        String fullString = text.getString();
        // 预先检测 glow 区域
        List<int[]> glowRanges = new ArrayList<>();
        addGlowRanges(fullString, "桜", glowRanges);
        addGlowRanges(fullString, "Mahiro", glowRanges);

        text.visit((style, asString) -> {
            Color color = baseColor;
            if (style != null && style.getColor() != null) {
                int rgb = style.getColor().getRgb();
                int alpha = baseColor.getAlpha();
                color = new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, alpha);
            }
            
            // 逐字符处理
            for (int i = 0; i < asString.length(); i++) {
                char c = asString.charAt(i);
                String s = String.valueOf(c);
                int globalIndex = charIndex[0] + i;
                
                // 检查当前字符是否在 glow 范围内
                boolean shouldGlow = false;
                for (int[] range : glowRanges) {
                    if (globalIndex >= range[0] && globalIndex < range[1]) {
                        shouldGlow = true;
                        break;
                    }
                }
                
                // 处理颜色代码
                if (c == '§' && i + 1 < asString.length()) {
                    // 这里简化处理：如果是颜色代码，跳过绘制（因为 visit 已经处理了大部分样式，但如果是字符串内嵌的颜色代码...）
                    // 实际上 visit 会把 Text 分割，但如果是 LiteralText("§aTest")，visit 拿到的是 "§aTest"
                    // 我们需要 drawColoredString 的逻辑，但这里是逐字符
                    // 暂时假设 visit 传递的文本可能包含未解析的颜色代码
                    // 我们的 drawColoredString 是处理整个字符串的
                    // 这里我们为了支持 glow，不得不拆开 drawColoredString
                    
                    // 为了不破坏现有的 drawColoredString 逻辑，我们回退一步：
                    // 如果整个片段包含在 glow 范围内，或者部分包含？
                    // 由于 asString 可能就是一个字符（因为 TextReplacer 这样生成的），我们可以直接判断
                }
            }
            
            // 上面的循环逻辑有点复杂，因为 drawColoredString 内部也在循环
            // 让我们简化：直接判断 asString 是否完全在 Glow 范围内
            // 由于 TextReplacer 是逐字符生成 Text 的，所以 asString 通常是单个字符
            // 如果是 NameProtect/TextReplacer 生成的，它们是 Text.literal("M"), Text.literal("a")...
            
            int startIndex = charIndex[0];
            int endIndex = startIndex + asString.length();
            boolean isFullyGlow = false;
            
            // 检查这个片段是否属于 Mahiro 或 桜
            for (int[] range : glowRanges) {
                // 如果片段与 glow 范围有交集，我们就认为它需要发光（对于逐字符的 Text 这是准确的）
                if (Math.max(startIndex, range[0]) < Math.min(endIndex, range[1])) {
                    isFullyGlow = true;
                    break;
                }
            }
            
            if (isFullyGlow) {
                // 使用发光绘制，但要注意处理内部的颜色代码（如果有的话）
                // 对于 TextReplacer 生成的文本，没有 § 代码，颜色在 style 里
                if (asString.contains("§")) {
                     // 如果包含颜色代码，还是回退到 drawColoredString，但加上 glow 参数？
                     // 现有的 drawColoredString 不支持 glow
                     // 考虑到 TextReplacer 的输出，我们直接用 drawGlowingString 绘制这个片段
                     NanoVGHelper.drawGlowingString(asString, currentX[0], y, font, size, color, 10.0f);
                     currentX[0] += NanoVGHelper.getTextWidth(asString, font, size);
                } else {
                     NanoVGHelper.drawGlowingString(asString, currentX[0], y, font, size, color, 10.0f);
                     currentX[0] += NanoVGHelper.getTextWidth(asString, font, size);
                }
            } else {
                drawColoredString(asString, currentX, y, font, size, color);
            }
            
            charIndex[0] += asString.length();
            return Optional.empty();
        }, Style.EMPTY);
    }
    
    private void addGlowRanges(String text, String target, List<int[]> ranges) {
        int index = text.indexOf(target);
        while (index != -1) {
            ranges.add(new int[]{index, index + target.length()});
            index = text.indexOf(target, index + 1);
        }
    }
    
    private void drawColoredString(String text, float[] currentX, float y, int font, float size, Color defaultColor) {
        Color currentColor = defaultColor;
        StringBuilder segment = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '§' && i + 1 < text.length()) {
                if (segment.length() > 0) {
                    NanoVGHelper.drawString(segment.toString(), currentX[0], y, font, size, currentColor);
                    currentX[0] += NanoVGHelper.getTextWidth(segment.toString(), font, size);
                    segment.setLength(0);
                }
                char code = Character.toLowerCase(text.charAt(i + 1));
                Color newColor = COLOR_CODES.get(code);
                if (newColor != null) {
                    currentColor = newColor;
                }
                i++;
            } else {
                segment.append(c);
            }
        }

        if (segment.length() > 0) {
            NanoVGHelper.drawString(segment.toString(), currentX[0], y, font, size, currentColor);
            currentX[0] += NanoVGHelper.getTextWidth(segment.toString(), font, size);
        }
    }

    private static class LineData {
        Text name;
        String score;
        float nameW;
        float scoreW;

        public LineData(Text name, String score, float nameW, float scoreW) {
            this.name = name;
            this.score = score;
            this.nameW = nameW;
            this.scoreW = scoreW;
        }
    }
}
