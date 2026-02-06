package dev.sakura.client.module.impl.render;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.events.packet.PacketEvent;
import dev.sakura.client.events.type.EventType;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.ColorValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

import java.awt.*;

public class WorldTweaks extends Module {
    public WorldTweaks() {
        super("WorldTweaks", "世界调整", Category.Render);
    }

    public final BoolValue fogModify = new BoolValue("Fog Modify", "雾修改", true);
    public final NumberValue<Integer> fogStart = new NumberValue<>("Fog Start", "雾起点", 0, 0, 256, 1, () -> fogModify.get());
    public final NumberValue<Integer> fogEnd = new NumberValue<>("Fog End", "雾终点", 64, 10, 256, 1, () -> fogModify.get());
    public final ColorValue fogColor = new ColorValue("Fog Color", "雾颜色", new Color(0xA900FF), () -> fogModify.get());

    public final BoolValue changeTime = new BoolValue("Change Time", "锁定时间", false);
    public final NumberValue<Integer> time = new NumberValue<>("Time", "时间", 21, 0, 23, 1, () -> changeTime.get());

    private long oldTime;
    private long oldTimeOfDay;
    private boolean oldTickDayTime = true;
    private boolean forcedNoTickDayTime;

    private boolean lastServerTickDayTime = true;
    private boolean hasServerTime;

    @Override
    protected void onEnable() {
        if (mc.world == null) return;
        oldTime = mc.world.getTime();
        oldTimeOfDay = mc.world.getTimeOfDay();
        oldTickDayTime = true;
        forcedNoTickDayTime = false;
    }

    @Override
    protected void onDisable() {
        if (mc.world instanceof ClientWorld clientWorld) {
            clientWorld.setTime(oldTime, oldTimeOfDay, oldTickDayTime);
        }
        forcedNoTickDayTime = false;
    }

    @EventHandler
    private void onPacket(PacketEvent event) {
        if (event.getType() != EventType.RECEIVE) return;
        if (!changeTime.get()) return;
        if (!(event.getPacket() instanceof WorldTimeUpdateS2CPacket packet)) return;

        lastServerTickDayTime = packet.tickDayTime();
        hasServerTime = true;
        event.setCancelled(true);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (nullCheck()) return;
        if (!(mc.world instanceof ClientWorld clientWorld)) return;

        if (changeTime.get()) {
            long desiredTimeOfDay = time.get().longValue() * 1000L;
            clientWorld.setTime(clientWorld.getTime(), desiredTimeOfDay, false);
            forcedNoTickDayTime = true;
        } else if (forcedNoTickDayTime) {
            boolean tickDayTime = hasServerTime ? lastServerTickDayTime : true;
            clientWorld.setTime(clientWorld.getTime(), clientWorld.getTimeOfDay(), tickDayTime);
            forcedNoTickDayTime = false;
        }
    }
}
