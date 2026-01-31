package dev.mahiro.client.module.impl.client;

import dev.mahiro.client.config.ConfigManager;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.events.type.EventType;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.client.ChatUtil;
import dev.mahiro.client.values.impl.BoolValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AutoHeypixel extends Module {
    public AutoHeypixel() {
        super("AutoHeypixel", "自动岛吉吉", Category.Client);
    }

    private final BoolValue autoScreenshot = new BoolValue("Auto Screenshot", "自动截图", true);

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (nullCheck()) return;
        if (event.getType() != EventType.RECEIVE) return;

        Packet<?> packet = event.getPacket();
        if (packet instanceof TitleS2CPacket(Text text)) {
            if (autoScreenshot.get() && text.getString().contains("胜利")) {
                CompletableFuture.runAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).thenRun(() -> {
                    mc.execute(() -> {
                        ScreenshotRecorder.saveScreenshot(ConfigManager.CONFIG_DIR.toFile(), mc.getFramebuffer(), (message) -> ChatUtil.addChatMessage(message.getString()));
                    });
                });
            }
        }
    }
}
