package dev.mzc.client.module.impl.client;

import dev.mzc.client.events.EventType;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.StringValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.BrandCustomPayload;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;

public class ClientSpoofer extends Module {

    public static ClientSpoofer INSTANCE;

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", "模式", Mode.Vanilla);
    public final StringValue customName = new StringValue("Name", "名称", "Sakura");

    public ClientSpoofer() {
        super("ClientSpoofer", "客户端伪装", Category.Client);
        this.setType(ModuleType.All);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventHandler
    private void onPacketSend(PacketEvent event) {
        if (event.getType() == EventType.SEND && event.getPacket() instanceof CustomPayloadC2SPacket packet) {
            if (packet.payload() instanceof BrandCustomPayload brandPayload) {
                String brand = getBrand();
                if (!brandPayload.brand().equals(brand)) {
                    event.setPacket(new CustomPayloadC2SPacket(new BrandCustomPayload(brand)));
                }
            }
        }
    }

    public String getBrand() {
        switch (mode.get()) {
            case Vanilla -> {
                return "vanilla";
            }
            case Lunar -> {
                return "lunarclient:71aa15d";
            }
            case Forge -> {
                return "forge";
            }
            case LabyMod -> {
                return "LabyMod";
            }
            case Custom -> {
                return customName.get();
            }
        }
        return "vanilla";
    }

    public enum Mode {
        Vanilla,
        Lunar,
        Forge,
        LabyMod,
        Custom
    }
}
