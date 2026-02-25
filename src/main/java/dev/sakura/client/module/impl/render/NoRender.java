package dev.sakura.client.module.impl.render;

import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;

public class NoRender extends Module {
    // Overlay
    public final BoolValue noHurtCam = new BoolValue("No Hurt Cam", "无伤害抖动", false);
    public final BoolValue noPortalOverlay = new BoolValue("No Portal Overlay", "无传送门效果", false);
    public final BoolValue noSpyglassOverlay = new BoolValue("No Spyglass Overlay", "无望远镜效果", false);
    public final BoolValue noNausea = new BoolValue("No Nausea", "无反胃效果", false);
    public final BoolValue noPumpkinOverlay = new BoolValue("No Pumpkin Overlay", "无南瓜头效果", false);
    public final BoolValue noPowderedSnowOverlay = new BoolValue("No Powdered Snow Overlay", "无细雪效果", false);
    public final BoolValue noFireOverlay = new BoolValue("No Fire Overlay", "无火焰效果", false);
    public final BoolValue noLiquidOverlay = new BoolValue("No Liquid Overlay", "无液体效果", false);
    public final BoolValue noInWallOverlay = new BoolValue("No In Wall Overlay", "无窒息效果", false);
    public final BoolValue noVignette = new BoolValue("No Vignette", "无晕影效果", false);
    public final BoolValue noTotemAnimation = new BoolValue("No Totem Animation", "无图腾动画", false);

    // HUD
    public final BoolValue noBossBar = new BoolValue("No Boss Bar", "无Boss条", false);
    public final BoolValue noScoreboard = new BoolValue("No Scoreboard", "无计分板", false);
    public final BoolValue noCrosshair = new BoolValue("No Crosshair", "无准星", false);
    public final BoolValue noTitle = new BoolValue("No Title", "无标题", false);
    public final BoolValue noHeldItemName = new BoolValue("No Held Item Name", "无手持物品名", false);
    public final BoolValue noPotionIcons = new BoolValue("No Potion Icons", "无药水图标", false);

    // World
    public final BoolValue noWeather = new BoolValue("No Weather", "无天气", false);
    public final BoolValue noFog = new BoolValue("No Fog", "无迷雾", false);
    public final BoolValue noBlindness = new BoolValue("No Blindness", "无失明", false);
    public final BoolValue noDarkness = new BoolValue("No Darkness", "无黑暗", false);
    public final BoolValue noExplosionParticles = new BoolValue("No Explosion Particles", "无爆炸粒子", false);

    public NoRender() {
        super("NoRender", "不渲染", Category.Render);
    }

    public boolean getNoHurtCam() {
        return isEnabled() && noHurtCam.get();
    }

    public boolean noPortalOverlay() {
        return isEnabled() && noPortalOverlay.get();
    }

    public boolean noSpyglassOverlay() {
        return isEnabled() && noSpyglassOverlay.get();
    }

    public boolean noNausea() {
        return isEnabled() && noNausea.get();
    }

    public boolean noPumpkinOverlay() {
        return isEnabled() && noPumpkinOverlay.get();
    }

    public boolean noPowderedSnowOverlay() {
        return isEnabled() && noPowderedSnowOverlay.get();
    }

    public boolean noFireOverlay() {
        return isEnabled() && noFireOverlay.get();
    }

    public boolean noLiquidOverlay() {
        return isEnabled() && noLiquidOverlay.get();
    }

    public boolean noInWallOverlay() {
        return isEnabled() && noInWallOverlay.get();
    }

    public boolean noVignette() {
        return isEnabled() && noVignette.get();
    }

    public boolean noTotemAnimation() {
        return isEnabled() && noTotemAnimation.get();
    }

    public boolean noBossBar() {
        return isEnabled() && noBossBar.get();
    }

    public boolean noScoreboard() {
        return isEnabled() && noScoreboard.get();
    }

    public boolean noCrosshair() {
        return isEnabled() && noCrosshair.get();
    }

    public boolean noTitle() {
        return isEnabled() && noTitle.get();
    }

    public boolean noHeldItemName() {
        return isEnabled() && noHeldItemName.get();
    }

    public boolean noPotionIcons() {
        return isEnabled() && noPotionIcons.get();
    }

    public boolean noWeather() {
        return isEnabled() && noWeather.get();
    }

    public boolean noFog() {
        return isEnabled() && noFog.get();
    }

    public boolean noBlindness() {
        return isEnabled() && noBlindness.get();
    }

    public boolean noDarkness() {
        return isEnabled() && noDarkness.get();
    }

    public boolean noExplosionParticles() {
        return isEnabled() && noExplosionParticles.get();
    }
}