package dev.mahiro.client.module.impl.render;

import dev.mahiro.client.events.render.Render3DEvent;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.module.impl.client.ClickGui;
import dev.mahiro.client.utils.render.Render3DUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.ColorValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trajectories extends Module {

    public enum ColorMode {
        Client,
        Custom,
        Rainbow
    }

    private final EnumValue<ColorMode> colorMode = new EnumValue<>("Color Mode", "颜色模式", ColorMode.Client);
    private final ColorValue pathColor = new ColorValue("Path Color", "轨迹颜色", new Color(255, 183, 197), () -> colorMode.is(ColorMode.Custom));
    private final ColorValue landingColor = new ColorValue("Landing Color", "落点颜色", new Color(255, 105, 180), () -> colorMode.is(ColorMode.Custom));
    private final BoolValue landingBox = new BoolValue("Landing Box", "落点方块", true);
    private final BoolValue entityBox = new BoolValue("Entity Box", "实体方框", true);
    private final BoolValue firedProjectiles = new BoolValue("Fired Projectiles", "已发射投掷物", true);
    private final NumberValue<Double> lineWidth = new NumberValue<>("Line Width", "线宽", 2.0, 0.5, 5.0, 0.1);
    private final NumberValue<Integer> maxSteps = new NumberValue<>("Max Steps", "最大步数", 300, 50, 1000, 10);
    private final NumberValue<Integer> trailLength = new NumberValue<>("Trail Length", "尾迹长度", 25, 5, 80, 1);
    private final BoolValue showBow = new BoolValue("Bow", "弓", true);
    private final BoolValue showCrossbow = new BoolValue("Crossbow", "弩", true);
    private final BoolValue showThrowables = new BoolValue("Throwables", "投掷物", true);
    private final BoolValue showTrident = new BoolValue("Trident", "三叉戟", true);
    private final BoolValue showFishingRod = new BoolValue("Fishing Rod", "鱼竿", false);

    private final Map<Entity, Trail> trails = new HashMap<>();

    public Trajectories() {
        super("Trajectories", "轨迹预测", Category.Render);
    }

    @EventHandler
    public void onRender3D(Render3DEvent event) {
        if (nullCheck()) return;
        if (mc.options.hudHidden) return;

        ItemStack mainHand = mc.player.getMainHandStack();
        ItemStack offHand = mc.player.getOffHandStack();

        ItemStack stack;
        Hand hand;

        if (isSupported(mainHand)) {
            stack = mainHand;
            hand = Hand.MAIN_HAND;
        } else if (isSupported(offHand)) {
            stack = offHand;
            hand = Hand.OFF_HAND;
        } else {
            stack = null;
            hand = Hand.MAIN_HAND;
        }

        if (stack != null) {
            boolean prevBob = mc.options.getBobView().getValue();
            mc.options.getBobView().setValue(false);

            if (stack.getItem() instanceof CrossbowItem && hasMultishot(stack)) {
                renderTrajectory(stack, hand, mc.player.getYaw() - 10f, event);
                renderTrajectory(stack, hand, mc.player.getYaw(), event);
                renderTrajectory(stack, hand, mc.player.getYaw() + 10f, event);
            } else {
                renderTrajectory(stack, hand, mc.player.getYaw(), event);
            }

            mc.options.getBobView().setValue(prevBob);
        }

        if (firedProjectiles.get()) {
            renderFiredProjectiles(event);
        }
    }

    private boolean isSupported(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        Item item = stack.getItem();
        if (item instanceof BowItem) return showBow.get();
        if (item instanceof CrossbowItem) return showCrossbow.get();
        if (item instanceof TridentItem) return showTrident.get();
        if (item instanceof FishingRodItem) return showFishingRod.get();
        if (item instanceof EnderPearlItem || item instanceof SnowballItem || item instanceof EggItem || item instanceof ExperienceBottleItem || item instanceof SplashPotionItem || item instanceof LingeringPotionItem || item instanceof EnderEyeItem) {
            return showThrowables.get();
        }
        return false;
    }

    private boolean hasMultishot(ItemStack stack) {
        if (mc.world == null) return false;
        var registry = mc.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        RegistryEntry<Enchantment> multishotEntry = registry.getOrThrow(Enchantments.MULTISHOT);
        return EnchantmentHelper.getLevel(multishotEntry, stack) > 0;
    }

    private void renderTrajectory(ItemStack stack, Hand hand, float yaw, Render3DEvent event) {
        MatrixStack matrices = event.getMatrices();
        Item item = stack.getItem();

        double tickDelta = event.getTickDelta();

        double x = MathHelper.lerp(tickDelta, mc.player.lastX, mc.player.getX());
        double y = MathHelper.lerp(tickDelta, mc.player.lastY, mc.player.getY());
        double z = MathHelper.lerp(tickDelta, mc.player.lastZ, mc.player.getZ());

        y += mc.player.getEyeHeight(mc.player.getPose()) - 0.1;

        if (hand == Hand.MAIN_HAND) {
            x -= MathHelper.cos(yaw / 180.0f * (float) Math.PI) * 0.16f;
            z -= MathHelper.sin(yaw / 180.0f * (float) Math.PI) * 0.16f;
        } else {
            x += MathHelper.cos(yaw / 180.0f * (float) Math.PI) * 0.16f;
            z += MathHelper.sin(yaw / 180.0f * (float) Math.PI) * 0.16f;
        }

        float maxDist = getDistance(item);

        double motionX = -MathHelper.sin(yaw / 180.0f * (float) Math.PI) * MathHelper.cos(mc.player.getPitch() / 180.0f * (float) Math.PI) * maxDist;
        double motionY = -MathHelper.sin((mc.player.getPitch() - getThrowPitch(item)) / 180.0f * (float) Math.PI) * maxDist;
        double motionZ = MathHelper.cos(yaw / 180.0f * (float) Math.PI) * MathHelper.cos(mc.player.getPitch() / 180.0f * (float) Math.PI) * maxDist;

        float power = mc.player.getItemUseTime() / 20.0f;
        power = (power * power + power * 2.0f) / 3.0f;

        if (power > 1.0f || power == 0.0f) {
            power = 1.0f;
        }

        float distance = MathHelper.sqrt((float) (motionX * motionX + motionY * motionY + motionZ * motionZ));
        motionX /= distance;
        motionY /= distance;
        motionZ /= distance;

        float pow = (item instanceof BowItem ? power * 2.0f : item instanceof CrossbowItem ? 2.2f : 1.0f) * getThrowVelocity(item);

        motionX *= pow;
        motionY *= pow;
        motionZ *= pow;

        if (!mc.player.isOnGround()) {
            motionY += mc.player.getVelocity().getY();
        }

        int steps = maxSteps.get();

        for (int i = 0; i < steps; i++) {
            Vec3d lastPos = new Vec3d(x, y, z);

            x += motionX;
            y += motionY;
            z += motionZ;

            if (mc.world.getBlockState(new BlockPos((int) x, (int) y, (int) z)).getBlock() == Blocks.WATER) {
                motionX *= 0.8;
                motionY *= 0.8;
                motionZ *= 0.8;
            } else {
                motionX *= 0.99;
                motionY *= 0.99;
                motionZ *= 0.99;
            }

            if (item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem) {
                motionY -= 0.05;
            } else {
                motionY -= 0.03;
            }

            Vec3d pos = new Vec3d(x, y, z);

            Color lineColor = getPathColor(i);
            Render3DUtil.drawLine(matrices, lastPos, pos, lineColor, lineWidth.get().floatValue());

            if (entityBox.get()) {
                for (Entity entity : mc.world.getEntities()) {
                    if (entity.equals(mc.player)) continue;
                    Box entityBox = entity.getBoundingBox();
                    Box hitBox = new Box(x - 0.3, y - 0.3, z - 0.3, x + 0.3, y + 0.3, z + 0.3);
                    if (entityBox.intersects(hitBox)) {
                        Color landing = getLandingColor(i);
                        Color side = withAlpha(landing, 80);
                        Render3DUtil.drawFullBox(matrices, entityBox, side, landing);
                        return;
                    }
                }
            }

            BlockHitResult bhr = mc.world.raycast(new RaycastContext(
                    lastPos, pos,
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE,
                    mc.player
            ));

            if (bhr != null && bhr.getType() == HitResult.Type.BLOCK) {
                if (landingBox.get()) {
                    Color landing = getLandingColor(i);
                    Color side = withAlpha(landing, 60);
                    Box box = new Box(bhr.getBlockPos());
                    Render3DUtil.drawFullBox(matrices, box, side, landing);
                }
                return;
            }

            if (y < mc.world.getBottomY() - 5) {
                return;
            }

            if (motionX == 0 && motionY == 0 && motionZ == 0) {
                continue;
            }
        }
    }

    private float getDistance(Item item) {
        if (item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem) {
            return 1.0f;
        }
        return 0.4f;
    }

    private float getThrowVelocity(Item item) {
        if (item instanceof SplashPotionItem || item instanceof LingeringPotionItem) return 0.5f;
        if (item instanceof ExperienceBottleItem) return 0.59f;
        if (item instanceof TridentItem) return 2.0f;
        return 1.5f;
    }

    private int getThrowPitch(Item item) {
        if (item instanceof SplashPotionItem || item instanceof LingeringPotionItem || item instanceof ExperienceBottleItem) {
            return 20;
        }
        return 0;
    }

    private void renderFiredProjectiles(Render3DEvent event) {
        MatrixStack matrices = event.getMatrices();

        if (mc.world != null) {
            for (Entity entity : mc.world.getEntities()) {
                if (!(entity instanceof ProjectileEntity)) continue;
                Trail trail = trails.computeIfAbsent(entity, e -> new Trail());
                trail.addPoint(new Vec3d(entity.getX(), entity.getY(), entity.getZ()), trailLength.get());
                renderForwardPrediction((ProjectileEntity) entity, event);
            }
        }

        trails.entrySet().removeIf(entry -> entry.getKey().isRemoved() || !entry.getKey().isAlive());

        for (Trail trail : trails.values()) {
            trail.render(matrices);
        }
    }

    private class Trail {
        private final List<Vec3d> points = new ArrayList<>();

        public void addPoint(Vec3d pos, int maxPoints) {
            points.add(pos);
            while (points.size() > maxPoints) {
                points.remove(0);
            }
        }

        public void render(MatrixStack matrices) {
            if (points.size() < 2) return;

            int size = points.size();
            for (int i = 1; i < size; i++) {
                Vec3d from = points.get(i - 1);
                Vec3d to = points.get(i);
                Color color = getPathColor(i);
                Render3DUtil.drawLine(matrices, from, to, color, lineWidth.get().floatValue());
            }
        }
    }

    private void renderForwardPrediction(ProjectileEntity projectile, Render3DEvent event) {
        MatrixStack matrices = event.getMatrices();

        double x = projectile.getX();
        double y = projectile.getY();
        double z = projectile.getZ();

        Vec3d vel = projectile.getVelocity();
        double motionX = vel.x;
        double motionY = vel.y;
        double motionZ = vel.z;

        int steps = Math.min(maxSteps.get(), 600);

        double gravity = gravityFor(projectile);

        for (int i = 0; i < steps; i++) {
            Vec3d lastPos = new Vec3d(x, y, z);

            x += motionX;
            y += motionY;
            z += motionZ;

            if (mc.world.getBlockState(new BlockPos((int) x, (int) y, (int) z)).getBlock() == Blocks.WATER) {
                motionX *= 0.8;
                motionY *= 0.8;
                motionZ *= 0.8;
            } else {
                motionX *= 0.99;
                motionY *= 0.99;
                motionZ *= 0.99;
            }

            motionY -= gravity;

            Vec3d pos = new Vec3d(x, y, z);

            Color lineColor = getPathColor(i);
            Render3DUtil.drawLine(matrices, lastPos, pos, lineColor, lineWidth.get().floatValue());

            if (entityBox.get()) {
                for (Entity entity : mc.world.getEntities()) {
                    if (entity.equals(mc.player) || entity.equals(projectile)) continue;
                    Box entityBox = entity.getBoundingBox();
                    Box hitBox = new Box(x - 0.3, y - 0.3, z - 0.3, x + 0.3, y + 0.3, z + 0.3);
                    if (entityBox.intersects(hitBox)) {
                        Color landing = getLandingColor(i);
                        Color side = withAlpha(landing, 80);
                        Render3DUtil.drawFullBox(matrices, entityBox, side, landing);
                        return;
                    }
                }
            }

            BlockHitResult bhr = mc.world.raycast(new RaycastContext(
                    lastPos, pos,
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE,
                    mc.player
            ));

            if (bhr != null && bhr.getType() == HitResult.Type.BLOCK) {
                if (landingBox.get()) {
                    Color landing = getLandingColor(i);
                    Color side = withAlpha(landing, 60);
                    Box box = new Box(bhr.getBlockPos());
                    Render3DUtil.drawFullBox(matrices, box, side, landing);
                }
                return;
            }

            if (y < mc.world.getBottomY() - 5) {
                return;
            }

            if (motionX == 0 && motionY == 0 && motionZ == 0) {
                continue;
            }
        }
    }

    private double gravityFor(Entity e) {
        if (e instanceof ArrowEntity || e instanceof TridentEntity) return 0.05;
        return 0.03;
    }

    private Color getPathColor(int index) {
        return switch (colorMode.get()) {
            case Client -> ClickGui.color(index * 4);
            case Custom -> pathColor.get();
            case Rainbow -> rainbowColor(index);
        };
    }

    private Color getLandingColor(int index) {
        return switch (colorMode.get()) {
            case Client -> ClickGui.color2(index * 6);
            case Custom -> landingColor.get();
            case Rainbow -> rainbowColor(index + 50);
        };
    }

    private Color rainbowColor(int index) {
        double speed = 0.5;
        double time = System.currentTimeMillis() / 1000.0 * speed;
        float hue = (float) ((time + index * 0.03) % 1.0);
        return Color.getHSBColor(hue, 0.7f, 1.0f);
    }

    private Color withAlpha(Color base, int alpha) {
        return new Color(base.getRed(), base.getGreen(), base.getBlue(), Math.max(0, Math.min(255, alpha)));
    }
}

