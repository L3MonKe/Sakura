package dev.sakura.client.module.impl.client;

import dev.sakura.client.gui.music.CloudMusicScreen;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.music.CloudMusicPlayer;
import dev.sakura.client.music.CloudMusicService;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;

import java.awt.*;

public class CloudMusicGui extends Module {
    public static final ColorValue backgroundColor = new ColorValue("Background Color", "背景颜色", new Color(25, 25, 35, 180));
    public static final NumberValue<Double> blurStrength = new NumberValue<>("Blur Strength", "模糊强度", 15.0, 0.0, 30.0, 1.0);
    public static final NumberValue<Double> shadowRange = new NumberValue<>("Shadow Range", "阴影范围", 12.0, 0.0, 30.0, 1.0);
    public static final NumberValue<Double> gradientSpeed = new NumberValue<>("Gradient Speed", "渐变速度", 1.0, 0.1, 5.0, 0.05);
    public static final BoolValue titleTextShadow = new BoolValue("Title Shadow", "标题阴影", true);
    public static final NumberValue<Double> titleTextShadowDist = new NumberValue<>("Title Shadow Dist", "阴影间距", 1.5, 0.0, 6.0, 0.1, titleTextShadow::get);

    private static final CloudMusicService SERVICE = new CloudMusicService();
    private static final CloudMusicPlayer PLAYER = new CloudMusicPlayer(SERVICE::resolveSongUrlSync);

    public CloudMusicGui() {
        super("CloudMusic", "网易云音乐", Category.Client);
    }

    public static CloudMusicPlayer getPlayer() {
        return PLAYER;
    }

    public static CloudMusicService getService() {
        return SERVICE;
    }

    @Override
    protected void onEnable() {
        if (mc.currentScreen == null && mc.mouse == null) {
            setState(false);
            return;
        }
        mc.setScreen(new CloudMusicScreen(this, SERVICE, PLAYER));
    }

    @Override
    protected void onDisable() {
        if (mc.currentScreen instanceof CloudMusicScreen) {
            mc.setScreen(null);
        }
    }
}
