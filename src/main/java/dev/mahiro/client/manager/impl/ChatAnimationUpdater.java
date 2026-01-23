package dev.mahiro.client.manager.impl;

import dev.mahiro.client.Mahiro;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.utils.animations.ChatAnimationManager;
import meteordevelopment.orbit.EventHandler;

public class ChatAnimationUpdater {
    public ChatAnimationUpdater() {
        Mahiro.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        ChatAnimationManager.getInstance().update();
    }
}