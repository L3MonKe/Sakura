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
import net.minecraft.util.math.MathHelper;

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
        if (nullCheck()) return;

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
        BlockPos playerPos = mc.player.getBlockPos();
        if (mc.world.getBlockState(playerPos).getBlock() instanceof CobwebBlock) {
            return true;
        }

        BlockPos belowPos = playerPos.down();
        if (mc.world.getBlockState(belowPos).getBlock() instanceof CobwebBlock) {
            return true;
        }

        Box box = mc.player.getBoundingBox().expand(0.0);

        for (int x = MathHelper.floor(box.minX); x <= box.maxX; x++) {
            for (int y = MathHelper.floor(box.minY); y <= box.maxY; y++) {
                for (int z = MathHelper.floor(box.minZ); z <= box.maxZ; z++) {
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