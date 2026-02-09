package dev.sakura.client.module.impl.combat;

import dev.sakura.client.events.client.TickEvent;
import dev.sakura.client.manager.Managers;
import dev.sakura.client.manager.impl.RotationManager;
import dev.sakura.client.module.Category;
import dev.sakura.client.module.Module;
import dev.sakura.client.utils.player.FindItemResult;
import dev.sakura.client.utils.player.InvUtil;
import dev.sakura.client.utils.rotation.MovementFix;
import dev.sakura.client.utils.rotation.Rotation;
import dev.sakura.client.values.impl.BoolValue;
import dev.sakura.client.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.movement.Scaffold;
import dev.sakura.client.utils.time.TimerUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import dev.sakura.client.events.render.item.HeldItemRendererEvent;
import net.minecraft.item.ItemStack;

public class AutoThrow extends Module {

    private final NumberValue<Integer> minRange = new NumberValue<>("Min Range", "最小距离", 3, 0, 6, 1);
    private final NumberValue<Integer> maxRange = new NumberValue<>("Max Range", "最大距离", 10, 6, 20, 1);
    private final NumberValue<Integer> rotationSpeed = new NumberValue<>("Rotation Speed", "旋转速度", 100, 1, 180, 1);
    private final NumberValue<Integer> minDelay = new NumberValue<>("Min Delay", "最小延迟(ms)", 100, 0, 1000, 10);
    private final NumberValue<Integer> maxDelay = new NumberValue<>("Max Delay", "最大延迟(ms)", 300, 0, 1000, 10);
    private final BoolValue autoSwitch = new BoolValue("Auto Switch", "自动切换", true);
    private final BoolValue silentSwitch = new BoolValue("Silent Switch", "静默切换", false, autoSwitch::get);
    private final BoolValue inCombat = new BoolValue("In Combat", "战斗穿插", false);
    private final BoolValue pauseInAura = new BoolValue("Pause In Aura", "攻击时暂停", false);
    private final BoolValue wallCheck = new BoolValue("Wall Check", "墙体检测", true);

    private LivingEntity target;
    private final TimerUtil throwTimer = new TimerUtil();
    private long nextDelay = 0;
    
    private boolean shouldSwapBack;
    
    private boolean isThrowing = false;
    private int oldSlot = -1;

    public AutoThrow() {
        super("AutoThrow", "自动投掷", Category.Combat);
        
        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> {
            if (minecraftClient.player == null || minecraftClient.world == null) return;
            if (!shouldSwapBack) return;

            shouldSwapBack = false;
            InvUtil.swapBack();
        });
    }

    @Override
    public void onEnable() {
        updateNextDelay();
        shouldSwapBack = false;
    }

    @Override
    public void onDisable() {
        target = null;
        if (silentSwitch.get()) {
            shouldSwapBack = true;
        }
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onHeldItemRender(HeldItemRendererEvent event) {
        if (!silentSwitch.get()) return;
        if (mc.player == null) return;

        if (isThrowing && oldSlot != -1 && event.getHand() == Hand.MAIN_HAND) {
             event.setItem(mc.player.getInventory().getStack(oldSlot));
             return;
        }

        int currentSlot = mc.player.getInventory().getSelectedSlot();
        ItemStack currentStack = mc.player.getInventory().getStack(currentSlot);

        if (event.getHand() == Hand.MAIN_HAND && (currentStack.getItem() == Items.SNOWBALL || currentStack.getItem() == Items.EGG)) {
             int bestSlot = -1;
             for (int i = 0; i < 9; i++) {
                 ItemStack s = mc.player.getInventory().getStack(i);
                 if (!s.isEmpty() && s.getItem() != Items.SNOWBALL && s.getItem() != Items.EGG) {
                     bestSlot = i;
                     break; 
                 }
             }
             
             if (bestSlot != -1) {
                 event.setItem(mc.player.getInventory().getStack(bestSlot));
             }
        }
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPreTick(TickEvent.Pre event) {
        if (nullCheck()) return;

        if (Sakura.MODULES.getModule(Scaffold.class).isEnabled()) return;
        
        KillAura killAura = Sakura.MODULES.getModule(KillAura.class);
        if (pauseInAura.get() && killAura.isEnabled() && killAura.getCurrentTarget() != null) {
            return;
        }

        if (silentSwitch.get()) {
            InvUtil.swapBack();
        }

        if (!throwTimer.passedMillise(nextDelay)) {
            if (target != null) {
                target = null;
            }
            return;
        }

        FindItemResult result = InvUtil.findInHotbar(itemStack -> 
            itemStack.getItem() == Items.SNOWBALL || itemStack.getItem() == Items.EGG
        );

        if (!result.found()) {
            target = null;
            return;
        }

        findTarget();

        if (target != null) {
            if (mc.player == null) return;
            if (inCombat.get() && mc.player.distanceTo(target) <= 3.0) {
                 if (killAura.isEnabled() && killAura.getCurrentTarget() != null) {
                     float cooldown = mc.player.getAttackCooldownProgress(0.0f);
                     
                     if (cooldown > 0.6f) {
                         target = null; 
                         return;
                     }
                 }
            }

            Rotation targetRotation = calculateArc(target);
            
            Managers.ROTATION.setRotations(targetRotation, rotationSpeed.get(), MovementFix.NORMAL, RotationManager.Priority.Highest);
        }
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPostTick(TickEvent.Post event) {
        if (nullCheck()) return;
        
        if (isThrowing) {
            mc.options.useKey.setPressed(false);
            isThrowing = false;
            
            if (oldSlot != -1 && autoSwitch.get()) {
                if (mc.player != null) mc.player.getInventory().setSelectedSlot(oldSlot);
            }
            oldSlot = -1;
            
            throwTimer.reset();
            updateNextDelay();
            return;
        }

        if (mc.player == null) return;
        if (mc.player.isUsingItem()) return;

        if (target != null && throwTimer.passedMillise(nextDelay)) {
            Rotation targetRotation = calculateArc(target);

            if (isRotated(targetRotation)) {
                FindItemResult result = InvUtil.findInHotbar(itemStack -> 
                    itemStack.getItem() == Items.SNOWBALL || itemStack.getItem() == Items.EGG
                );

                if (result.found()) {
                    throwItem(result.slot());
                }
            }
        }
    }

    private void findTarget() {
        if (target != null) {
            if (!isInvalid(target)) {
                return;
            }
        }
        
        target = null;
        for (LivingEntity entity : Managers.COMBAT.getEntities(maxRange.get())) {
            if (isInvalid(entity)) continue;
            target = entity;
            break; 
        }
    }

    private boolean isInvalid(LivingEntity entity) {
        if (entity == null || !entity.isAlive()) return true;
        if (mc.player == null) return true;
        
        if (wallCheck.get() && !mc.player.canSee(entity)) return true;
        
        float dist = mc.player.distanceTo(entity);
        return dist > maxRange.get() || dist < minRange.get();
    }

    private Rotation calculateArc(LivingEntity target) {
        if (mc.player == null) return new Rotation(0, 0);
        double posX = target.getX() + (target.getX() - target.lastX) * 2.0 - mc.player.getX();
        double posY = target.getY() + target.getEyeHeight(target.getPose()) * 0.5 - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
        double posZ = target.getZ() + (target.getZ() - target.lastZ) * 2.0 - mc.player.getZ();
        
        double distance = Math.sqrt(posX * posX + posZ * posZ);
        
        double v = 1.5;
        double g = 0.03;
        
        double time = distance / v;
        double drop = 0.5 * g * time * time;
        
        posY += drop;
        
        float pitch = (float) -Math.toDegrees(Math.atan2(posY, distance));
        float yaw = (float) Math.toDegrees(Math.atan2(posZ, posX)) - 90.0F;

        return new Rotation(yaw, pitch);
    }

    private boolean isRotated(Rotation targetRotation) {
        Rotation current = Managers.ROTATION.getRotation();
        float yawDiff = Math.abs(current.yaw - targetRotation.yaw) % 360;
        if (yawDiff > 180) yawDiff = 360 - yawDiff;
        return yawDiff < 10 && Math.abs(current.pitch - targetRotation.pitch) < 10;
    }

    private void throwItem(int slot) {
        if (mc.player == null) return;
        int currentSlot = mc.player.getInventory().getSelectedSlot();
        if (currentSlot != slot) {
             oldSlot = currentSlot;
             mc.player.getInventory().setSelectedSlot(slot);
        } else {
             oldSlot = -1;
        }

        mc.options.useKey.setPressed(true);
        isThrowing = true;
    }

    private void updateNextDelay() {
        if (minDelay.get() >= maxDelay.get()) {
            nextDelay = minDelay.get();
        } else {
            nextDelay = (long) (minDelay.get() + Math.random() * (maxDelay.get() - minDelay.get()));
        }
    }
}
