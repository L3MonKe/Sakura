package dev.mahiro.client.module.impl.client;

import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.EnumValue;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.util.AssetInfo;
import net.minecraft.util.Identifier;

public class Capes extends Module {
    public Capes() {
        super("Capes", "披风", Category.Client);
    }

    public enum CapeMode {
        Default,
        Light,
        Nichijou
    }

    public final EnumValue<CapeMode> capeMode = new EnumValue<>("Cape Mode", "披风选择", CapeMode.Default);

    public String getName() {
        return
                switch (capeMode.get()) {
                    case Default -> "cape_default";
                    case Light -> "cape_light";
                    case Nichijou -> "cape_nichijou";
                };
    }

    private AssetInfo.TextureAsset getTexture(String capeName) {
        return new AssetInfo.TextureAssetInfo(Identifier.of("mahiro", "capes/" + capeName), Identifier.of("mahiro", "textures/capes/" + capeName + ".png"));
    }

    public AssetInfo.TextureAsset getCape(AbstractClientPlayerEntity player, boolean elytra) {
        if (isEnabled() && player.equals(mc.player)) {
            return getTexture(getName());
        }

        // IRC的披风

        PlayerListEntry entry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        if (entry == null) return null;
        SkinTextures skin = entry.getSkinTextures();
        return elytra ? skin.elytra() : skin.cape();
    }
}
