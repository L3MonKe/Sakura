package dev.sakura.client.mixin.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.utils.render.ScreenWhiteTransition;
import dev.sakura.client.utils.render.NewMenuMusicController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {
    protected MixinOptionsScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addCustomButton(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(
            Text.of(Sakura.CONFIG.getClientConfig().useNewMainMenu ? "MainMenu: Sakuranotoki" : "MainMenu: Sakura"),
            (button) -> {
                boolean wasNew = Sakura.CONFIG.getClientConfig().useNewMainMenu;
                Sakura.CONFIG.getClientConfig().customMainMenu = true;
                Sakura.CONFIG.getClientConfig().useNewMainMenu = !Sakura.CONFIG.getClientConfig().useNewMainMenu;
                Sakura.CONFIG.saveDefaultConfig();
                button.setMessage(Text.of(Sakura.CONFIG.getClientConfig().useNewMainMenu ? "MainMenu: Sakuranotoki" : "MainMenu: Sakura"));
                if (!wasNew && Sakura.CONFIG.getClientConfig().useNewMainMenu && this.client != null && this.client.world == null) {
                    NewMenuMusicController.requestDelayNextStart();
                    ScreenWhiteTransition.clearNewMenuFadeRequest();
                    ScreenWhiteTransition.startToAction(Sakura::redirectToMainMenu, 800L);
                    return;
                }
                applyMenuSwitch();
            }
        ).dimensions(10, 10, 160, 20).build());
    }

    private void applyMenuSwitch() {
        if (this.client == null || this.client.world != null) {
            return;
        }
        Sakura.redirectToMainMenu();
    }
}
