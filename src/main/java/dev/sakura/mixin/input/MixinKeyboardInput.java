package dev.sakura.mixin.input;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sakura.Sakura;
import dev.sakura.events.input.MoveInputEvent;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @Author：Gu-Yuemang
 * @Date：12/7/2025 1:55 PM
 */
@Mixin(KeyboardInput.class)
public class MixinKeyboardInput {

    @ModifyExpressionValue(method = "tick", at = @At(value = "NEW", target = "(ZZZZZZZ)Lnet/minecraft/util/PlayerInput;"))
    private PlayerInput modifyInput(PlayerInput original) {

        var event = new MoveInputEvent(
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
