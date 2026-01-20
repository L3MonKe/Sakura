package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.manager.impl.RotationManager;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.player.InvUtil;
import dev.mahiro.client.utils.rotation.MovementFix;
import dev.mahiro.client.utils.vector.Rotation;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;

public class AutoPearl extends Module {
    public static AutoPearl INSTANCE;

    private final BoolValue inventorySwap = new BoolValue("InventorySwap", "背包切换", true);
    private final BoolValue rotation = new BoolValue("Rotation", "旋转", false);
    private final NumberValue<Double> rotationSpeed = new NumberValue<>("RotationSpeed", "旋转速度", 0.5, 0.0, 1.0, 0.01, rotation::get);
    private final NumberValue<Double> fov = new NumberValue<>("Fov", "视场", 10.0, 0.0, 50.0, 1.0, rotation::get);

    private boolean shouldThrow = false;
    public static boolean throwing = false;

    public AutoPearl() {
        super("AutoPearl", "自动珍珠", Category.Player);
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        if (nullCheck()) {
            setState(false);
            return;
        }

        if (rotation.get()) {
            return;
        }

        if (getBindMode() == BindMode.Hold) {
            shouldThrow = true;
            return;
        }

        throwPearl(mc.player.getYaw(), mc.player.getPitch());
        setState(false);
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (!rotation.get()) return;

        if (Managers.ROTATION.inFov(mc.player.getYaw(), mc.player.getPitch(), fov.get())) {
            throwing = true;
            int pearl;

            if (mc.player.getMainHandStack().getItem() == Items.ENDER_PEARL) {
                mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, mc.player.getYaw(), mc.player.getPitch()));
            } else if (inventorySwap.get() && (pearl = InvUtil.find(Items.ENDER_PEARL).slot()) != -1) {
                InvUtil.invSwap(pearl);
                mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, mc.player.getYaw(), mc.player.getPitch()));
                InvUtil.swapBack();
            } else if ((pearl = InvUtil.findInHotbar(Items.ENDER_PEARL).slot()) != -1) {
                InvUtil.swap(pearl, false);
                mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, mc.player.getYaw(), mc.player.getPitch()));
                InvUtil.swapBack();
            }

            throwing = false;
            setState(false);
        } else {
            Managers.ROTATION.setRotations(
                    new Rotation(mc.player.getYaw(), mc.player.getPitch()),
                    rotationSpeed.get() * 10,
                    MovementFix.OFF,
                    RotationManager.Priority.High
            );
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player == null) return;

        if (shouldThrow && getBindMode() == BindMode.Hold) {
            shouldThrow = false;
            throwPearl(mc.player.getYaw(), mc.player.getPitch());
        }
    }

    public void throwPearl(float yaw, float pitch) {
        if (mc.player == null || mc.getNetworkHandler() == null) return;

        throwing = true;
        int pearl;

        if (mc.player.getMainHandStack().getItem() == Items.ENDER_PEARL) {
            mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, yaw, pitch));
        } else if (inventorySwap.get() && (pearl = InvUtil.find(Items.ENDER_PEARL).slot()) != -1) {
            InvUtil.invSwap(pearl);
            mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, yaw, pitch));
            InvUtil.invSwapBack();
        } else if ((pearl = InvUtil.findInHotbar(Items.ENDER_PEARL).slot()) != -1) {
            int old = mc.player.getInventory().selectedSlot;
            InvUtil.swap(pearl, false);
            mc.getNetworkHandler().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, yaw, pitch));
            InvUtil.swap(old, false);
        }

        throwing = false;
    }
}