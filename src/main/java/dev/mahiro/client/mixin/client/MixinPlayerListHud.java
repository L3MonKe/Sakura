package dev.mahiro.client.mixin.client;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.module.impl.hud.DynamicIslandHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static dev.mahiro.client.Mahiro.mc;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud {
    @Shadow
    private Text header;

    @Shadow
    private Text footer;

    /*@Inject(method = "getPlayerName", at = @At("HEAD"), cancellable = true)
    public void getPlayerName(PlayerListEntry playerListEntry, CallbackInfoReturnable<Text> info) {
        Chat chat = Mahiro.MODULES.getModule(Chat.class);

        if (chat.isEnabled() && chat.enableTab.get()) info.setReturnValue(chat.getPlayerName(playerListEntry));
    }*/

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V", at = @At("HEAD"), cancellable = true)
    private void onRender(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci) {
        if (!Mahiro.MODULES.getModule(DynamicIslandHud.class).isEnabled()) return;
        if (!mc.options.playerListKey.isPressed()) return;

        List<PlayerListEntry> entries = mc.getNetworkHandler() == null ? List.of() : List.copyOf(mc.getNetworkHandler().getPlayerList());
        DynamicIslandHud.hookVanillaTab(this.header, this.footer, entries);
        ci.cancel();
    }
}
