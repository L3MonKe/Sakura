package dev.mzc.client.module.impl.render;

import com.mojang.authlib.GameProfile;
import dev.mzc.client.events.EventType;
import dev.mzc.client.events.client.TickEvent;
import dev.mzc.client.events.packet.PacketEvent;
import dev.mzc.client.module.Category;
import dev.mzc.client.module.Module;
import dev.mzc.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class Freecam extends Module {
    private final NumberValue<Double> speed = new NumberValue<>("Speed", "速度", 1.0, 0.1, 5.0, 0.1);
    private final NumberValue<Double> verticalSpeed = new NumberValue<>("VerticalSpeed", "垂直速度", 0.5, 0.1, 5.0, 0.1);

    private OtherClientPlayerEntity fakePlayer;
    private Vec3d startPos;
    private float startYaw;
    private float startPitch;

    public Freecam() {
        super("Freecam", "灵魂出窍", Category.Render);
        this.setType(ModuleType.All);
    }

    @Override
    protected void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        // 保存当前状态
        startPos = mc.player.getPos();
        startYaw = mc.player.getYaw();
        startPitch = mc.player.getPitch();

        // 创建假人
        spawnFakePlayer();

        // 允许飞行和穿墙
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed((float) (speed.get() / 20.0));
    }

    @Override
    protected void onDisable() {
        if (nullCheck()) return;

        // 移除假人
        removeFakePlayer();

        // 恢复状态
        mc.player.getAbilities().flying = false;
        mc.player.getAbilities().setFlySpeed(0.05f); // 默认飞行速度
        mc.player.noClip = false;

        if (startPos != null) {
            mc.player.refreshPositionAndAngles(startPos.x, startPos.y, startPos.z, startYaw, startPitch);
            mc.player.setVelocity(Vec3d.ZERO);
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        // 强制开启飞行和穿墙
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed((float) (speed.get() / 20.0));
        mc.player.noClip = true;
        mc.player.setOnGround(false);
        
        // 处理垂直移动
        if (mc.options.jumpKey.isPressed()) {
            mc.player.setVelocity(mc.player.getVelocity().add(0, verticalSpeed.get() / 20.0, 0));
        } else if (mc.options.sneakKey.isPressed()) {
            mc.player.setVelocity(mc.player.getVelocity().add(0, -verticalSpeed.get() / 20.0, 0));
        }
    }

    @EventHandler
    public void onPacket(PacketEvent event) {
        if (event.getType() == EventType.SEND) {
            Packet<?> packet = event.getPacket();
            
            // 拦截移动包，防止服务器知道我们移动了
            if (packet instanceof PlayerMoveC2SPacket) {
                event.cancel();
            }
            // 拦截输入包
            else if (packet instanceof PlayerInputC2SPacket) {
                event.cancel();
            }
            // 拦截疾跑等状态包
            else if (packet instanceof ClientCommandC2SPacket) {
                event.cancel();
            }
        }
    }

    private void spawnFakePlayer() {
        fakePlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.randomUUID(), mc.player.getName().getString()));
        fakePlayer.copyPositionAndRotation(mc.player);
        fakePlayer.bodyYaw = mc.player.bodyYaw;
        fakePlayer.headYaw = mc.player.headYaw;
        fakePlayer.getInventory().clone(mc.player.getInventory());
        fakePlayer.getAttributes().setFrom(mc.player.getAttributes());
        fakePlayer.setPose(mc.player.getPose());
        
        mc.world.addEntity(fakePlayer);
    }

    private void removeFakePlayer() {
        if (fakePlayer != null) {
            fakePlayer.discard();
            fakePlayer = null;
        }
    }
}
