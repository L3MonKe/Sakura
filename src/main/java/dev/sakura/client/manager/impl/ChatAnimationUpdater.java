package dev.sakura.client.manager.impl;

import dev.sakura.client.Sakura;
import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.utils.animations.ChatAnimationManager;
import meteordevelopment.orbit.EventHandler;

public class ChatAnimationUpdater {
    public ChatAnimationUpdater() {
        Sakura.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        ChatAnimationManager.getInstance().update();
    }
}