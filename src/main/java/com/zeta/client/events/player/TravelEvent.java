package com.zeta.client.events.player;

import com.zeta.client.events.Cancellable;
import com.zeta.client.events.EventType;
import net.minecraft.util.math.Vec3d;

public class TravelEvent extends Cancellable {
    private final EventType type;
    private Vec3d movementInput;

    public TravelEvent(EventType type, Vec3d movementInput) {
        this.type = type;
        this.movementInput = movementInput;
    }

    public EventType getType() {
        return type;
    }

    public boolean isPre() {
        return type == EventType.PRE;
    }

    public boolean isPost() {
        return type == EventType.POST;
    }

    public Vec3d getMovementInput() {
        return movementInput;
    }

    public void setMovementInput(Vec3d movementInput) {
        this.movementInput = movementInput;
    }
}