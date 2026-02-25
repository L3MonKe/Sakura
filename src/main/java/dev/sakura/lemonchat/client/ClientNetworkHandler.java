package dev.sakura.lemonchat.client;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.client.Chat;
import dev.sakura.lemonchat.network.NetHandler;
import dev.sakura.lemonchat.network.c2s.ChatMessageC2S;
import dev.sakura.lemonchat.network.c2s.HandShakeC2S;
import dev.sakura.lemonchat.network.c2s.LoginC2S;
import dev.sakura.lemonchat.network.s2c.ChatMessageS2C;
import net.minecraft.text.Text;

import java.io.IOException;

import static dev.sakura.client.Sakura.mc;

public class ClientNetworkHandler implements NetHandler {
    public final ClientSession session;

    public ClientNetworkHandler(ClientSession session) {
        this.session = session;
    }

    @Override
    public void onHandShakeC2S(HandShakeC2S packet) throws IOException {

    }

    @Override
    public void onLoginC2S(LoginC2S packet) throws IOException {

    }

    @Override
    public void onMessageC2S(ChatMessageC2S packet) throws IOException {

    }

    @Override
    public void onMessageS2C(ChatMessageS2C packet) throws IOException {
        if (Sakura.MODULES.getModule(Chat.class).enable.get()) {
            sendMessage(packet.message);
        }
    }

    public void sendMessage(String text, Object... args) {
        for (Object arg : args) {
            text = text.replaceFirst("\\{}", arg.toString());
        }

        if (mc != null && mc.world != null && mc.player != null) {
            mc.inGameHud.getChatHud().addMessage(Text.literal(text));
        } else ChatClient.LOGGER.info(text);
    }
}
