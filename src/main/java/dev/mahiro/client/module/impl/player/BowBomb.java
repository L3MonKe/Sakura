package dev.mahiro.client.module.impl.player;

import dev.mahiro.client.events.EventType;
import dev.mahiro.client.events.client.TickEvent;
import dev.mahiro.client.events.packet.PacketEvent;
import dev.mahiro.client.manager.Managers;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.utils.client.ChatUtil;
import dev.mahiro.client.utils.player.MoveUtil;
import dev.mahiro.client.utils.time.TimerUtil;
import dev.mahiro.client.values.impl.BoolValue;
import dev.mahiro.client.values.impl.EnumValue;
import dev.mahiro.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class BowBomb extends Module {
    public BowBomb() {
        super("BowBomb", "弓箭漏洞", Category.Player);
    }

    private enum Exploit {
        Strong, Fast, Strict, Phobos
    }

    private final BoolValue aim = new BoolValue("Aimbot", "自动瞄准", true);
    private final NumberValue<Double> predictTicks = new NumberValue<>("Predict Ticks", "预测刻数", 3.0, 0.0, 10.0, 0.1);
    private final BoolValue rotation = new BoolValue("Rotation", "旋转", false);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 10, 0, 10, 1, rotation::get);
    private final NumberValue<Integer> spoofs = new NumberValue<>("Spoofs", "欺骗次数", 50, 0, 200, 1);
    private final EnumValue<Exploit> exploit = new EnumValue<>("Exploit", "利用方式", Exploit.Strong);
    private final BoolValue minimize = new BoolValue("Minimize", "最小化", false);
    private final NumberValue<Double> delay = new NumberValue<>("Delay", "延迟", 5.0, 0.0, 10.0, 0.1);
    private final NumberValue<Double> activeTime = new NumberValue<>("Active Time", "激活时间", 0.4, 0.0, 3.0, 0.01);
    private final BoolValue message = new BoolValue("Message", "消息", true);

    private final TimerUtil delayTimer = new TimerUtil();
    private final TimerUtil activeTimer = new TimerUtil();
    private final Random random = new Random();

    private boolean active = false;
    public static boolean send = false;

    @Override
    public void onDisable() {
        send = false;
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (!mc.player.isUsingItem() || mc.player.getActiveItem().getItem() != Items.BOW) {
            activeTimer.reset();
            active = false;
        } else {
            active = true;
        }

        if (!mc.player.isUsingItem() || mc.player.getActiveItem().getItem() != Items.BOW || !aim.get()) {
            return;
        }
        PlayerEntity target = getTarget();
        if (target == null) return;
        Vec3d headPos = target.getEyePos().add(MoveUtil.getMotionVec(target, predictTicks.get().floatValue(), true));
        Managers.ROTATION.lookAt(headPos, rotationSpeed.get());
    }

    private PlayerEntity getTarget() {
        PlayerEntity target = null;
        double distance = 100000;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (Math.abs(player.getY() - mc.player.getY()) > 4) continue;
            if (isValid(player, distance)) continue;
            target = player;
            distance = mc.player.distanceTo(player);
        }
        return target;
    }

    private boolean isValid(Entity entity, double range) {
        return entity == null || !entity.isAlive() || entity.equals(mc.player) || mc.player.getPos().distanceTo(entity.getPos()) > range;
    }

    @EventHandler
    protected void onPacketSend(PacketEvent event) {
        if (event.getType() != EventType.SEND) return;

        if (nullCheck() || !delayTimer.passedSecond(delay.get()) || !activeTimer.passedSecond(activeTime.get()) || !active)
            return;
        if (event.getPacket() instanceof PlayerActionC2SPacket packet && packet.getAction() == PlayerActionC2SPacket.Action.RELEASE_USE_ITEM) {
            send = true;
            if (message.get()) ChatUtil.addChatMessage("§rBomb");
            mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));

            if (exploit.is(Exploit.Fast)) {
                for (int i = 0; i < getRuns(); i++) {
                    spoof(mc.player.getX(), minimize.get() ? mc.player.getY() : mc.player.getY() - 1e-10, mc.player.getZ(), true);
                    spoof(mc.player.getX(), mc.player.getY() + 1e-10, mc.player.getZ(), false);
                }
            }
            if (exploit.is(Exploit.Strong)) {
                for (int i = 0; i < getRuns(); i++) {
                    spoof(mc.player.getX(), mc.player.getY() + 1e-10, mc.player.getZ(), false);
                    spoof(mc.player.getX(), minimize.get() ? mc.player.getY() : mc.player.getY() - 1e-10, mc.player.getZ(), true);
                }
            }
            if (exploit.is(Exploit.Phobos)) {
                for (int i = 0; i < getRuns(); i++) {
                    spoof(mc.player.getX(), mc.player.getY() + 0.00000000000013, mc.player.getZ(), true);
                    spoof(mc.player.getX(), mc.player.getY() + 0.00000000000027, mc.player.getZ(), false);
                }
            }
            if (exploit.is(Exploit.Strict)) {
                double[] strict_direction = new double[]{100f * -Math.sin(Math.toRadians(mc.player.getYaw())), 100f * Math.cos(Math.toRadians(mc.player.getYaw()))};
                for (int i = 0; i < getRuns(); i++) {
                    if (random.nextBoolean()) {
                        spoof(mc.player.getX() - strict_direction[0], mc.player.getY(), mc.player.getZ() - strict_direction[1], false);
                    } else {
                        spoof(mc.player.getX() + strict_direction[0], mc.player.getY(), mc.player.getZ() + strict_direction[1], true);
                    }
                }
            }
            send = false;
            delayTimer.reset();
        }
    }

    private void spoof(double x, double y, double z, boolean ground) {
        if (rotation.get()) {
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.Full(x, y, z, mc.player.getYaw(), mc.player.getPitch(), ground, mc.player.horizontalCollision));
        } else {
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, ground, mc.player.horizontalCollision));
        }
    }

    private int getRuns() {
        return spoofs.get();
    }
}
