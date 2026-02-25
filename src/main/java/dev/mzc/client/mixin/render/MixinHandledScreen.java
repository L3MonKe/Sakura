package dev.mzc.client.mixin.render;

import dev.mzc.client.Sakura;
import dev.mzc.client.events.input.MouseDraggedEvent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen extends Screen {

    protected MixinHandledScreen(net.minecraft.text.Text title) {
        super(title);
    }

    @Shadow
    @Nullable
    protected abstract Slot getSlotAt(double x, double y);

    @Shadow
    protected abstract void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType);

    @Shadow
    private boolean doubleClicking;

    private Slot lastClickedSlot;

    @Inject(method = "mouseReleased", at = @At("HEAD"))
    private void onMouseReleased(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        lastClickedSlot = null;
    }

    @Inject(method = "mouseDragged", at = @At("TAIL"))
    private void onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY, CallbackInfoReturnable<Boolean> cir) {
        MouseDraggedEvent event = new MouseDraggedEvent();
        Sakura.EVENT_BUS.post(event);

        if (button != GLFW_MOUSE_BUTTON_LEFT || doubleClicking || !event.isCancelled()) {
            return;
        }

        Slot slot = getSlotAt(mouseX, mouseY);
        if (slot != null && slot.hasStack() && hasShiftDown() && slot != lastClickedSlot) {
            lastClickedSlot = slot;
            onMouseClick(slot, slot.id, button, SlotActionType.QUICK_MOVE);
        }
    }
}
