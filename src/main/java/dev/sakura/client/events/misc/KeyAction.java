package dev.sakura.client.events.misc;

import org.lwjgl.glfw.GLFW;

public enum KeyAction {
    Press,
    Repeat,
    Release;

    public static KeyAction from(int action) {
        return switch (action) {
            case GLFW.GLFW_PRESS -> Press;
            case GLFW.GLFW_RELEASE -> Release;
            default -> Repeat;
        };
    }
}