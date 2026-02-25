package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import net.minecraft.client.util.Icons;
import net.minecraft.client.util.Window;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(Window.class)
public class MixinWindow {
    @Redirect(method = "setIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Icons;getIcons(Lnet/minecraft/resource/ResourcePack;)Ljava/util/List;"))
    private List<InputSupplier<InputStream>> onSetIcon(Icons instance, ResourcePack resourcePack) throws IOException {
        final InputStream stream16 = Sakura.class.getResourceAsStream("/assets/sakura/icons/icon_16x16.png");
        final InputStream stream32 = Sakura.class.getResourceAsStream("/assets/sakura/icons/icon_32x32.png");

        if (stream16 == null || stream32 == null) {
            Sakura.LOGGER.error("找不到icon图标!");
            return instance.getIcons(resourcePack);
        }

        return List.of(() -> stream16, () -> stream32);
    }
}
