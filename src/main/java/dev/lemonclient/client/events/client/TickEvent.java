package dev.lemonclient.client.events.client;

import dev.lemonclient.client.auth.AuthGate;

import static dev.lemonclient.client.LemonClient.mc;

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
