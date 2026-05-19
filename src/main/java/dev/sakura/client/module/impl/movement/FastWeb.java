package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.player.MotionEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
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

        if (MoveUtil.isMoving()) {
            if (mc.player.isOnGround() || !onlyGround.get()) {
                MoveUtil.strafe(0.63);
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

    @Override
    public String getSuffix() {
        return mode.get().name();
    }

    private boolean isInWeb() {
        BlockPos playerPos = mc.player.getBlockPos();
        if (mc.world.getBlockState(playerPos).getBlock() instanceof CobwebBlock) {
            return true;
        }

        Box box = mc.player.getBoundingBox().expand(0.0);

        for (int x = MathHelper.floor(box.minX); x <= box.maxX; x++) {
            for (int y = MathHelper.floor(box.minY); y <= box.maxY; y++) {
                for (int z = MathHelper.floor(box.minZ); z <= box.maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (mc.world.getBlockState(pos).getBlock() instanceof CobwebBlock) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}