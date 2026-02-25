package dev.mzc.client.module.impl.movement;

import dev.mzc.client.events.client.OpenScreenEvent;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

public class GuiMove extends Module {

    public GuiMove() {
        super("GUIMove", "界面移动", Category.Movement);
        this.setType(ModuleType.Safe);
    }

    @Override
    public void onEnable() {
        applyKeyState();
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;
        handleMovement();
        keepSprint();
    }

    @EventHandler
    public void onOpenScreen(OpenScreenEvent event) {
        if (nullCheck()) return;
        handleMovement();
    }

    private void handleMovement() {
        if (mc.currentScreen instanceof ChatScreen) return;
        if (!(mc.currentScreen instanceof HandledScreen)) return;
        applyKeyState();
    }

    private void applyKeyState() {
        if (mc.getWindow() == null) return;
        long w = mc.getWindow().getHandle();
        mc.options.forwardKey.setPressed(isKeyDown(w, mc.options.forwardKey.getDefaultKey().getCode()));
        mc.options.backKey.setPressed(isKeyDown(w, mc.options.backKey.getDefaultKey().getCode()));
        mc.options.leftKey.setPressed(isKeyDown(w, mc.options.leftKey.getDefaultKey().getCode()));
        mc.options.rightKey.setPressed(isKeyDown(w, mc.options.rightKey.getDefaultKey().getCode()));
        mc.options.jumpKey.setPressed(isKeyDown(w, mc.options.jumpKey.getDefaultKey().getCode()));
    }

    private void keepSprint() {
        PlayerEntity p = mc.player;
        if (p == null) return;

        boolean sprinting =
                p.forwardSpeed > 0 &&
                        !p.isSneaking() &&
                        !p.isUsingItem() &&
                        p.getHungerManager().getFoodLevel() > 6;

        if (sprinting) p.setSprinting(true);
    }

    private boolean isKeyDown(long window, int key) {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

    private void releaseKeys() {
        mc.options.forwardKey.setPressed(false);
        mc.options.backKey.setPressed(false);
        mc.options.leftKey.setPressed(false);
        mc.options.rightKey.setPressed(false);
        mc.options.jumpKey.setPressed(false);
    }
}