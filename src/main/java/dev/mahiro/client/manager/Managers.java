package dev.mahiro.client.manager;

import dev.mahiro.client.manager.impl.*;

public class Managers {
    private static boolean initialized;

    public static AccountManager ACCOUNT;
    public static ChatAnimationUpdater CHAT_ANIMATION;
    public static ExtrapolationManager EXTRAPOLATION;
    public static HealthManager HEALTH;
    public static RenderManager RENDER;
    public static RotationManager ROTATION;
    public static SoundManager SOUND;
    public static CombatManager COMBAT;

    public static void init() {
        if (initialized) return;

        ACCOUNT = new AccountManager();
        CHAT_ANIMATION = new ChatAnimationUpdater();
        EXTRAPOLATION = new ExtrapolationManager();
        HEALTH = new HealthManager();
        RENDER = new RenderManager();
        ROTATION = new RotationManager();
        SOUND = new SoundManager();
        COMBAT = new CombatManager();

        initialized = true;
    }
}