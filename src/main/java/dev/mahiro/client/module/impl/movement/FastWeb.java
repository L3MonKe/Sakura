package dev.mahiro.client.module.impl.movement;

import dev.mahiro.client.events.player.MotionEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.player.MovementUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.CobwebBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class FastWeb extends Module {
    public FastWeb() {
        super("FastWeb", "快速的网", Category.Movement);
    }

    public enum Mode {
        Grim
    }

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Grim);

    private final BoolValue onlyGround = new BoolValue("OnlyOnGround", "仅在地面", false, () -> mode.is(Mode.Grim));
    private final BoolValue motionY = new BoolValue("MotionY", "垂直运动", false, () -> mode.is(Mode.Grim));

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (mc.player == null || mc.world == null) return;

        if (!isInWeb()) {
            return;
        }

        if (MovementUtil.isMoving()) {
            if (mc.player.isOnGround() || !onlyGround.get()) {
                MovementUtil.strafe(0.63);
            }

            if (motionY.get()) {
                mc.player.setVelocity(
                        mc.player.getVelocity().x,
                        0.1f,
                        mc.player.getVelocity().z
                );
            }
        }
    }

    private boolean isInWeb() {
        BlockPos playerPos = null;
        if (mc.player != null) {
            playerPos = mc.player.getBlockPos();
        }
        if (mc.world != null && mc.world.getBlockState(playerPos).getBlock() instanceof CobwebBlock) {
            return true;
        }

        BlockPos belowPos = null;
        if (playerPos != null) {
            belowPos = playerPos.down();
        }
        if (mc.world != null && mc.world.getBlockState(belowPos).getBlock() instanceof CobwebBlock) {
            return true;
        }

        Box playerBox = null;
        if (mc.player != null) {
            playerBox = mc.player.getBoundingBox();
        }

        Box expandedBox = null;
        if (playerBox != null) {
            expandedBox = playerBox.expand(0.0);
        }

        int minX = 0;
        if (expandedBox != null) {
            minX = (int) Math.floor(expandedBox.minX);
        }
        int minY = 0;
        if (expandedBox != null) {
            minY = (int) Math.floor(expandedBox.minY);
        }
        int minZ = 0;
        if (expandedBox != null) {
            minZ = (int) Math.floor(expandedBox.minZ);
        }
        int maxX = 0;
        if (expandedBox != null) {
            maxX = (int) Math.floor(expandedBox.maxX);
        }
        int maxY = 0;
        if (expandedBox != null) {
            maxY = (int) Math.floor(expandedBox.maxY);
        }
        int maxZ = 0;
        if (expandedBox != null) {
            maxZ = (int) Math.floor(expandedBox.maxZ);
        }

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (mc.world != null && mc.world.getBlockState(pos).getBlock() instanceof CobwebBlock) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}