package dev.sakura.client.module.impl.movement;

import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.key.KeyEvent;
import dev.sakura.client.event.impl.render.Render3DEvent;
import dev.sakura.client.event.type.KeyAction;
import dev.sakura.client.gui.clickgui.ClickGuiScreen;
import dev.sakura.client.mixin.accessor.ICreativeInventoryScreen;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.EnumValue;
import dev.sakura.client.values.impl.NumberValue;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class GuiMove extends Module {
    public GuiMove() {
        super("GuiMove", "界面移动", Category.Movement);
    }

    public enum Screens {
        ClickGui,
        Inventory,
        Both
    }

    public final BoolValue jump = new BoolValue("Jump", "跳跃", true);
    public final BoolValue sneak = new BoolValue("Sneak", "潜行", true);
    public final BoolValue sprint = new BoolValue("Sprint", "冲刺", true);

    private final BoolValue arrowsRotate = new BoolValue("ArrowsRotate", "方向键旋转", true);
    private final NumberValue<Double> rotateSpeed = new NumberValue<>("RotateSpeed", "旋转速度", 4.0, 0.0, 10.0, 0.1);

    private final EnumValue<Screens> screens = new EnumValue<>("Screens", "界面", Screens.Inventory);

    @Override
    public void onDisable() {
        mc.options.forwardKey.setPressed(false);
        mc.options.backKey.setPressed(false);
        mc.options.leftKey.setPressed(false);
        mc.options.rightKey.setPressed(false);

        if (jump.get()) mc.options.jumpKey.setPressed(false);
        if (sneak.get()) mc.options.sneakKey.setPressed(false);
        if (sprint.get()) mc.options.sprintKey.setPressed(false);
    }

    public boolean disableSpace() {
        return isEnabled() && jump.get() && mc.options.jumpKey.isPressed();
    }

    public boolean disableArrows() {
        return isEnabled() && arrowsRotate.get();
    }

    @EventHandler
    private void onKey(KeyEvent event) {
        if (skip()) return;

        pass(mc.options.forwardKey, event.getKey(), event.getAction());
        pass(mc.options.backKey, event.getKey(), event.getAction());
        pass(mc.options.leftKey, event.getKey(), event.getAction());
        pass(mc.options.rightKey, event.getKey(), event.getAction());

        if (jump.get()) pass(mc.options.jumpKey, event.getKey(), event.getAction());
        if (sneak.get()) pass(mc.options.sneakKey, event.getKey(), event.getAction());
        if (sprint.get()) pass(mc.options.sprintKey, event.getKey(), event.getAction());
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (skip()) return;

        float rotationDelta = (float) (rotateSpeed.get() * event.getTickDelta() * 20.0);

        if (arrowsRotate.get()) {
            float yaw = mc.player.getYaw();
            float pitch = mc.player.getPitch();

            if (isKeyPressed(GLFW.GLFW_KEY_LEFT)) yaw -= rotationDelta;
            if (isKeyPressed(GLFW.GLFW_KEY_RIGHT)) yaw += rotationDelta;
            if (isKeyPressed(GLFW.GLFW_KEY_UP)) pitch -= rotationDelta;
            if (isKeyPressed(GLFW.GLFW_KEY_DOWN)) pitch += rotationDelta;

            pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);

            mc.player.setYaw(yaw);
            mc.player.setPitch(pitch);
        }
    }

    private void pass(KeyBinding bind, int key, KeyAction action) {
        if (key != GLFW.GLFW_KEY_UNKNOWN && key != bind.getDefaultKey().getCode()) return;
        if (action == KeyAction.Press) bind.setPressed(true);
        if (action == KeyAction.Release) bind.setPressed(false);
    }

    private boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(mc.getWindow().getHandle(), key) == GLFW.GLFW_PRESS;
    }

    public boolean skip() {
        if (mc.currentScreen == null || (mc.currentScreen instanceof CreativeInventoryScreen && ICreativeInventoryScreen.getSelectedTab() == ItemGroups.getSearchGroup()) || mc.currentScreen instanceof ChatScreen || mc.currentScreen instanceof SignEditScreen || mc.currentScreen instanceof AnvilScreen || mc.currentScreen instanceof AbstractCommandBlockScreen || mc.currentScreen instanceof StructureBlockScreen)
            return true;
        if (screens.get() == Screens.ClickGui && !(mc.currentScreen instanceof ClickGuiScreen)) return true;
        return screens.get() == Screens.Inventory && mc.currentScreen instanceof ClickGuiScreen;
    }
}
