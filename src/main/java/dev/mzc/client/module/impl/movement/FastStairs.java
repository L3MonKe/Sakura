package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.mixin.accessor.ILivingEntity;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.utils.player.MovementUtil;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;

public class FastStairs extends Module {
    public FastStairs() {
        super("FastStairs", "快速楼梯", Category.Movement);
        this.setType(ModuleType.Safe);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (MovementUtil.isMoving() && mc.player.isOnGround()) {
            BlockPos pos = mc.player.getBlockPos();
            if (mc.world.getBlockState(pos).getBlock() instanceof StairsBlock) {
                ((ILivingEntity) mc.player).setLastJumpCooldown(0);
                mc.player.jump();
            }
        }
    }
}
