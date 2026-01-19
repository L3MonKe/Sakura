package dev.lemonclient.client.manager.impl;

import dev.lemonclient.client.LemonClient;
import dev.lemonclient.client.events.client.TickEvent;
import dev.lemonclient.client.utils.animations.ChatAnimationManager;
import meteordevelopment.orbit.EventHandler;

public class ChatAnimationUpdater {
    public ChatAnimationUpdater() {
        LemonClient.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        ChatAnimationManager.getInstance().update();
    }
}