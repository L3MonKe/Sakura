package dev.sakura.client.module.impl.client;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.lemonchat.client.ClientSession;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Objects;

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

    private Identifier getTexture(String capeName) {
        return Identifier.of("sakura", "textures/capes/" + capeName + ".png");
    }

    public Identifier getCape(AbstractClientPlayerEntity player, boolean elytra) {
        try {
            if (isEnabled() && player.equals(mc.player)) {
                return getTexture(getName());
            }

            if (ClientSession.get() != null && ClientSession.get().hasCape(player)) {
                return getTexture(ClientSession.get().getCapeName(player));
            }

            return elytra ? Objects.requireNonNull(mc.getNetworkHandler().getPlayerListEntry(player.getUuid())).getSkinTextures().elytraTexture() : Objects.requireNonNull(mc.getNetworkHandler().getPlayerListEntry(player.getUuid())).getSkinTextures().capeTexture();
        } catch (Exception e) {
            return null;
        }
    }
}
