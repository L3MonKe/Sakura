package dev.mzc.client.mixin.input;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.mzc.client.Sakura;
import dev.mzc.client.events.input.MoveInputEvent;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyboardInput.class)
public class MixinKeyboardInput {
    @ModifyExpressionValue(method = "tick", at = @At(value = "NEW", target = "(ZZZZZZZ)Lnet/minecraft/util/PlayerInput;"))
    private PlayerInput modifyInput(PlayerInput original) {
        MoveInputEvent event = new MoveInputEvent(
                original.forward(),
                original.backward(),
                original.left(),
                original.right(),
                original.jump(),
                original.sneak(),
                original.sprint()
        );
        Sakura.EVENT_BUS.post(event);
        return event.toPlayerInput();
    }
}
