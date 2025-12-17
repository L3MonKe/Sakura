package dev.sakura.module.impl.movement;

import dev.sakura.Sakura;
import dev.sakura.events.client.TickEvent;
import dev.sakura.events.player.MotionEvent;
import dev.sakura.events.type.EventType;
import dev.sakura.module.Category;
import dev.sakura.module.Module;
import dev.sakura.utils.entity.EntityUtil;
import dev.sakura.utils.player.MovementUtils;
import dev.sakura.values.impl.BoolValue;
import dev.sakura.values.impl.EnumValue;
import dev.sakura.values.impl.NumberValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Step extends Module {
    public static Step INSTANCE;

    private final EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.Vanilla);
    private final NumberValue<Double> height = new NumberValue<>("Height", 1.0, 0.5, 4.0, 0.5);
    private final BoolValue onlyMoving = new BoolValue("OnlyMoving", true);
    private final BoolValue useTimer = new BoolValue("Timer", true, () -> mode.get() == Mode.NCP || mode.get() == Mode.OldNCP);
    private final BoolValue fast = new BoolValue("Fast", true, () -> mode.get() == Mode.NCP && useTimer.get());
    private final BoolValue inWebPause = new BoolValue("InWebPause", true);
    private final BoolValue sneakingPause = new BoolValue("SneakingPause", true);

    private boolean timer;
    private int packets = 0;
    private float savedTimer = 1.0f;

    public Step() {
        super("Step", Category.Movement);
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        Sakura.EVENT_BUS.subscribe(this);
    }

    @Override
    protected void onDisable() {
        Sakura.EVENT_BUS.unsubscribe(this);
        if (mc.player != null) {
            setStepHeight(0.6f);
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.world == null) return;

        setSuffix(mode.get().name());

        if (sneakingPause.get() && mc.player.isSneaking() ||
                mc.player.isInLava() ||
                mc.player.isTouchingWater() ||
                inWebPause.get() && EntityUtil.isInWeb(mc.player) ||
                !mc.player.isOnGround() ||
                onlyMoving.get() && !MovementUtils.isMoving()) {
            setStepHeight(0.6f);
            return;
        }

        setStepHeight(height.get().floatValue());
    }

    @EventHandler
    public void onMotion(MotionEvent event) {
        if (mc.player == null) return;

        if (event.getType() == EventType.POST) {
            packets--;
            return;
        }

        if (timer && packets <= 0) {
            timer = false;
        }

        boolean strict = mode.get() == Mode.NCP;

        if (mode.get() == Mode.OldNCP || strict) {
            double stepHeight = mc.player.getY() - mc.player.prevY;

            if (stepHeight <= 0.75 || stepHeight > height.get()) {
                return;
            }

            double[] offsets = getOffset(stepHeight);
            if (offsets != null && offsets.length > 1) {
                if (useTimer.get()) {
                    timer = true;
                    packets = 2;
                }

                for (double offset : offsets) {
                    mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                            mc.player.prevX,
                            mc.player.prevY + offset,
                            mc.player.prevZ,
                            false,
                            mc.player.horizontalCollision
                    ));
                }
            }
        }
    }

    private double[] getOffset(double height) {
        boolean strict = mode.get() == Mode.NCP;

        if (height == 0.75) {
            if (strict) {
                return new double[]{0.42, 0.753, 0.75};
            } else {
                return new double[]{0.42, 0.753};
            }
        } else if (height == 0.8125) {
            if (strict) {
                return new double[]{0.39, 0.7, 0.8125};
            } else {
                return new double[]{0.39, 0.7};
            }
        } else if (height == 0.875) {
            if (strict) {
                return new double[]{0.39, 0.7, 0.875};
            } else {
                return new double[]{0.39, 0.7};
            }
        } else if (height == 1) {
            if (strict) {
                return new double[]{0.42, 0.753, 1};
            } else {
                return new double[]{0.42, 0.753};
            }
        } else if (height == 1.5) {
            return new double[]{0.42, 0.75, 1.0, 1.16, 1.23, 1.2};
        } else if (height == 2) {
            return new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
        } else if (height == 2.5) {
            return new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
        }

        return null;
    }

    private void setStepHeight(float value) {
        if (mc.player == null) return;
        var attribute = mc.player.getAttributeInstance(EntityAttributes.STEP_HEIGHT);
        if (attribute != null) {
            attribute.setBaseValue(value);
        }
    }

    private enum Mode {
        Vanilla,
        OldNCP,
        NCP
    }
}