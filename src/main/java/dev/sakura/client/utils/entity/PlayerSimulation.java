package dev.sakura.client.utils.entity;

import net.minecraft.util.math.Vec3d;

public interface PlayerSimulation {
    Vec3d getPos();
    void tick();
}
