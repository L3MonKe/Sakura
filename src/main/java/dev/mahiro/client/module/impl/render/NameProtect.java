package dev.mahiro.client.module.impl.render;

import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.TextReplacer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class NameProtect extends Module {
    private static final String FAKE_NAME = "桜";

    public NameProtect() {
        super("NameProtect", "名字保护", Category.Render);
    }

    public static String getReplacement(String original) {
        if (original == null) return null;
        
        try {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return original;
            
            String playerName = mc.player.getName().getString();
            if (playerName == null || playerName.isEmpty() || playerName.length() < 2) return original;

            if (original.contains(playerName)) {
                return original.replace(playerName, FAKE_NAME);
            }
        } catch (Exception e) {
            return original;
        }
        return original;
    }

    public static Text getGradientReplacement(String original) {
        if (original == null) return null;

        try {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return Text.of(original);

            String playerName = mc.player.getName().getString();
            if (playerName == null || playerName.isEmpty() || playerName.length() < 2) return Text.of(original);

            if (original.contains(playerName)) {
                MutableText result = Text.empty();
                int lastIndex = 0;
                int index = original.indexOf(playerName);

                while (index != -1) {
                    result.append(Text.of(original.substring(lastIndex, index)));
                    result.append(TextReplacer.getGradientText(FAKE_NAME));
                    
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
    
    public static boolean shouldReplace(String text) {
        if (text == null) return false;
        try {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return false;
            String playerName = mc.player.getName().getString();
            return playerName != null && !playerName.isEmpty() && playerName.length() >= 2 && text.contains(playerName);
        } catch (Exception e) {
            return false;
        }
    }
}
