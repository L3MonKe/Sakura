package dev.lemonclient.lemonchat.network;

import dev.lemonclient.lemonchat.network.c2s.ChatMessageC2S;
import dev.lemonclient.lemonchat.network.c2s.HandShakeC2S;
import dev.lemonclient.lemonchat.network.c2s.LoginC2S;
import dev.lemonclient.lemonchat.network.s2c.ChatMessageS2C;

import java.io.IOException;

public interface NetHandler {
    void onHandShakeC2S(HandShakeC2S packet) throws IOException;

    void onLoginC2S(LoginC2S packet) throws IOException;

    void onMessageC2S(ChatMessageC2S packet) throws IOException;

    void onMessageS2C(ChatMessageS2C packet) throws IOException;
}
