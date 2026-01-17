package com.zeta.client.events.client;

import com.zeta.client.auth.AuthGate;

import static com.zeta.client.Zeta.mc;

public class TickEvent {
    public static class Pre extends TickEvent {
        public Pre() {
        }
    }

    public static class Post extends TickEvent {
        public Post() {
            AuthGate.doTickCheck(mc);
        }
    }
}
