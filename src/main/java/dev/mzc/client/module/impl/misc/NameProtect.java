package dev.mzc.client.module.impl.misc;

import dev.mzc.client.Sakura;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.StringValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.awt.*;

public class NameProtect extends Module {
    public final StringValue customName = new StringValue("CustomName", "自定义名字", "桜");
    public final ColorValue gradientStart = new ColorValue("GradientStart", "渐变开始", new Color(255, 180, 225));
    public final ColorValue gradientEnd = new ColorValue("GradientEnd", "渐变结束", Color.WHITE);

    public NameProtect() {
        super("NameProtect", "名字保护", Category.Misc);
        this.setType(ModuleType.Safe);
    }

    public static String getReplacement(String original) {
        if (original == null) return null;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return original;

        String playerName = mc.player.getName().getString();
        if (playerName == null || playerName.length() < 2) return original;

        NameProtect mod = (NameProtect) Sakura.MODULES.getModule(NameProtect.class);
        String fakeName = (mod != null) ? mod.customName.get() : "桜";

        if (original.contains(playerName)) {
            return original.replace(playerName, fakeName);
        }
        return original;
    }

    public static Text getGradientReplacement(String original) {
        if (original == null) return null;

        try {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return Text.of(original);

            String playerName = mc.player.getName().getString();
            if (playerName == null || playerName.length() < 2) return Text.of(original);

            NameProtect mod = (NameProtect) Sakura.MODULES.getModule(NameProtect.class);
            String fakeName = (mod != null) ? mod.customName.get() : "桜";

            if (original.contains(playerName)) {
                MutableText result = Text.empty();
                int lastIndex = 0;
                int index = original.indexOf(playerName);

                while (index != -1) {
                    result.append(Text.of(original.substring(lastIndex, index)));
                    
                    if (mod != null) {
                        result.append(mod.getGradientText(fakeName));
                    } else {
                        result.append(Text.of(fakeName));
                    }

                    lastIndex = index + playerName.length();
                    index = original.indexOf(playerName, lastIndex);
                }

                result.append(Text.of(original.substring(lastIndex)));
                return result;
            }
        } catch (Exception e) {
            return Text.of(original);
        }
        return Text.of(original);
    }

    public static Text getGradientReplacement(Text original) {
        if (original == null) return null;
        return replaceInText(original);
    }

    private static MutableText replaceInText(Text text) {
        MutableText result;

        if (text.getContent() instanceof PlainTextContent) {
            String content = ((PlainTextContent) text.getContent()).string();
            if (shouldReplace(content)) {
                result = replaceStringWithStyle(content, text.getStyle());
            } else {
                result = Text.literal(content).setStyle(text.getStyle());
            }
        } else {
            result = text.copyContentOnly().setStyle(text.getStyle());
        }

        for (Text sibling : text.getSiblings()) {
            result.append(replaceInText(sibling));
        }

        return result;
    }

    private static MutableText replaceStringWithStyle(String original, Style style) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return Text.literal(original).setStyle(style);
        String playerName = mc.player.getName().getString();
        if (playerName == null || playerName.length() < 2) return Text.literal(original).setStyle(style);

        NameProtect mod = (NameProtect) Sakura.MODULES.getModule(NameProtect.class);
        String fakeName = (mod != null) ? mod.customName.get() : "桜";

        MutableText result = Text.empty();
        int lastIndex = 0;
        int index = original.indexOf(playerName);

        while (index != -1) {
            String prefix = original.substring(lastIndex, index);
            if (!prefix.isEmpty()) {
                result.append(Text.literal(prefix).setStyle(style));
            }

            if (mod != null) {
                result.append(mod.getGradientText(fakeName));
            } else {
                result.append(Text.literal(fakeName).setStyle(style));
            }

            lastIndex = index + playerName.length();
            index = original.indexOf(playerName, lastIndex);
        }

        String suffix = original.substring(lastIndex);
        if (!suffix.isEmpty()) {
            result.append(Text.literal(suffix).setStyle(style));
        }

        return result;
    }


    public static boolean shouldReplace(String text) {
        if (text == null) return false;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return false;
        String playerName = mc.player.getName().getString();
        return playerName != null && playerName.length() >= 2 && text.contains(playerName);
    }

    public Text getGradientText(String content) {
        MutableText text = Text.empty();
        long time = System.currentTimeMillis();

        for (int i = 0; i < content.length(); i++) {
            int color = getGradientColor(i, time);
            text.append(Text.literal(String.valueOf(content.charAt(i)))
                    .setStyle(Style.EMPTY.withColor(color)));
        }
        return text;
    }

    private int getGradientColor(int offset, long time) {
        double speed = 2.0;

        double progress = (Math.sin((time * 0.003 * speed + offset * 0.5)) + 1.0) / 2.0;

        Color start = gradientStart.get();
        Color end = gradientEnd.get();

        int r = (int) (start.getRed() + (end.getRed() - start.getRed()) * progress);
        int g = (int) (start.getGreen() + (end.getGreen() - start.getGreen()) * progress);
        int b = (int) (start.getBlue() + (end.getBlue() - start.getBlue()) * progress);

        return (r << 16) | (g << 8) | b;
    }
}
