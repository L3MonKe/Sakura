package dev.mahiro.client.mixin.entity;

import net.minecraft.fluid.FlowableFluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FlowableFluid.class)
public class MixinFlowableFluid {
    /*@Redirect(method = "getVelocity", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", ordinal = 0))
    private boolean redirectVelocity(Iterator<Direction> var9) {
        Velocity velocity = Mahiro.MODULES.getModule(Velocity.class);
        if (velocity.isEnabled() && velocity.waterPush.get()) {
            return false;
        }
        return var9.hasNext();
    }*/
}
