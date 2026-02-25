package dev.mzc.client.module.impl.client;

import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.lemonchat.client.ClientSession;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class Capes extends Module {
    public Capes() {
        super("Capes", "披风", Category.Client);
        this.setType(ModuleType.All);
    }

    public enum CapeMode {
        Default("默认"),
        Light("光"),
        Nichijou("日常");

        private final String cnName;

        CapeMode(String cnName) {
            this.cnName = cnName;
        }
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
