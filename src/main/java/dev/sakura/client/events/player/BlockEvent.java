package dev.sakura.client.events.player;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockEvent {
    private BlockPos blockPos;
    private Direction direction;

    public BlockEvent(BlockPos blockPos, Direction direction) {
        this.blockPos = blockPos;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
