package dev.mzc.client.module.impl.player;

import dev.mzc.client.mixin.accessor.IMinecraftClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.EnumValue;
import dev.mzc.client.values.impl.NumberValue;

public class FastPlace extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private Direction lockedFace = null;
    private boolean lastMouseState = false;

    public enum Mode {
        Face,   // 只在点击时进行
        Always  // 始终进行，根据设置的CPS进行放置
    }

    // 模式选择
    public final EnumValue<Mode> mode = new EnumValue<>("Mode", "选择放置模式", Mode.Face);

    private long lastClickTime = 0;  // 记录上次点击的时间
    public final NumberValue<Integer> minCps = new NumberValue<>("MinCPS", "最小CPS", 16, 1, 20, 1);
    public final NumberValue<Integer> maxCps = new NumberValue<>("MaxCPS", "最大CPS", 18, 1, 20, 1);

    public FastPlace() {
        super("FastPlace", "快速放置", Category.Player);
        this.setType(ModuleType.Safe);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled() || mc.player == null || mc.interactionManager == null) return;
            handlePlace();
        });
    }

    @Override
    public void onEnable() {
        lockedFace = null;
    }

    @Override
    public void onDisable() {
        lockedFace = null;
    }

    private void handlePlace() {

        if (mc.currentScreen != null) {
            lastMouseState = false; // 如果在 GUI 中，禁用放置
            return;
        }
        long window = mc.getWindow().getHandle();
        boolean mouseDown = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

        // 如果不是方块，直接让右键保持原版逻辑（允许吃饭、扔药水、扔经验瓶等）
        if (!(mc.player.getMainHandStack().getItem() instanceof BlockItem blockItem)) {
            lastMouseState = mouseDown;
            return;
        }

        // 当 SafeAnchor 开启且当前方块是萤石，用于给重生锚充能时，不对右键进行加速
        HitResult hit = mc.crosshairTarget;
        if (hit instanceof BlockHitResult blockHit
                && mc.world != null
                && mc.world.getBlockState(blockHit.getBlockPos()).isOf(net.minecraft.block.Blocks.RESPAWN_ANCHOR)
                && mc.player.getMainHandStack().isOf(net.minecraft.item.Items.GLOWSTONE)
                && dev.mzc.client.Sakura.MODULES.getModule(dev.mzc.client.module.impl.combat.SafeAnchor.class).isEnabled()) {
            lastMouseState = mouseDown;
            return;
        }

        // 判断当前模式
        if (mode.is(Mode.Face)) {
            handleFaceMode(blockItem, mouseDown);
        } else if (mode.is(Mode.Always)) {
            handleAlwaysMode(blockItem, mouseDown);
        }
    }

    private void handleFaceMode(BlockItem blockItem, boolean mouseDown) {
        // 禁止原版右键长按等待判定
        mc.options.useKey.setPressed(false);

        if (!(mc.crosshairTarget instanceof BlockHitResult hit) || mc.crosshairTarget.getType() != HitResult.Type.BLOCK) {
            lastMouseState = mouseDown;
            return;
        }

        if (mouseDown && !lastMouseState) {
            lockedFace = hit.getSide();
        }

        if (!mouseDown) {
            lockedFace = null;
            lastMouseState = false;
            return;
        }

        if (lockedFace == null || hit.getSide() != lockedFace) {
            lastMouseState = true;
            return;
        }

        ItemUsageContext useCtx = new ItemUsageContext(mc.player, Hand.MAIN_HAND, hit);
        ItemPlacementContext placeCtx = new ItemPlacementContext(useCtx);

        if (blockItem.getBlock().getPlacementState(placeCtx) == null) {
            lastMouseState = true;
            return;
        }

        ClientPlayerInteractionManager im = mc.interactionManager;
        ActionResult result = im.interactBlock(mc.player, Hand.MAIN_HAND, hit);

        if (result == ActionResult.SUCCESS) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }

        lastMouseState = true;
        mc.options.useKey.setPressed(false);
    }

    private void handleAlwaysMode(BlockItem blockItem, boolean mouseDown) {
        // 使用原版逻辑进行放置，以绕过 Grim 等反作弊
        // 原理：通过模拟按下右键并修改冷却时间，让原版客户端处理发包顺序和角度检查

        // 1. 强制按下右键（实现自动放置），但仅当用户按住右键时
        if (!mouseDown) {
            mc.options.useKey.setPressed(false);
            return;
        }
        mc.options.useKey.setPressed(true);

        // 2. 计算是否满足点击间隔
        long currentTime = System.currentTimeMillis();
        long timeSinceLastClick = currentTime - lastClickTime;

        // 获取当前设置的 CPS
        int min = minCps.get();
        int max = maxCps.get();
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }

        // 计算随机延迟
        // 为了避免过于规律，每次点击后重新计算下一次的延迟
        // 这里简化逻辑：如果距离上次点击已经足够久，就尝试放置
        // 注意：原版 tick 是 50ms 一次，所以只能在 tick 边界触发
        // 14 CPS = 71ms. 1.4 ticks.
        // 这意味着我们会交替 1 tick (50ms) 和 2 ticks (100ms) 触发，平均 15 CPS 或 10 CPS
        
        double randomCps = min + Math.random() * (max - min);
        int cpsDelay = (int) (1000.0 / randomCps);

        if (timeSinceLastClick >= cpsDelay) {
            // 如果满足时间间隔，将原版冷却时间设为 0，允许原版在当前 tick 进行放置
            if (mc instanceof IMinecraftClient accessor) {
                // 只有当当前冷却大于0时才重置，避免不必要的干扰
                if (accessor.hookGetItemUseCooldown() > 0) {
                    accessor.hookSetItemUseCooldown(0);
                }
            }
            // 更新最后尝试放置的时间
            // 注意：我们无法得知原版是否 *真的* 放置了（可能因为角度不对等），
            // 但为了控制频率，我们假设它尝试了。
            // 更好的做法是监听发包，但简单的计时器通常足够。
            lastClickTime = currentTime;
        }
        
        // 如果不满足时间间隔，原版自带的 itemUseCooldown 会阻止放置（默认为 4 ticks = 200ms）
        // 我们不需要做任何事，只要不重置它，它就会倒计时
    }
}
