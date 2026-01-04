package dev.sakura.client.module.impl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.events.EventType;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.render.Render2DEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.nanovg.NanoVGRenderer;
import dev.sakura.client.nanovg.font.FontLoader;
import dev.sakura.client.nanovg.util.NanoVGHelper;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.render.Shader2DUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

import static org.lwjgl.nanovg.NanoVG.*;

public class NameTags extends Module {
    public static NameTags INSTANCE;

    private final NumberValue<Double> scaling = new NumberValue<>("Size", "大小", 4.0, 0.1, 10.0, 0.1);
    private final NumberValue<Double> minScale = new NumberValue<>("MinSize", "最小大小", 0.5, 0.1, 5.0, 0.1);
    private final BoolValue self = new BoolValue("Self", "自身", false);
    private final BoolValue armor = new BoolValue("Armor", "装备", true);
    private final BoolValue enchants = new BoolValue("Enchants", "附魔", true, armor::get);
    private final BoolValue durability = new BoolValue("Durability", "耐久", true, armor::get);
    private final BoolValue itemName = new BoolValue("ItemName", "物品名", true);
    private final BoolValue ping = new BoolValue("Ping", "延迟", true);
    private final BoolValue health = new BoolValue("Health", "血量", true);
    private final BoolValue pops = new BoolValue("Pops", "图腾消耗", true);
    private final BoolValue blur = new BoolValue("Blur", "模糊", true);
    private final NumberValue<Double> blurStrength = new NumberValue<>("BlurStrength", "模糊强度", 10.0, 1.0, 25.0, 0.5, blur::get);

    private final Map<UUID, Integer> popCounts = new HashMap<>();

    public NameTags() {
        super("NameTags", "名牌显示", Category.Render);
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        popCounts.clear();
    }

    @Override
    protected void onDisable() {
        popCounts.clear();
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (event.getType() != EventType.RECEIVE) return;

        if (event.getPacket() instanceof EntityStatusS2CPacket packet) {
            if (packet.getStatus() == 35) {
                var entity = packet.getEntity(mc.world);
                if (entity instanceof PlayerEntity player) {
                    popCounts.put(player.getUuid(), popCounts.getOrDefault(player.getUuid(), 0) + 1);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRender2D(Render2DEvent event) {
        if (mc.player == null || mc.world == null) return;

        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player && !self.get()) continue;
            if (!player.isAlive()) continue;

            double x = player.prevX + (player.getX() - player.prevX) * mc.getRenderTickCounter().getTickDelta(true);
            double y = player.prevY + (player.getY() - player.prevY) * mc.getRenderTickCounter().getTickDelta(true);
            double z = player.prevZ + (player.getZ() - player.prevZ) * mc.getRenderTickCounter().getTickDelta(true);

            Vec3d pos = new Vec3d(x, y + player.getBoundingBox().getLengthY() + 0.5, z);
            Vec3d screenPos = Render3DUtil.worldToScreen(pos);

            if (screenPos != null) {
                render(event.getContext(), player, (float) screenPos.x, (float) screenPos.y, (float) screenPos.z);
            }
        }
    }

    private void drawNvg(float posX, float posY, float scale, Consumer<Long> drawer) {
        NanoVGRenderer.INSTANCE.draw(vg -> {
            nvgSave(vg);
            nvgTranslate(vg, posX, posY);
            nvgScale(vg, scale, scale);
            nvgTranslate(vg, -posX, -posY);
            drawer.accept(vg);
            nvgRestore(vg);
        });
    }

    private void render(DrawContext context, PlayerEntity player, float posX, float posY, float posZ) {
        final String name = player.getName().getString();
        final float hp = player.getHealth() + player.getAbsorptionAmount();
        final int playerPops = popCounts.getOrDefault(player.getUuid(), 0);
        final int playerPing = getPlayerPing(player);

        List<ItemStack> stacks = getPlayerEquipment(player);
        final String mainHandName = getPlayerMainHandName(player);

        final float headSize = 28;
        final float itemSize = 16;
        final float itemSpacing = 2;
        final float padding = 8;
        final float headerHeight = 18;
        final float radius = 6;
        final float enchantFontSize = 7;
        final float durFontSize = 8;
        final float infoFontSize = 11;

        int maxEnchants = calculateMaxEnchants(stacks);
        float enchantHeight = maxEnchants * (enchantFontSize + 1);
        float durHeight = durability.get() ? durFontSize + 4 : 0;
        float nameHeight = (itemName.get() && !mainHandName.isEmpty()) ? 14 : 0;
        float itemsWidth = 6 * itemSize + 5 * itemSpacing;

        float contentHeight = enchantHeight + itemSize + durHeight + nameHeight + padding;
        float contentWidth = headSize + padding + itemsWidth + padding;

        float totalWidth = contentWidth + padding * 2;
        float totalHeight = headerHeight + contentHeight + padding;

        float boxX = posX - totalWidth / 2;
        float boxY = posY - totalHeight;

        // Calculate scale
        float calculatedScale = posZ * scaling.get().floatValue();
        final float finalScale = Math.max(calculatedScale, minScale.get().floatValue());

        // Draw Blur
        if (blur.get()) {
            float scaledWidth = totalWidth * finalScale;
            float scaledHeight = totalHeight * finalScale;
            float scaledRadius = radius * finalScale;
            float scaledBoxX = posX - (totalWidth / 2 * finalScale);
            float scaledBoxY = posY - (totalHeight * finalScale);

            Shader2DUtil.drawRoundedBlur(context.getMatrices(),
                    scaledBoxX, scaledBoxY, scaledWidth, scaledHeight, scaledRadius,
                    new Color(0, 0, 0, 0),
                    blurStrength.get().floatValue(), 1.0f
            );
        }

        // Draw Main Panel
        drawNvg(posX, posY, finalScale, vg -> {
            drawPanelBackground(boxX, boxY, totalWidth, totalHeight, headerHeight, radius);
            drawPanelInfo(boxX, boxY, totalWidth, headerHeight, padding, infoFontSize, name, hp, playerPing, playerPops);
        });

        float headX = boxX + padding;
        float headY = boxY + headerHeight + padding;

        // Draw Head
        drawPlayerHead(context, player, posX, posY, finalScale, headX, headY, headSize);

        // Draw Items
        if (armor.get()) {
            drawEquipment(context, stacks, mainHandName, posX, posY, finalScale, headX, headY, headSize, padding, enchantHeight, itemSize, itemSpacing, durHeight, durFontSize, enchantFontSize, itemsWidth);
        }
    }

    private List<ItemStack> getPlayerEquipment(PlayerEntity player) {
        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(player.getMainHandStack());
        stacks.add(player.getInventory().armor.get(3));
        stacks.add(player.getInventory().armor.get(2));
        stacks.add(player.getInventory().armor.get(1));
        stacks.add(player.getInventory().armor.get(0));
        stacks.add(player.getOffHandStack());
        return stacks;
    }

    private String getPlayerMainHandName(PlayerEntity player) {
        return (itemName.get() && !player.getMainHandStack().isEmpty())
                ? getEnglishItemName(player.getMainHandStack()) : "";
    }

    private int calculateMaxEnchants(List<ItemStack> stacks) {
        if (!enchants.get()) return 0;
        int maxEnchants = 0;
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                var ench = stack.get(DataComponentTypes.ENCHANTMENTS);
                if (ench != null) {
                    int count = 0;
                    for (var e : ench.getEnchantments()) {
                        String s = getEnchantShortName(e);
                        if (!s.isEmpty()) count++;
                    }
                    maxEnchants = Math.max(maxEnchants, count);
                }
            }
        }
        return maxEnchants;
    }

    private void drawPanelBackground(float x, float y, float width, float height, float headerHeight, float radius) {
        NanoVGHelper.drawRoundRect(x, y, width, height, radius, new Color(158, 158, 158, 48));
        NanoVGHelper.drawRoundRect(x + 2, y + 2, width - 4, headerHeight - 2, radius - 2, new Color(171, 171, 172, 48));
    }

    private void drawPanelInfo(float x, float y, float width, float headerHeight, float padding, float fontSize, String name, float hp, int pingVal, int popsVal) {
        float headerY = y + headerHeight - 5;

        // Name
        NanoVGHelper.drawString(name, x + padding, headerY, FontLoader.bold(11), fontSize, Color.WHITE);

        // Health
        if (health.get()) {
            String healthStr = String.format("%.1f", hp);
            Color healthColor = getHealthColor(hp);
            float healthWidth = NanoVGHelper.getTextWidth(healthStr, FontLoader.regular(11), fontSize);
            float healthX = x + width / 2 - healthWidth / 2;
            NanoVGHelper.drawString(healthStr, healthX, headerY, FontLoader.bold(11), fontSize, healthColor);
        }

        // Ping
        if (ping.get()) {
            String pingStr = pingVal + "ms";
            Color pingColor = getPingColor(pingVal);
            float pingWidth = NanoVGHelper.getTextWidth(pingStr, FontLoader.regular(10), fontSize - 1);
            float pingX = x + width - padding - pingWidth;
            float signalX = pingX - 18;

            drawSignalIcon(signalX, y + headerHeight / 2 - 5, 12, pingVal, pingColor);
            NanoVGHelper.drawString(pingStr, pingX, headerY, FontLoader.regular(10), fontSize - 1, pingColor);
        }

        // Pops
        if (pops.get() && popsVal > 0) {
            String popStr = "-" + popsVal;
            float healthWidth = health.get() ? NanoVGHelper.getTextWidth(String.format("%.1f", hp), FontLoader.regular(11), fontSize) : 0;
            float popX = x + width / 2 + healthWidth / 2 + 8;
            NanoVGHelper.drawString(popStr, popX, headerY, FontLoader.bold(11), fontSize, new Color(255, 80, 80));
        }
    }

    private void drawPlayerHead(DrawContext context, PlayerEntity player, float posX, float posY, float scale, float headX, float headY, float headSize) {
        PlayerListEntry entry = mc.getNetworkHandler() == null ? null : mc.getNetworkHandler().getPlayerListEntry(player.getUuid());

        if (entry != null && entry.getSkinTextures() != null) {
            drawNvg(posX, posY, scale, vg -> NanoVGHelper.drawRoundRect(headX - 1, headY - 1, headSize + 2, headSize + 2, 4, new Color(172, 172, 174, 47)));

            context.getMatrices().push();
            context.getMatrices().translate(posX, posY, 0);
            context.getMatrices().scale(scale, scale, 1f);
            context.getMatrices().translate(-posX, -posY, 0);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            context.drawTexture(RenderLayer::getGuiTextured,
                    entry.getSkinTextures().texture(),
                    (int) headX, (int) headY,
                    8, 8,
                    (int) headSize, (int) headSize,
                    8, 8,
                    64, 64);
            context.drawTexture(RenderLayer::getGuiTextured,
                    entry.getSkinTextures().texture(),
                    (int) headX, (int) headY,
                    40, 8,
                    (int) headSize, (int) headSize,
                    8, 8,
                    64, 64);

            RenderSystem.disableBlend();
            context.getMatrices().pop();
        } else {
            drawNvg(posX, posY, scale, vg -> NanoVGHelper.drawRoundRect(headX, headY, headSize, headSize, 4, new Color(168, 168, 170, 52)));
        }
    }

    private void drawEquipment(DrawContext context, List<ItemStack> stacks, String mainHandName, float posX, float posY, float scale, float headX, float headY, float headSize, float padding, float enchantHeight, float itemSize, float itemSpacing, float durHeight, float durFontSize, float enchantFontSize, float itemsWidth) {
        float itemAreaX = headX + headSize + padding;
        float itemY = headY + enchantHeight;
        float currentItemX = itemAreaX;

        context.getMatrices().push();
        context.getMatrices().translate(posX, posY, 0);
        context.getMatrices().scale(scale, scale, 1f);
        context.getMatrices().translate(-posX, -posY, 0);

        for (ItemStack stack : stacks) {
            final float itemX = currentItemX;

            if (!stack.isEmpty()) {
                DiffuseLighting.disableGuiDepthLighting();
                context.drawItem(stack, (int) itemX, (int) itemY);

                // Draw Count
                if (stack.getCount() > 1) {
                    drawNvg(posX, posY, scale, vg -> {
                        String countStr = String.valueOf(stack.getCount());
                        NanoVGHelper.drawString(countStr, itemX + 9, itemY + 12, FontLoader.bold(7), 7, Color.WHITE);
                    });
                }

                // Draw Durability
                if (durability.get() && stack.getMaxDamage() > 0) {
                    drawItemDurability(posX, posY, scale, stack, itemX, itemY, itemSize, durFontSize);
                }

                // Draw Enchants
                if (enchants.get()) {
                    drawItemEnchants(posX, posY, scale, stack, itemX, itemY, enchantFontSize);
                }
            }
            currentItemX += itemSize + itemSpacing;
        }

        context.getMatrices().pop();

        if (itemName.get() && !mainHandName.isEmpty()) {
            float nameY = itemY + itemSize + durHeight + 6;
            drawNvg(posX, posY, scale, vg -> {
                float nameWidth = NanoVGHelper.getTextWidth(mainHandName, FontLoader.regular(10), 10);
                float nameX = itemAreaX + itemsWidth / 2 - nameWidth / 2;
                NanoVGHelper.drawString(mainHandName, nameX, nameY + 8, FontLoader.regular(10), 10, new Color(184, 184, 186, 184));
            });
        }
    }

    private void drawItemDurability(float posX, float posY, float scale, ItemStack stack, float itemX, float itemY, float itemSize, float durFontSize) {
        float durabilityVal = stack.getMaxDamage() - stack.getDamage();
        int percent = (int) ((durabilityVal / (float) stack.getMaxDamage()) * 100F);
        Color durColor = getDurabilityColor(percent);

        drawNvg(posX, posY, scale, vg -> {
            String percentStr = String.valueOf(percent);
            float strWidth = NanoVGHelper.getTextWidth(percentStr, FontLoader.regular(8), durFontSize);
            NanoVGHelper.drawString(percentStr, itemX + 8 - strWidth / 2, itemY + itemSize + durFontSize + 2, FontLoader.regular(8), durFontSize, durColor);
        });
    }

    private void drawItemEnchants(float posX, float posY, float scale, ItemStack stack, float itemX, float itemY, float enchantFontSize) {
        var enchantments = stack.get(DataComponentTypes.ENCHANTMENTS);
        if (enchantments != null && !enchantments.isEmpty()) {
            drawNvg(posX, posY, scale, vg -> {
                float enchantY = itemY - 2;
                for (var enchEntry : enchantments.getEnchantments()) {
                    String shortName = getEnchantShortName(enchEntry);
                    int level = enchantments.getLevel(enchEntry);
                    if (shortName.isEmpty()) continue;

                    String levelStr = level > 1 ? String.valueOf(level) : "";
                    float shortWidth = NanoVGHelper.getTextWidth(shortName, FontLoader.regular(7), enchantFontSize);
                    NanoVGHelper.drawString(shortName, itemX + 8 - shortWidth / 2, enchantY, FontLoader.regular(7), enchantFontSize, new Color(187, 187, 191, 52));
                    if (!levelStr.isEmpty()) {
                        NanoVGHelper.drawString(levelStr, itemX + 8 + shortWidth / 2, enchantY, FontLoader.regular(7), enchantFontSize, new Color(255, 100, 100));
                    }
                    enchantY -= (enchantFontSize + 1);
                }
            });
        }
    }

    private String getEnglishItemName(ItemStack stack) {
        String key = stack.getItem().getTranslationKey();
        String name = key.substring(key.lastIndexOf('.') + 1);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                result.append(' ');
            } else if (i == 0 || name.charAt(i - 1) == '_') {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private void drawSignalIcon(float x, float y, float size, int ping, Color color) {
        int bars;
        if (ping <= 50) {
            bars = 4;
        } else if (ping <= 100) {
            bars = 3;
        } else if (ping <= 200) {
            bars = 2;
        } else if (ping <= 400) {
            bars = 1;
        } else {
            bars = 0;
        }

        float barWidth = size / 5.5f;
        float spacing = size / 12f;
        float maxHeight = size;

        for (int i = 0; i < 4; i++) {
            float barHeight = maxHeight * (0.25f + 0.22f * i);
            float barX = x + i * (barWidth + spacing);
            float barY = y + maxHeight - barHeight;

            Color barColor;
            if (i < bars) {
                barColor = color;
            } else {
                barColor = new Color(163, 162, 162, 60);
            }

            NanoVGHelper.drawRoundRect(barX, barY, barWidth, barHeight, 1, barColor);
        }
    }

    private String getEnchantShortName(RegistryEntry<Enchantment> enchant) {
        String id = enchant.getIdAsString();
        if (id.contains("blast_protection")) return "Bla";
        if (id.contains("fire_protection")) return "Fir";
        if (id.contains("projectile_protection")) return "Pro";
        if (id.contains("protection")) return "Pro";
        if (id.contains("thorns")) return "Tho";
        if (id.contains("sharpness")) return "Sha";
        if (id.contains("efficiency")) return "Eff";
        if (id.contains("unbreaking")) return "Unb";
        if (id.contains("power")) return "Pow";
        if (id.contains("mending")) return "Men";
        if (id.contains("feather_falling")) return "Fea";
        if (id.contains("depth_strider")) return "Dep";
        if (id.contains("frost_walker")) return "Fro";
        if (id.contains("soul_speed")) return "Sou";
        if (id.contains("swift_sneak")) return "Swi";
        if (id.contains("respiration")) return "Res";
        if (id.contains("aqua_affinity")) return "Aqu";
        if (id.contains("fire_aspect")) return "Fir";
        if (id.contains("looting")) return "Loo";
        if (id.contains("knockback")) return "Kno";
        if (id.contains("smite")) return "Smi";
        if (id.contains("bane")) return "Ban";
        if (id.contains("sweeping")) return "Swe";
        if (id.contains("fortune")) return "For";
        if (id.contains("silk_touch")) return "Sil";
        if (id.contains("vanishing")) return "Van";
        if (id.contains("binding")) return "Bin";
        if (id.contains("loyalty")) return "Loy";
        if (id.contains("riptide")) return "Rip";
        if (id.contains("channeling")) return "Cha";
        if (id.contains("impaling")) return "Imp";
        if (id.contains("multishot")) return "Mul";
        if (id.contains("quick_charge")) return "Qui";
        if (id.contains("piercing")) return "Pie";
        return "";
    }

    private int getPlayerPing(PlayerEntity player) {
        if (mc.getNetworkHandler() == null) return 0;
        PlayerListEntry entry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        return entry != null ? entry.getLatency() : 0;
    }

    private Color getHealthColor(float health) {
        if (health >= 15) return new Color(100, 255, 100);
        if (health >= 10) return new Color(255, 255, 100);
        if (health >= 5) return new Color(255, 165, 0);
        return new Color(255, 100, 100);
    }

    private Color getPingColor(int ping) {
        if (ping <= 100) return new Color(100, 255, 100);
        if (ping <= 200) return new Color(255, 255, 100);
        if (ping <= 300) return new Color(255, 165, 0);
        return new Color(255, 100, 100);
    }

    private Color getDurabilityColor(int percent) {
        if (percent >= 70) return new Color(100, 255, 100);
        if (percent >= 30) return new Color(255, 255, 100);
        return new Color(255, 100, 100);
    }
}