package dev.sakura.client.module.impl.render;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.Sakura;
import dev.sakura.client.events.entity.AttackEvent;
import dev.sakura.client.events.render.Render3DEvent;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.combat.KillAura;
import dev.sakura.client.utils.animations.Animation;
import dev.sakura.client.utils.animations.Direction;
import dev.sakura.client.utils.animations.impl.DecelerateAnimation;
import dev.sakura.client.utils.render.Render3DUtil;
import dev.sakura.client.utils.render.RenderUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.*;

public class TargetESP extends Module {
    private final EnumValue<Mode> mode = new EnumValue<>("Mark Mode", "标记模式", Mode.Points);
    private final EnumValue<ImageMode> imageMode = new EnumValue<>("Image Mode", "图片模式", ImageMode.Rectangle);
    private final NumberValue<Float> circleSpeed = new NumberValue<>("Circle Speed", "圆圈速度", 2.0F, 0.1F, 5.0F, 0.1F);
    private final BoolValue onlyPlayer = new BoolValue("Only Player", "仅玩家", true);
    private KillAura killAura;
    private LivingEntity target;
    private final TimerUtil timerUtil = new TimerUtil();
    private final Animation alphaAnim = new DecelerateAnimation(400, 1);
    private final long lastTime = System.currentTimeMillis();
    private final Identifier rectangle = Identifier.of("sakura", "textures/particles/target.png");
    private final Identifier quadstapple = Identifier.of("sakura", "textures/particles/quadstapple.png");
    private final Identifier trianglestapple = Identifier.of("sakura", "textures/particles/trianglestapple.png");
    private final Identifier trianglestipple = Identifier.of("sakura", "textures/particles/trianglestipple.png");
    public double prevCircleStep;
    public double circleStep;

    public TargetESP() {
        super("TargetESP", "目标ESP", Category.Render);
    }

    @Override
    public void onEnable() {
        target = null;
        killAura = Sakura.MODULES.getModule(KillAura.class);
        super.onEnable();
    }

    @EventHandler
    public void onAttack(AttackEvent event) {
        if (event.getTargetEntity() != null
                && (onlyPlayer.get() && event.getTargetEntity() instanceof PlayerEntity || !onlyPlayer.get())) {
            if (event.getTargetEntity() instanceof LivingEntity) {
                target = (LivingEntity) event.getTargetEntity();
                alphaAnim.setDirection(Direction.FORWARDS);
                timerUtil.reset();
            }
        }
    }

    @EventHandler
    public void onUpdate(Render3DEvent event) {
        if (killAura.isEnabled() && killAura.getCurrentTarget() instanceof LivingEntity living) {
            target = living;
            alphaAnim.setDirection(Direction.FORWARDS);
            timerUtil.reset();
        }

        if (timerUtil.passedMS(100)) {
            alphaAnim.setDirection(Direction.BACKWARDS);
            if (alphaAnim.isDone())
                target = null;
        }
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (target != null) {
            if (mode.get() == Mode.Points)
                points(event);

            if (mode.get() == Mode.Exhi) {
                Color color = this.target.hurtTime > 3 ? new Color(200, 255, 100, 75)
                        : this.target.hurtTime < 3 ? new Color(235, 40, 40, 75) : new Color(255, 255, 255, 75);
                MatrixStack matrices = event.getMatrices();
                matrices.push();

                double x = MathHelper.lerp(event.getTickDelta(), target.prevX, target.getX());
                double y = MathHelper.lerp(event.getTickDelta(), target.prevY, target.getY());
                double z = MathHelper.lerp(event.getTickDelta(), target.prevZ, target.getZ());

                Box axisAlignedBB = target.getBoundingBox().offset(-x, -y, -z);
                Render3DUtil.drawFullBox(matrices, axisAlignedBB, color, color, 2f);
                matrices.pop();
            }

            if (mode.get() == Mode.Ghost) {
                MatrixStack matrices = event.getMatrices();
                matrices.push();

                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.disableCull();
                RenderSystem.disableDepthTest();

                double radius = 0.67;
                float speed = 45;
                float size = 0.4f;
                double distance = 19;
                int length = 20;

                double x = MathHelper.lerp(event.getTickDelta(), target.prevX, target.getX());
                double y = MathHelper.lerp(event.getTickDelta(), target.prevY, target.getY());
                double z = MathHelper.lerp(event.getTickDelta(), target.prevZ, target.getZ());

                Vec3d interpolated = new Vec3d(
                        x + (target.getX() - target.prevX) * event.getTickDelta(),
                        y + (target.getY() - target.prevY) * event.getTickDelta() + 0.75f,
                        z + (target.getZ() - target.prevZ) * event.getTickDelta());

                matrices.translate(interpolated.x, interpolated.y, interpolated.z);

                for (int i = 0; i < length; i++) {
                    double angle = 0.15f * (System.currentTimeMillis() - lastTime - (i * distance)) / (speed);
                    double s = Math.sin(angle) * radius;
                    double c = Math.cos(angle) * radius;

                    matrices.push();
                    matrices.translate(s, c, -c);
                    matrices.scale(size, size, size);

                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
                    RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);

                    Matrix4f matrix = matrices.peek().getPositionMatrix();

                    double animOutput = alphaAnim.getOutput();

                    Color color = new Color(255, 255, 255, (int) (animOutput * 255 * 0.7));
                    float r = color.getRed() / 255.0F;
                    float g = color.getGreen() / 255.0F;
                    float b = color.getBlue() / 255.0F;
                    float a = color.getAlpha() / 255.0F;

                    buffer.vertex(matrix, -0.5f, -0.5f, 0).color(r, g, b, a);
                    buffer.vertex(matrix, 0.5f, -0.5f, 0).color(r, g, b, a);
                    buffer.vertex(matrix, 0.5f, 0.5f, 0).color(r, g, b, a);
                    buffer.vertex(matrix, -0.5f, 0.5f, 0).color(r, g, b, a);

                    BufferRenderer.drawWithGlobalProgram(buffer.end());

                    matrices.pop();
                }

                RenderSystem.enableCull();
                RenderSystem.enableDepthTest();
                RenderSystem.disableBlend();

                matrices.pop();
            }

            if (mode.get() == Mode.Image) {
                MatrixStack matrices = event.getMatrices();
                matrices.push();

                double x = MathHelper.lerp(event.getTickDelta(), target.prevX, target.getX());
                double y = MathHelper.lerp(event.getTickDelta(), target.prevY, target.getY());
                double z = MathHelper.lerp(event.getTickDelta(), target.prevZ, target.getZ());

                Vec3d camPos = mc.getEntityRenderDispatcher().camera.getPos();

                Identifier texture = switch (imageMode.get()) {
                    case Rectangle -> rectangle;
                    case QuadStapple -> quadstapple;
                    case TriangleStapple -> trianglestapple;
                    case TriangleStipple -> trianglestipple;
                };

                RenderSystem.setShaderTexture(0, texture);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.enableDepthTest();
                RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX);

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

                matrices.translate(x - camPos.x, y - camPos.y + target.getHeight() / 2f, z - camPos.z);
                matrices.multiply(mc.getEntityRenderDispatcher().camera.getRotation());
                matrices.scale(0.5f, 0.5f, 0.5f);

                Matrix4f matrix = matrices.peek().getPositionMatrix();

                float width = 1.0f;
                float height = 1.0f;

                buffer.vertex(matrix, -width / 2f, -height / 2f, 0.0f).texture(0.0f, 0.0f);
                buffer.vertex(matrix, -width / 2f, height / 2f, 0.0f).texture(0.0f, 1.0f);
                buffer.vertex(matrix, width / 2f, height / 2f, 0.0f).texture(1.0f, 1.0f);
                buffer.vertex(matrix, width / 2f, -height / 2f, 0.0f).texture(1.0f, 0.0f);

                BufferRenderer.drawWithGlobalProgram(buffer.end());

                RenderSystem.disableBlend();
                matrices.pop();
            }

            if (mode.get() == Mode.Circle) {
                prevCircleStep = circleStep;
                circleStep += (double) this.circleSpeed.get() * RenderUtil.deltaTime();
                float eyeHeight = target.getStandingEyeHeight();
                if (target.isSneaking()) {
                    eyeHeight -= 0.2F;
                }

                double cs = prevCircleStep + (circleStep - prevCircleStep) * (double) event.getTickDelta();
                double prevSinAnim = Math.abs(1.0D + Math.sin(cs - 0.5D)) / 2.0D;
                double sinAnim = Math.abs(1.0D + Math.sin(cs)) / 2.0D;
                double x = MathHelper.lerp(event.getTickDelta(), target.prevX, target.getX())
                        - mc.getEntityRenderDispatcher().camera.getPos().x;
                double y = MathHelper.lerp(event.getTickDelta(), target.prevY, target.getY())
                        - mc.getEntityRenderDispatcher().camera.getPos().y + prevSinAnim * (double) eyeHeight;
                double z = MathHelper.lerp(event.getTickDelta(), target.prevZ, target.getZ())
                        - mc.getEntityRenderDispatcher().camera.getPos().z;
                double nextY = MathHelper.lerp(event.getTickDelta(), target.prevY, target.getY())
                        - mc.getEntityRenderDispatcher().camera.getPos().y + sinAnim * (double) eyeHeight;

                MatrixStack matrices = event.getMatrices();
                matrices.push();
                RenderSystem.disableCull();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP,
                        VertexFormats.POSITION_COLOR);
                RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);

                int i;
                Color color;
                Color mainColor = Color.WHITE;

                for (i = 0; i <= 360; ++i) {
                    color = mainColor;
                    float r = color.getRed() / 255.0F;
                    float g = color.getGreen() / 255.0F;
                    float b = color.getBlue() / 255.0F;
                    Matrix4f matrix = matrices.peek().getPositionMatrix();
                    buffer.vertex(matrix, (float) (x + Math.cos(Math.toRadians(i)) * (double) target.getWidth() * 0.8D),
                                    (float) nextY,
                                    (float) (z + Math.sin(Math.toRadians(i)) * (double) target.getWidth() * 0.8D))
                            .color(r, g, b, 0.6F);
                    buffer.vertex(matrix, (float) (x + Math.cos(Math.toRadians(i)) * (double) target.getWidth() * 0.8D),
                                    (float) y, (float) (z + Math.sin(Math.toRadians(i)) * (double) target.getWidth() * 0.8D))
                            .color(r, g, b, 0.01F);
                }

                BufferRenderer.drawWithGlobalProgram(buffer.end());

                buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);

                for (i = 0; i <= 360; ++i) {
                    color = mainColor;
                    float r = color.getRed() / 255.0F;
                    float g = color.getGreen() / 255.0F;
                    float b = color.getBlue() / 255.0F;
                    Matrix4f matrix = matrices.peek().getPositionMatrix();
                    buffer.vertex(matrix, (float) (x + Math.cos(Math.toRadians(i)) * (double) target.getWidth() * 0.8D),
                                    (float) nextY,
                                    (float) (z + Math.sin(Math.toRadians(i)) * (double) target.getWidth() * 0.8D))
                            .color(r, g, b, 0.8F);
                }

                BufferRenderer.drawWithGlobalProgram(buffer.end());

                RenderSystem.enableCull();
                RenderSystem.disableBlend();
                matrices.pop();
            }
        }
    }

    private void points(Render3DEvent event) {
        if (target != null) {
            double markerX = MathHelper.lerp(event.getTickDelta(), target.prevX, target.getX());
            double markerY = MathHelper.lerp(event.getTickDelta(), target.prevY, target.getY())
                    + target.getHeight() / 1.6f;
            double markerZ = MathHelper.lerp(event.getTickDelta(), target.prevZ, target.getZ());

            float time = (float) ((((System.currentTimeMillis() - lastTime) / 1500F))
                    + (Math.sin((((System.currentTimeMillis() - lastTime) / 1500F))) / 10f));
            float alpha = 0.5f;
            float pl = 0;
            boolean fa = false;

            for (int iteration = 0; iteration < 3; iteration++) {
                for (float i = time * 360; i < time * 360 + 90; i += 2) {
                    float max = time * 360 + 90;
                    float dc = (float) (i - (time * 360 - 45)) / (max - (time * 360 - 45));
                    float rf = 0.6f;
                    double radians = Math.toRadians(i);
                    double plY = pl + Math.sin(radians * 1.2f) * 0.1f;

                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.disableCull();
                    RenderSystem.disableDepthTest();

                    MatrixStack matrices = event.getMatrices();
                    matrices.push();

                    Vec3d camPos = mc.getEntityRenderDispatcher().camera.getPos();

                    matrices.translate(markerX - camPos.x, markerY - camPos.y, markerZ - camPos.z);

                    matrices.multiply(mc.getEntityRenderDispatcher().camera.getRotation());

                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
                    RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);

                    Matrix4f matrix = matrices.peek().getPositionMatrix();

                    float q = (!fa ? 0.25f : 0.15f)
                            * (Math.max(fa ? 0.25f : 0.15f, fa ? dc : (1f + (0.4f - dc)) / 2f) + 0.45f);
                    float size = q * (2f + ((0.5f - alpha) * 2));

                    Color color = new Color(255, 255, 255, (int) (alphaAnim.getOutput() * 255));

                    float r = color.getRed() / 255.0F;
                    float g = color.getGreen() / 255.0F;
                    float b = color.getBlue() / 255.0F;
                    float a = color.getAlpha() / 255.0F;

                    float cosX = (float) (Math.cos(radians) * rf);
                    float sinZ = (float) (Math.sin(radians) * rf);

                    float halfSize = size / 2f;

                    buffer.vertex(matrix, cosX - halfSize, (float) plY - 0.7f - halfSize, sinZ).color(r, g, b, a);
                    buffer.vertex(matrix, cosX + halfSize, (float) plY - 0.7f - halfSize, sinZ).color(r, g, b, a);
                    buffer.vertex(matrix, cosX + halfSize, (float) plY - 0.7f + halfSize, sinZ).color(r, g, b, a);
                    buffer.vertex(matrix, cosX - halfSize, (float) plY - 0.7f + halfSize, sinZ).color(r, g, b, a);

                    BufferRenderer.drawWithGlobalProgram(buffer.end());

                    matrices.pop();

                    RenderSystem.enableDepthTest();
                    RenderSystem.enableCull();
                    RenderSystem.disableBlend();
                }
                time *= -1.025f;
                fa = !fa;
                pl += 0.45f;
            }
        }
    }

    private enum Mode {
        Points, Ghost, Image, Exhi, Circle
    }

    private enum ImageMode {
        Rectangle, QuadStapple, TriangleStapple, TriangleStipple
    }
}