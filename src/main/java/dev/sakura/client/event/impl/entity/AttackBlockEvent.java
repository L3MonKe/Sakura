package dev.sakura.client.event.impl.entity;

import dev.sakura.client.event.Cancellable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class AttackBlockEvent extends Cancellable {
    private final BlockPos pos;
    private final Direction direction;

    public AttackBlockEvent(BlockPos pos, Direction direction) {
        this.pos = pos;
        this.direction = direction;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Direction getDirection() {
        return direction;
    }
}
