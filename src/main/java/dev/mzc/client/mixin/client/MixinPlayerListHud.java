package dev.mzc.client.mixin.client;

import dev.mzc.client.Sakura;
import dev.mzc.client.module.impl.client.Chat;
import dev.mzc.client.module.impl.hud.DynamicIslandHud;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static dev.mzc.client.Sakura.mc;

@Mixin(PlayerListHud.class)
public class MixinPlayerListHud {
    @Shadow
    private Text header;

    @Shadow
    private Text footer;

    @Inject(method = "getPlayerName", at = @At("HEAD"), cancellable = true)
    public void getPlayerName(PlayerListEntry playerListEntry, CallbackInfoReturnable<Text> info) {
        Chat chat = Sakura.MODULES.getModule(Chat.class);

        if (chat.isEnabled() && chat.enableTab.get()) info.setReturnValue(chat.getPlayerName(playerListEntry));
    }

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V", at = @At("HEAD"), cancellable = true)
    private void onRender(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci) {
        DynamicIslandHud dynamicIslandHud = Sakura.MODULES.getModule(DynamicIslandHud.class);
        if (dynamicIslandHud != null && dynamicIslandHud.isEnabled()) {
            List<PlayerListEntry> entries = mc.getNetworkHandler() == null ? List.of() : List.copyOf(mc.getNetworkHandler().getPlayerList());
            DynamicIslandHud.hookVanillaTab(this.header, this.footer, entries);
            ci.cancel();
        }
    }
}
