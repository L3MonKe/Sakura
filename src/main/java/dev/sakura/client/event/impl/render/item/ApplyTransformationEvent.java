package dev.sakura.client.event.impl.render.item;

import dev.sakura.client.event.Cancellable;
import net.minecraft.client.render.model.json.Transformation;

public class ApplyTransformationEvent extends Cancellable {
    public Transformation transformation;
    public boolean leftHanded;

    public ApplyTransformationEvent(Transformation transformation, boolean leftHanded) {
        this.transformation = transformation;
        this.leftHanded = leftHanded;
    }
}
