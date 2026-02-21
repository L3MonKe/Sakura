package dev.sakura.client.mixin.accessor;

import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(KeyBinding.class)
public interface IKeyBinding {
    @Accessor("KEYS_BY_ID")
    static Map<String, KeyBinding> getKeysById() {
        return null;
    }

    @Invoker("reset")
    void invokeReset();
}
