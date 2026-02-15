package dev.sakura.client.module.impl.combat;

import dev.sakura.client.Sakura;
import dev.sakura.client.event.EventHandler;
import dev.sakura.client.event.impl.client.TickEvent;
import dev.sakura.client.event.impl.input.MoveInputEvent;
import dev.sakura.client.event.impl.render.item.HeldItemRendererEvent;
import dev.sakura.client.event.impl.render.item.UpdateHeldItemEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.math.MathUtil;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.player.MoveUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.utils.rotation.RotationUtil;
import dev.sakura.client.utils.time.TimerUtil;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class AutoThrow extends Module {

    private final NumberValue<Integer> minRange = new NumberValue<>("Min Range", "最小距离", 3, 0, 6, 1);
    private final NumberValue<Integer> maxRange = new NumberValue<>("Max Range", "最大距离", 10, 6, 20, 1);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 8, 1, 10, 1);
    private final NumberValue<Integer> minDelay = new NumberValue<>("Min Delay", "最小延迟(ms)", 100, 0, 1000, 10);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", "最大延迟(ms)", 300, 0, 1000, 10);
    private final NumberValue<Integer> switchDelay = new NumberValue<>("Switch Delay", "切换延迟(ms)", 0, 0, 1000, 10);
    private final BoolValue autoSwitch = new BoolValue("Auto Switch", "自动切换", true);
    private final BoolValue silentSwitch = new BoolValue("Silent Switch", "静默切换", false, autoSwitch::get);
    private final BoolValue inCombat = new BoolValue("In Combat", "战斗穿插", false);
    private final BoolValue pauseInAura = new BoolValue("Pause In Aura", "攻击时暂停", false);
    private final BoolValue wallCheck = new BoolValue("Wall Check", "墙体检测", true);


    public AutoThrow() {
        super("AutoThrow", "自动投掷", Category.Combat);
    }



}