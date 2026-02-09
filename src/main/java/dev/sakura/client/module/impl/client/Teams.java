package dev.sakura.client.module.impl.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.EnumValue;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Teams extends Module {
    private static Teams instance;

    public static Teams getInstance() {
        return instance;
    }

    public Teams() {
        super("Teams", "团队", Category.Client);
        instance = this;
    }

    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Armor);

    public static boolean isSameTeam(Entity entity) {
        if (!Sakura.MODULES.getModule(Teams.class).isEnabled()) {
            return false;
        } else if (entity instanceof PlayerEntity player) {
            if (instance.mode.is(Mode.Armor)) {
                Integer c1 = getLeatherArmorColor(player);
                Integer c2 = getLeatherArmorColor(instance.mc.player);
                if (c1 == -1 || c2 == -1) {
                    return false;
                }
                return c1.equals(c2);
            } else if (instance.mode.is(Mode.Color)) {
                Integer c1 = player.getTeamColorValue();
                Integer c2 = instance.mc.player.getTeamColorValue();
                return c1.equals(c2);
            } else {
                String playerTeam = getTeam(player);
                String targetTeam = getTeam(instance.mc.player);
                return Objects.equals(playerTeam, targetTeam);
            }
        } else {
            return false;
        }
    }

    private static String getTeam(Entity entity) {
        Scoreboard scoreboard = instance.mc.getNetworkHandler().getScoreboard();
        if (scoreboard == null) {
            return null;
        } else {
            Team team = scoreboard.getTeam(entity.getName().getString());
            return team != null ? team.getName() : null;
        }
    }

    private static int getLeatherArmorColor(PlayerEntity player) {
        for (EquipmentSlot slot : AttributeModifierSlot.ARMOR) {
            ItemStack stack = player.getEquippedStack(slot);
            if (stack.isEmpty()) continue;

            DyedColorComponent dyed = stack.get(DataComponentTypes.DYED_COLOR);
            if (dyed != null) {
                return dyed.rgb();
            }
        }
        return -1;
    }

    public static Color getMostPopularColor(Text text) {
        Object2IntMap.Entry<Color> biggestEntry = null;
        for (var entry : getColoredCharacterCount(toColoredTextList(text)).object2IntEntrySet()) {
            if (biggestEntry == null) biggestEntry = entry;
            else if (entry.getIntValue() > biggestEntry.getIntValue()) biggestEntry = entry;
        }
        return biggestEntry == null ? null : biggestEntry.getKey();
    }

    public static Object2IntMap<Color> getColoredCharacterCount(List<ColoredText> coloredTexts) {
        Object2IntMap<Color> colorCount = new Object2IntOpenHashMap<>();

        for (ColoredText coloredText : coloredTexts) {
            if (colorCount.containsKey(coloredText.color())) {
                // Since color was already catalogued, simply update the record by adding the length of the new text segment to the old one
                colorCount.put(coloredText.color(), colorCount.getInt(coloredText.color()) + coloredText.text().length());
            } else {
                // Add new entry to the hashmap
                colorCount.put(coloredText.color(), coloredText.text().length());
            }
        }

        return colorCount;
    }

    private static List<ColoredText> toColoredTextList(Text text) {
        List<ColoredText> result = new ArrayList<>();
        Stack<Style> styleStack = new Stack<>();

        processTextComponent(text, styleStack, result);

        return result;
    }

    private static void processTextComponent(Text component, Stack<Style> styleStack, List<ColoredText> result) {
        Stack<Style> newStack = new Stack<>();
        newStack.addAll(styleStack);

        if (component instanceof MutableText mutable) {
            Style style = mutable.getStyle();
            if (!style.isEmpty()) {
                newStack.push(style);
            }
        }

        String content = component.getString();
        if (!content.isEmpty()) {
            Color effectiveColor = getEffectiveColor(newStack);
            result.add(new ColoredText(effectiveColor, content));
        }

        for (Text sibling : component.getSiblings()) {
            processTextComponent(sibling, newStack, result);
        }
    }

    private static Color getEffectiveColor(Stack<Style> styleStack) {
        for (int i = styleStack.size() - 1; i >= 0; i--) {
            Style style = styleStack.get(i);
            if (style.getColor() != null) {
                TextColor textColor = style.getColor();
                int rgb = textColor.getRgb();
                if (rgb != 0) {
                    return new Color(rgb);
                }
            }
        }
        return new Color(255, 255, 255);
    }

    private record ColoredText(Color color, String text) {
    }

    public enum Mode {
        Armor,
        Scoreboard,
        Color
    }
}