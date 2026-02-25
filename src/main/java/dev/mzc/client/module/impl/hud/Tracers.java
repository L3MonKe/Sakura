package dev.mzc.client.module.impl.hud;

import dev.mzc.client.module.HudModule;
import dev.mzc.client.module.impl.client.ClickGui;
import dev.mzc.client.nanovg.NanoVGRenderer;
import dev.mzc.client.nanovg.util.NanoVGHelper;
import dev.mzc.client.utils.color.ColorUtil;
import dev.mzc.client.utils.render.Render3DUtil;
import dev.mzc.client.utils.render.RenderUtil;
import dev.mzc.client.values.impl.BoolValue;
import dev.mzc.client.values.impl.ColorValue;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.nanovg.NVGColor;

import java.awt.*;

import static org.lwjgl.nanovg.NanoVG.*;

public class Tracers extends HudModule {

    public enum ColorMode {
        Static("静态"),
        Rainbow("彩虹"),
        Health("血量"),
        Distance("距离"),
        Sync("同步");

        private final String cnName;

        ColorMode(String cnName) {
            this.cnName = cnName;
        }
    }

    private final EnumValue<ColorMode> colorMode = new EnumValue<>("ColorMode", "颜色模式", ColorMode.Sync);
    private final BoolValue outline = new BoolValue("Outline", "描边", false);
    private final ColorValue color = new ColorValue("Color", "颜色", new Color(255, 255, 255, 255), () -> colorMode.is(ColorMode.Static));
    private final NumberValue<Float> width = new NumberValue<>("Width", "宽度", 1.5f, 0.1f, 5.0f, 0.1f);
    private final NumberValue<Double> range = new NumberValue<>("Range", "距离", 100.0, 10.0, 500.0, 10.0);


    public Tracers() {
        super("Tracers", "玩家追踪", 100, 100);
    }

    @Override
    public void onRender(DrawContext context) {
        // Calculate center of the HUD module
        float centerX = getX() + getWidth() / 2;
        float centerY = getY() + getHeight() / 2;

        NanoVGRenderer.INSTANCE.draw(vg -> {
            nvgStrokeWidth(vg, width.get());

            for (PlayerEntity player : mc.world.getPlayers()) {
                if (player == mc.player) continue;

                double distance = mc.player.distanceTo(player);
                if (distance > range.get()) continue;

                // Determine color based on mode
                Color lineColor;
                switch (colorMode.get()) {
                    case Static -> lineColor = color.get();
                    case Rainbow -> lineColor = new Color(RenderUtil.getRainbow(System.currentTimeMillis(), 3000, 0), true);
                    case Health -> {
                        float health = player.getHealth() + player.getAbsorptionAmount();
                        float maxHealth = player.getMaxHealth() + player.getAbsorptionAmount();
                        lineColor = getHealthColor(health, maxHealth);
                    }
                    case Distance -> {
                        float maxRange = range.get().floatValue();
                        float distRatio = Math.min(1.0f, (float) distance / maxRange);
                        // Close = Red, Far = Green (Standard tracers usually invert this, let's do Close=Red/Danger)
                        // Actually standard Minecraft tracers: Close = Red, Far = Green is common for "danger"
                        // Or maybe Close = Green (safe friend) and Far = Red?
                        // Let's stick to Green (far/safe) -> Red (close/danger)
                        lineColor = ColorUtil.interpolateColorC(Color.RED, Color.GREEN, distRatio);
                    }
                    case Sync -> lineColor = ClickGui.color(1);
                    default -> lineColor = Color.WHITE;
                }

                NVGColor nvgColor = NanoVGHelper.nvgColor(lineColor);
                nvgStrokeColor(vg, nvgColor);

                // Simple interpolation for smooth rendering
                float tickDelta = mc.getRenderTickCounter().getTickDelta(true);
                double x = player.prevX + (player.getX() - player.prevX) * tickDelta;
                double y = player.prevY + (player.getY() - player.prevY) * tickDelta;
                double z = player.prevZ + (player.getZ() - player.prevZ) * tickDelta;

                // Target position (head)
                // Use height + 0.1 for top of outline box
                Vec3d targetPos = new Vec3d(x, y + player.getHeight() + 0.1, z);
                
                // Project to screen
                Vec3d screenPos = Render3DUtil.worldToScreen(targetPos);

                // If player is visible (in front of camera)
                if (screenPos != null) {
                    nvgBeginPath(vg);
                    nvgMoveTo(vg, centerX, centerY);
                    nvgLineTo(vg, (float) screenPos.x, (float) screenPos.y);
                    nvgStroke(vg);
                }

                if (outline.get()) {
                    Vec3d topPos = new Vec3d(x, y + player.getHeight() + 0.1, z);
                    Vec3d botPos = new Vec3d(x, y, z);

                    Vec3d topScreen = Render3DUtil.worldToScreen(topPos);
                    Vec3d botScreen = Render3DUtil.worldToScreen(botPos);

                    if (topScreen != null && botScreen != null) {
                        float h = (float) (botScreen.y - topScreen.y);
                        float w = h / 2.0f;
                        float boxX = (float) topScreen.x - w / 2.0f;
                        float boxY = (float) topScreen.y;

                        nvgBeginPath(vg);
                        nvgRect(vg, boxX, boxY, w, h);
                        nvgStroke(vg);
                    }
                }
            }
        });
    }

    private Color getHealthColor(float health, float maxHealth) {
        float percentage = Math.max(0, Math.min(1, health / maxHealth));
        return ColorUtil.interpolateColorC(Color.RED, Color.GREEN, percentage);
    }
}
