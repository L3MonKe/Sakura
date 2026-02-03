package dev.mahiro.client.manager;

import dev.mahiro.client.manager.impl.*;

public class Managers {
    private static boolean initialized;

    public static ChatAnimationUpdater CHAT_ANIMATION;
    public static CombatManager COMBAT;
    public static ExtrapolationManager EXTRAPOLATION;
    public static HealthManager HEALTH;
    public static RenderManager RENDER;
    public static RotationManager ROTATION;
    public static ShaderManager SHADER;
    public static SoundManager SOUND;

    public static void init() {
        if (initialized) return;

        CHAT_ANIMATION = new ChatAnimationUpdater();
        COMBAT = new CombatManager();
        EXTRAPOLATION = new ExtrapolationManager();
        HEALTH = new HealthManager();
        RENDER = new RenderManager();
        ROTATION = new RotationManager();
        SHADER = new ShaderManager();
        SOUND = new SoundManager();

        initialized = true;
    }
}