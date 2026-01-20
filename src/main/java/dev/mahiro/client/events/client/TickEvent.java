package dev.mahiro.client.events.client;

import dev.mahiro.client.auth.AuthGate;

import static dev.mahiro.client.LemonClient.mc;

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
