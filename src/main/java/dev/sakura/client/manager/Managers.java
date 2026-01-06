package dev.sakura.client.manager;

import dev.sakura.client.manager.impl.*;

public class Managers {
    private static boolean initialized;

    public static AccountManager ACCOUNT;
    public static ChatAnimationUpdater CHAT_ANIMATION;
    public static ExtrapolationManager EXTRAPOLATION;
    public static RenderManager RENDER;
    public static RotationManager ROTATION;
    public static ShaderManager SHADER;
    public static SoundManager SOUND;

    public static void init() {
        if (initialized) return;

        ACCOUNT = new AccountManager();
        CHAT_ANIMATION = new ChatAnimationUpdater();
        EXTRAPOLATION = new ExtrapolationManager();
        RENDER = new RenderManager();
        ROTATION = new RotationManager();
        SHADER = new ShaderManager();
        SOUND = new SoundManager();

        initialized = true;
    }
}