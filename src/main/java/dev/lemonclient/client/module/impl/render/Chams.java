package dev.lemonclient.client.module.impl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.lemonclient.client.events.render.item.HeldItemRendererEvent;
import dev.lemonclient.client.module.Category;
import dev.lemonclient.client.module.Module;
import dev.lemonclient.client.values.impl.BoolValue;
import dev.lemonclient.client.values.impl.ColorValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;

import java.awt.*;

public class Chams extends Module {
    public Chams() {
        super("Chams", "产慕斯", Category.Render);
    }

    public final BoolValue handItems = new BoolValue("Hand Items", "手上东西", false);
    private final ColorValue handItemsColor = new ColorValue("Hand Items Color", "手上东西颜色", new Color(0x9317DE5D, true), handItems::get);

    public final BoolValue players = new BoolValue("Players", "玩家", false);
    public final ColorValue playerColor = new ColorValue("Player Color", "玩家颜色", new Color(0x932DD8E8, true), players::get);
    public final BoolValue playerTexture = new BoolValue("Player Texture", "玩家图片", true, players::get);
    public final BoolValue playerHeldItems = new BoolValue("Player Held Items", "玩家手持物品", true, players::get);

    public final BoolValue alternativeBlending = new BoolValue("Alternative Blending", "何意味", true);

    @EventHandler
    public void onRenderHands(HeldItemRendererEvent event) {
        if (handItems.get()) {
            RenderSystem.setShaderColor(handItemsColor.get().getRed() / 255f, handItemsColor.get().getGreen() / 255f, handItemsColor.get().getBlue() / 255f, handItemsColor.get().getAlpha() / 255f);
        }
    }

    public boolean shouldApplyHand(ArmedEntityRenderState state) {
        return isEnabled() && players.get() && playerHeldItems.get() && alternativeBlending.get() && state instanceof PlayerEntityRenderState;
    }
}
