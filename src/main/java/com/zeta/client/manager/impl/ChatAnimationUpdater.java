package com.zeta.client.manager.impl;

import com.zeta.client.Zeta;
import com.zeta.client.events.client.TickEvent;
import com.zeta.client.utils.animations.ChatAnimationManager;
import meteordevelopment.orbit.EventHandler;

public class ChatAnimationUpdater {
    public ChatAnimationUpdater() {
        Zeta.EVENT_BUS.subscribe(this);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        ChatAnimationManager.getInstance().update();
    }
}