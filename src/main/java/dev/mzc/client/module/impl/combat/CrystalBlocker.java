package dev.mzc.client.module.impl.combat;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.manager.Managers;
import dev.mzc.client.manager.impl.RotationManager;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.player.FindItemResult;
import dev.mzc.client.utils.player.InvUtil;
import dev.mzc.client.utils.rotation.MovementFix;
import dev.mzc.client.utils.rotation.RotationUtil;
import dev.mzc.client.utils.vector.Rotation;
import dev.mzc.client.utils.world.BlockUtil;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;

import java.util.Comparator;

public class CrystalBlocker extends Module {
    private final NumberValue<Double> range = new NumberValue<>("Range", "检测水晶的范围", 4.0, 1.0, 6.0, 0.1);
    private final EnumValue<RotateMode> rotate = new EnumValue<>("Rotate", "旋转模式", RotateMode.Silent);
    private final EnumValue<SwitchMode> switchMode = new EnumValue<>("Switch", "切换方式", SwitchMode.Visible);
    private final NumberValue<Integer> delay = new NumberValue<>("Delay", "放置间隔(Ticks)", 2, 0, 20, 1);
    private final NumberValue<Integer> visibleSwapBackDelay = new NumberValue<>("SwapBackDelay", "切回延迟(Ticks)", 0, 0, 20, 1, () -> switchMode.is(SwitchMode.Visible));
    private final NumberValue<Double> silentSpeed = new NumberValue<>("SilentSpeed", "静默旋转速度", 10.0, 0.5, 20.0, 0.1, () -> rotate.is(RotateMode.Silent));

    private int timer = 0;
    private boolean waitingSwapBack = false;
    private int swapBackTicks = 0;
    private int savedOldSlot = -1;

    public enum RotateMode {
        None, Normal, Silent
    }
    public enum SwitchMode {
        Visible, Silent
    }

    public CrystalBlocker() {
        super("CrystalBlocker", "水晶阻挡", Category.Combat);
        this.setType(ModuleType.Safe);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (!mc.player.isOnGround()) return;

        if (waitingSwapBack) {
            if (swapBackTicks > 0) {
                swapBackTicks--;
                if (swapBackTicks > 0) return;
            }
            if (savedOldSlot >= 0) {
                mc.player.getInventory().setSelectedSlot(savedOldSlot);
                if (mc.interactionManager != null) mc.interactionManager.syncSelectedSlot();
            }
            waitingSwapBack = false;
            savedOldSlot = -1;
            return;
        }

        if (timer > 0) {
            timer--;
        }

        // 1. 寻找周围 4x4 范围内的水晶
        EndCrystalEntity targetCrystal = mc.world.getEntitiesByClass(EndCrystalEntity.class, 
                mc.player.getBoundingBox().expand(range.get()), 
                crystal -> {
                    // 检查是否在同一高度 (Y 坐标差值小于 1)
                    return Math.abs(crystal.getY() - mc.player.getY()) < 1.0;
                })
                .stream()
                .min(Comparator.comparingDouble(c -> mc.player.distanceTo(c)))
                .orElse(null);

        if (targetCrystal == null) return;

        // 2.1 如果玩家与水晶之间已有黑曜石阻挡，则不再放置
        Vec3d eyes = mc.player.getEyePos();
        Vec3d towards = targetCrystal.getPos().add(0, 0.5, 0);
        HitResult hit = mc.world.raycast(new RaycastContext(
                eyes,
                towards,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                mc.player
        ));
        if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = ((BlockHitResult) hit).getBlockPos();
            if (mc.world.getBlockState(hitPos).isOf(Blocks.OBSIDIAN)) return;
        }

        // 2. 计算玩家和水晶中间的方块位置
        Vec3d crystalPos = targetCrystal.getPos();
        Vec3d playerPos = mc.player.getPos();
        
        // 取中间点
        Vec3d midPoint = playerPos.lerp(crystalPos, 0.5);
        BlockPos placePos = BlockPos.ofFloored(midPoint).withY(mc.player.getBlockY());

        // 如果中间点就是玩家脚下或水晶脚下，尝试寻找更合适的阻挡点
        if (placePos.equals(mc.player.getBlockPos()) || placePos.equals(targetCrystal.getBlockPos())) {
             // 简单的方向偏移寻找
             Vec3d dir = crystalPos.subtract(playerPos).normalize();
             placePos = BlockPos.ofFloored(playerPos.add(dir)).withY(mc.player.getBlockY());
        }

        // 3. 检查是否可以放置
        if (!BlockUtil.canPlaceAt(placePos)) return;
        if (!BlockUtil.solid(placePos.down())) return;

        // 4. 寻找黑曜石
        FindItemResult obsidian = InvUtil.findInHotbar(Items.OBSIDIAN);
        if (!obsidian.found()) return;

        // 5. 旋转与放置逻辑
        Direction side = BlockUtil.getPlaceSide(placePos);
        if (side == null) return;

        // 计算旋转
        Rotation rot = RotationUtil.calculate(placePos);
        boolean readyToPlace = true;

        if (rotate.get() == RotateMode.Normal) {
            Rotation patched = RotationUtil.applySensitivityPatch(rot);
            float clampedPitch = MathHelper.clamp(patched.pitch, -90.0f, 90.0f);
            mc.player.setYaw(patched.yaw);
            mc.player.setHeadYaw(patched.yaw);
            mc.player.setPitch(clampedPitch);
        } else if (rotate.get() == RotateMode.Silent) {
            // 每 tick 都更新旋转，保持平滑
            Managers.ROTATION.setRotations(rot, silentSpeed.get(), MovementFix.OFF, RotationManager.Priority.Highest);
            
            // 检查当前旋转是否已经足够接近目标
            if (Managers.ROTATION.rotations != null) {
                double yawDiff = Math.abs(MathHelper.wrapDegrees(Managers.ROTATION.rotations.yaw - rot.yaw));
                double pitchDiff = Math.abs(Managers.ROTATION.rotations.pitch - rot.pitch);
                // 如果角度差异过大，暂停放置，等待旋转对齐
                if (yawDiff > 15 || pitchDiff > 15) {
                    readyToPlace = false;
                }
            }
        }

        // 执行放置
        if (readyToPlace && timer <= 0) {
            placeBlock(placePos, obsidian, side);
            timer = delay.get();
        }
    }

    private void placeBlock(BlockPos pos, FindItemResult item, Direction side) {
        // 切换物品
        int oldSlot = mc.player.getInventory().selectedSlot;
        if (switchMode.get() == SwitchMode.Visible) {
            int target = item.slot();
            boolean needSwitch = oldSlot != target;
            if (needSwitch) {
                mc.player.getInventory().setSelectedSlot(target);
                mc.interactionManager.syncSelectedSlot();
            }
            BlockUtil.clickBlock(pos.offset(side), side.getOpposite(), false, false);
            mc.player.swingHand(Hand.MAIN_HAND);
            if (needSwitch) {
                int backDelay = visibleSwapBackDelay.get();
                if (backDelay > 0) {
                    waitingSwapBack = true;
                    swapBackTicks = backDelay;
                    savedOldSlot = oldSlot;
                } else {
                    mc.player.getInventory().setSelectedSlot(oldSlot);
                    mc.interactionManager.syncSelectedSlot();
                }
            }
        } else {
            boolean swapped = InvUtil.invSwap(item.slot());
            if (!swapped) return;
            BlockUtil.clickBlock(pos.offset(side), side.getOpposite(), false, true);
            mc.player.swingHand(Hand.MAIN_HAND);
            InvUtil.invSwapBack();
        }
    }
}
