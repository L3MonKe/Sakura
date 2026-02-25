package dev.mzc.client.mixin.entity;

import dev.mzc.client.Sakura;
import dev.mzc.client.module.impl.movement.Velocity;
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
        Velocity velocity = Sakura.MODULES.getModule(Velocity.class);
        if (velocity.isEnabled() && velocity.waterPush.get()) {
            return false;
        }
        return var9.hasNext();
    }
}
