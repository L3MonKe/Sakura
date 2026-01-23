package dev.mahiro.client.mixin.entity;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.movement.Velocity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(FlowableFluid.class)
public class MixinFlowableFluid {
    @Redirect(method = "getVelocity", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", ordinal = 0))
    private boolean redirectVelocity(Iterator<Direction> var9) {
        Velocity velocity = Mahiro.MODULES.getModule(Velocity.class);
        if (velocity.isEnabled() && velocity.waterPush.get()) {
            return false;
        }
        return var9.hasNext();
    }
}
