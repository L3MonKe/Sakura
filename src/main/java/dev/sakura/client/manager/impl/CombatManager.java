package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.module.impl.client.Targets;
import dev.sakura.client.module.impl.client.Teams;
import dev.sakura.client.module.impl.combat.AntiBot;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.text.Style;
import net.minecraft.text.MutableText;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import static dev.sakura.client.Sakura.mc;

public class CombatManager extends Managers {

    private record ColoredText(Color color, String text) {}

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
                net.minecraft.text.TextColor textColor = style.getColor();
                int rgb = textColor.getRgb();
                if (rgb != 0) {
                    return new Color(rgb);
                }
            }
        }
        return new Color(255, 255, 255);
    }
    public List<LivingEntity> getEntities(double range) {
        List<LivingEntity> list = new ArrayList<>();
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                if (isEnemy(livingEntity, range)) {
                    list.add(livingEntity);
                }
            }
        }
        return list;
    }

    public LivingEntity getClosestEnemy(double range) {
        return getClosestEnemy(range, Sakura.MODULES.getModule(Targets.class).targetBy.get());
    }

    public LivingEntity getClosestEnemy(double range, TargetBy targetBy) {
        LivingEntity target = null;
        switch (targetBy) {
            case FOV -> target = getTargetByFOV(range);
            case Health -> target = getTargetByHealth(range);
            case Distance -> target = getNearestTarget(range);
        }
        return target;
    }

    public boolean isEnemy(LivingEntity entity, double range) {
        Targets targetSetting = Sakura.MODULES.getModule(Targets.class);
        if (isInvalid(entity, range)) {
            return false;
        }
        return switch (entity) {
            case AnimalEntity animalEntity when targetSetting.targets.isEnabled("Animals") -> true;
            case PassiveEntity passiveEntity when targetSetting.targets.isEnabled("Passive") -> true;
            case MobEntity mobEntity when targetSetting.targets.isEnabled("Mobs") -> true;
            case PlayerEntity player when targetSetting.targets.isEnabled("Player") -> {
                Teams teams = Sakura.MODULES.getModule(Teams.class);
                if (teams.isEnabled()) {
                    yield !teams.isSameTeam(player);
                }
                yield !AntiBot.isBot(player);
            }
            default -> false;
        };
    }

    public boolean isInvalid(LivingEntity entity, double range) {
        Targets targetSetting = Sakura.MODULES.getModule(Targets.class);
        if (Vec3d.of(mc.player.getBlockPos()).distanceTo(Vec3d.of(entity.getBlockPos())) > range) {
            return true;
        }
        if (entity.isInvisible() && targetSetting.check.isEnabled("Invisible")) {
            return true;
        }
        if (entity.isDead() && targetSetting.check.isEnabled("Dead")) {
            return true;
        }
        return entity == mc.player;
    }

    private int getLeatherArmorColor(PlayerEntity player) {
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

    public LivingEntity getNearestTarget(double range) {
        return getEntities(range).stream().min(Comparator.comparing(t -> mc.player.distanceTo(t))).orElse(null);
    }

    public LivingEntity getTargetByHealth(double range) {
        return getEntities(range).stream().min(Comparator.comparing(t -> (Managers.HEALTH.getHealth(t)))).orElse(null);
    }

    public LivingEntity getTargetByFOV(double range) {
        return getEntities(range).stream().min(Comparator.comparing(this::getFOVAngle)).orElse(null);
    }

    public LivingEntity getTargetByFOV(double range, double fov) {
        return getEntities(range).stream()
                .filter(entityPlayer -> getFOVAngle(entityPlayer) < fov)
                .min(Comparator.comparing(this::getFOVAngle)).orElse(null);
    }

    private double getFOVAngle(LivingEntity e) {
        double yaw = MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(e.getZ() - mc.player.getZ(), e.getX() - mc.player.getX())) - 90.0);
        return Math.abs(yaw - MathHelper.wrapDegrees(mc.player.getYaw()));
    }

    public enum TargetBy {
        Distance,
        FOV,
        Health
    }
}