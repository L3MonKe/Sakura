package dev.sakura.client.mixin.accessor;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface IEntityFlag {
    @Invoker("setFlag")
    void invokeSetFlag(int index, boolean value);
}
