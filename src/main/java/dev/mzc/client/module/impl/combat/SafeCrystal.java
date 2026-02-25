package dev.mzc.client.module.impl.combat;

import dev.mzc.client.values.impl.BoolValue;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.NumberValue;


/**
 * AutoCrystal = CrystalClicker + CrystalPlacer
 * 逻辑保持完全一致，仅增加开关
 */
public class SafeCrystal extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    /* ================= 设置开关 ================= */

    private final BoolValue autoBreak = new BoolValue("AutoBreak", "自动破坏", true);
    private final BoolValue autoPlace = new BoolValue("AutoPlace", "自动放置", true);
    private final NumberValue<Double> AntiSuicide = new NumberValue<>("AntiSuicide", "防自杀", 2.0, 1.0, 20.0, 0.5);


    /* ================= 冷却 ================= */

    private int attackCooldown = 0;
    private int placeCooldown = 0;

    private static final double MAX_ATTACK_DISTANCE = 3.6D;

    public SafeCrystal() {
        super("SafeCrystal", "安全水晶",Category.Combat);
        this.setType(ModuleType.Safe);

        ClientTickEvents.END_CLIENT_TICK.register(c -> {
            if (!isEnabled() || mc.player == null || mc.interactionManager == null) return;

            if (autoBreak.get()) {
                handleAutoCrystalBreak();
            }

            if (autoPlace.get()) {
                handleAutoCrystalPlace();
            }
        });
    }

    @Override
    public void onEnable() {
        attackCooldown = 0;
        placeCooldown = 0;
    }

    @Override
    public void onDisable() {
        attackCooldown = 0;
        placeCooldown = 0;;
    }

    /* ===================================================== */
    /* ================= 自动破坏（水晶） ================= */
    /* ===================================================== */

    private void handleAutoCrystalBreak() {

        // ❤️ 可配置血量保护
        if (mc.player.getHealth() < AntiSuicide.get().floatValue()) return;
        if (attackCooldown > 0) {
            attackCooldown--;
            return;
        }

        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof EntityHitResult ehr)) return;

        if (!(ehr.getEntity() instanceof EndCrystalEntity crystal)) return;

        if (mc.player.distanceTo(crystal) > MAX_ATTACK_DISTANCE) return;

        /* ===== 防自杀判断（保持原逻辑） ===== */

        boolean playerHigherOrEqual =
                mc.player.getY() >= crystal.getY();

        if (playerHigherOrEqual) {
            Vec3d start = mc.player.getEyePos();
            Vec3d end = crystal.getPos();

            BlockHitResult blockHit = mc.world.raycast(new RaycastContext(
                    start,
                    end,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    mc.player
            ));

            if (blockHit.getType() == HitResult.Type.MISS) {
                return;
            }
        }

        mc.interactionManager.attackEntity(mc.player, crystal);
        mc.player.swingHand(Hand.MAIN_HAND);

        attackCooldown = 1 + (int) (Math.random() * 2);
    }

    /* ===================================================== */
    /* ================= 自动放置（水晶） ================= */
    /* ===================================================== */

    private void handleAutoCrystalPlace() {

        if (placeCooldown > 0) {
            placeCooldown--;
            return;
        }

        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof BlockHitResult bhr)) return;

        BlockPos base = bhr.getBlockPos();

        // 只允许黑曜石 / 基岩
        if (!mc.world.getBlockState(base).isOf(Blocks.OBSIDIAN)
                && !mc.world.getBlockState(base).isOf(Blocks.BEDROCK)) {
            return;
        }

        BlockPos above = base.up();

        // 上方必须是空气
        if (!mc.world.getBlockState(above).isAir()) return;

        // 上方只要有任何实体就不放
        Box box = new Box(
                above.getX(), above.getY(), above.getZ(),
                above.getX() + 1, above.getY() + 2, above.getZ() + 1
        );

        if (!mc.world.getOtherEntities(null, box).isEmpty()) {
            return;
        }

        Hand crystalHand = getCrystalHand();
        if (crystalHand == null) return;

        mc.interactionManager.interactBlock(
                mc.player,
                crystalHand,
                bhr
        );

        mc.player.swingHand(crystalHand);

        placeCooldown = 1 + (int) (Math.random() * 2);
    }

    /* ================= 工具方法 ================= */

    private Hand getCrystalHand() {
        if (mc.player.getMainHandStack().isOf(Items.END_CRYSTAL))
            return Hand.MAIN_HAND;

        if (mc.player.getOffHandStack().isOf(Items.END_CRYSTAL))
            return Hand.OFF_HAND;

        return null;
    }

    @Override
    public String getSuffix() {
        if (autoBreak.get() && autoPlace.get()) return "Break+Place";
        if (autoBreak.get()) return "Break";
        if (autoPlace.get()) return "Place";
        return "None";
    }


}
