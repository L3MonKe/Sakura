package dev.sakura.client.module.impl.player.mine;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockData {
    private BlockPos currentPos;
    private Direction direction;
    private Long startTime;

    public BlockData(BlockPos currentPos, Direction direction, Long startTime) {
        this.currentPos = currentPos;
        this.direction = direction;
        this.startTime = startTime;
    }

    public BlockPos getCurrentPos() {
        return currentPos;
    }

    public Direction getDirection() {
        return direction;
    }

    public Long getStartTime() {
        return startTime;
    }
}
