package dev.mzc.client.module.impl.player.mine;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.render.Render3DEvent;
import dev.mzc.client.manager.Managers;
import dev.mzc.client.manager.impl.RotationManager;
import dev.mzc.client.Sakura;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.player.InvUtil;
import dev.mzc.client.utils.render.Render3DUtil;
import dev.mzc.client.utils.rotation.MovementFix;
import dev.mzc.client.utils.rotation.RotationUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Nucker extends Module {
    private final NumberValue<Double> range = new NumberValue<>("Range", "挖掘范围", 5.0, 1.0, 6.0, 0.1);
    private final BoolValue rotate = new BoolValue("Rotate", "自动瞄准", true);
    private final BoolValue autoSwap = new BoolValue("Auto Swap", "自动切工具", true);
    private final BoolValue silentSwap = new BoolValue("Silent Swap", "静默切换", true, autoSwap::get);
    private final BoolValue usePacketMine = new BoolValue("PacketMine", "联动发包挖掘", false);
    private final BoolValue swing = new BoolValue("Swing", "挥手动画", true);
    private final BoolValue avoidSelf = new BoolValue("Avoid Self", "不挖脚下及周围", true);
    private final BoolValue city = new BoolValue("City", "自动破甲(City)", true);
    private final NumberValue<Integer> targetRange = new NumberValue<>("Target Range", "目标搜索范围", 10, 1, 20, 1, city::get);
    
    private final BoolValue render = new BoolValue("Render", "渲染", true);
    private final BoolValue renderText = new BoolValue("Render Text", "显示百分比", true, render::get);
    private final EnumValue<RenderMode> renderMode = new EnumValue<>("Render Mode", "渲染模式", RenderMode.Zoom, render::get);

    private final ColorValue renderColor = new ColorValue("Render Color", "渲染颜色", new Color(255, 0, 0, 50), render::get);
    private final ColorValue readyColor = new ColorValue("Ready Color", "就绪颜色", new Color(0, 255, 0, 50), render::get);

    private BlockPos currentTarget = null;
    private long startTime = -1;
    private boolean mining = false;

    public enum RenderMode {
        Normal, Zoom
    }

    public Nucker() {
        super("Nucker", "自动挖掘", Category.Player);
        this.setType(ModuleType.Hack);
    }

    @Override
    public void onDisable() {
        resetMining();
    }

    private void resetMining() {
        if (mining && currentTarget != null) {
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, currentTarget, Direction.UP));
        }
        currentTarget = null;
        startTime = -1;
        mining = false;
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (nullCheck()) return;

        if (currentTarget != null) {
            // 检查目标是否失效
            if (mc.world.isAir(currentTarget) || mc.player.squaredDistanceTo(currentTarget.toCenterPos()) > Math.pow(range.get(), 2)) {
                resetMining();
            }
        }

        if (currentTarget == null) {
            findTarget();
        }

        if (currentTarget != null) {
            handleMining();
        }
    }

    private void findTarget() {
        List<BlockPos> targets = new ArrayList<>();

        // 自动破甲逻辑
        if (city.get()) {
            mc.world.getPlayers().stream()
                    .filter(p -> p != mc.player && p.isAlive() && mc.player.distanceTo(p) <= targetRange.get())
                    .forEach(p -> {
                        BlockPos pos = p.getBlockPos();
                        BlockPos[] surround = {
                                pos.north(), pos.south(), pos.east(), pos.west()
                        };
                        for (BlockPos neighbor : surround) {
                            if (mc.player.squaredDistanceTo(neighbor.toCenterPos()) <= Math.pow(range.get(), 2) && canMine(neighbor)) {
                                targets.add(neighbor);
                            }
                        }
                    });
        }

        // 基础方块挖掘逻辑
        int r = range.get().intValue() + 1; // 稍微扩大搜索半径以确保覆盖
        BlockPos playerPos = mc.player.getBlockPos();

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    
                    // 距离检查
                    if (mc.player.squaredDistanceTo(pos.toCenterPos()) > Math.pow(range.get(), 2)) continue;

                    // 严格限制：只挖掘玩家 Y 坐标及以上的方块
                    if (pos.getY() < playerPos.getY()) continue;

                    // 精确排除玩家脚下坐标 (避免误触)
                    if (avoidSelf.get()) {
                        if (pos.getX() == playerPos.getX() && pos.getZ() == playerPos.getZ() && pos.getY() == playerPos.getY()) {
                            continue;
                        }
                    }
                    
                    if (canMine(pos)) targets.add(pos);
                }
            }
        }

        if (!targets.isEmpty()) {
            // 按照距离排序，优先挖掘最近的
            targets.sort((a, b) -> Double.compare(mc.player.squaredDistanceTo(a.toCenterPos()), mc.player.squaredDistanceTo(b.toCenterPos())));
            currentTarget = targets.get(0);
            startTime = System.currentTimeMillis();
            mining = false;
        }
    }

    private boolean canMine(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        return !state.isAir() && state.getHardness(mc.world, pos) != -1.0F;
    }

    private void handleMining() {
        if (currentTarget == null) return;

        // 联动 PacketMine
        if (usePacketMine.get()) {
            PacketMine packetMine = Sakura.MODULES.getModule(PacketMine.class);
            if (packetMine != null) {
                if (!packetMine.isEnabled()) {
                    packetMine.setState(true);
                }
                
                // 检查是否已经是当前挖掘目标，避免重复重置
                BlockData data = packetMine.getBlockData();
                if (data == null || !data.getCurrentPos().equals(currentTarget)) {
                    packetMine.hookPos(currentTarget, true);
                }
                
                resetMining(); // 移交给 PacketMine 处理后重置
                return;
            }
        }

        // 自动瞄准
        if (rotate.get()) {
            Managers.ROTATION.setRotations(RotationUtil.calculate(currentTarget.toCenterPos()), 10, MovementFix.OFF, RotationManager.Priority.Medium);
        }

        // 开始挖掘
        if (!mining) {
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, currentTarget, Direction.UP));
            if (swing.get()) mc.player.swingHand(Hand.MAIN_HAND);
            mining = true;
        }

        // 挖掘过程中的挥手动画
        if (swing.get() && mc.player.age % 4 == 0) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }

        long breakTime = (long) calcBreakTime(currentTarget);
        if (System.currentTimeMillis() - startTime >= breakTime) {
            int oldSlot = mc.player.getInventory().selectedSlot;
            int bestSlot = -1;

            if (autoSwap.get()) {
                bestSlot = InvUtil.findFastestTool(mc.world.getBlockState(currentTarget), false).slot();
                if (bestSlot != -1 && bestSlot != oldSlot) {
                    if (silentSwap.get()) {
                        InvUtil.swap(bestSlot, true);
                    } else {
                        mc.player.getInventory().selectedSlot = bestSlot;
                    }
                }
            }

            // 完成挖掘
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, currentTarget, Direction.UP));
            if (swing.get()) mc.player.swingHand(Hand.MAIN_HAND);
            
            if (autoSwap.get() && silentSwap.get() && bestSlot != -1) {
                InvUtil.swapBack();
            }
            
            resetMining(); // 重置以寻找下一个目标
        }
    }

    private float calcBreakTime(BlockPos pos) {
        BlockState state = mc.world.getBlockState(pos);
        float hardness = state.getHardness(mc.world, pos);
        float breakSpeed = getBreakSpeed(state);
        if (breakSpeed == -1.0f) return -1.0f;
        float relativeDamage = breakSpeed / hardness / 30.0f;
        int ticks = MathHelper.ceil(0.7f / relativeDamage);
        return ticks * 50.0f;
    }

    private float getBreakSpeed(BlockState blockState) {
        float maxSpeed = 1.0f;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;
            float speed = stack.getMiningSpeedMultiplier(blockState);
            if (speed > 1.0f) {
                var enchantmentRegistry = mc.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
                RegistryEntry<Enchantment> efficiencyEntry = enchantmentRegistry.getOrThrow(Enchantments.EFFICIENCY);
                int efficiencyLevel = EnchantmentHelper.getLevel(efficiencyEntry, stack);
                if (efficiencyLevel > 0) {
                    speed += (float) (efficiencyLevel * efficiencyLevel + 1);
                }
                if (speed > maxSpeed) maxSpeed = speed;
            }
        }
        return maxSpeed;
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (!render.get()) return;
        if (currentTarget != null) {
            long breakTime = (long) calcBreakTime(currentTarget);
            double progress = (double) (System.currentTimeMillis() - startTime) / breakTime;
            progress = MathHelper.clamp(progress, 0.0, 1.0);

            Box box = new Box(currentTarget);
            
            if (renderMode.get() == RenderMode.Zoom) {
                box = box.expand((progress - 1.0) / 2.0);
            }
            
            // 实现平滑颜色渐变：从 renderColor 到 readyColor
            Color c1 = renderColor.get();
            Color c2 = readyColor.get();
            int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * progress);
            int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * progress);
            int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * progress);
            int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * progress);
            Color color = new Color(r, g, b, a);
            
            Render3DUtil.drawFilledBox(event.getMatrices(), box, color);
            Render3DUtil.drawBoxOutline(event.getMatrices(), box, color.getRGB(), 1.0f);
            
            if (renderText.get()) {
                Vec3d center = box.getCenter();
                Render3DUtil.drawText(String.format("%.0f%%", progress * 100), center, 0, 0, 0, Color.WHITE);
            }
        }
    }
}
