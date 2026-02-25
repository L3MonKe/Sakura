package dev.mzc.client.mixin.render;

import dev.mzc.client.render.smoothswap.SmoothSwapManager;
import dev.mzc.client.render.smoothswap.SwapStacks;
import dev.mzc.client.render.smoothswap.SwapUtil;
import dev.mzc.client.render.smoothswap.Vec2;
import dev.mzc.client.module.impl.misc.SmoothSwap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.mzc.client.render.smoothswap.SwapUtil.getCount;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreenSmoothSwap {

    @Shadow
    @Final
    protected ScreenHandler handler;

    @Shadow
    protected int x, y;

    @Unique
    private Screen smooth_Swapping$currentScreen = null;

    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (SmoothSwap.INSTANCE == null || !SmoothSwap.INSTANCE.isEnabled()) return;
        try {
            smooth_Swapping$doRender(mouseX, mouseY);
        } catch (Exception e) {
            SwapUtil.reset();
        }
    }

    @Unique
    private void smooth_Swapping$doRender(double mouseX, double mouseY) {
        if (handler instanceof CreativeInventoryScreen.CreativeScreenHandler) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.player.currentScreenHandler == null) {
            return;
        }

        SmoothSwapManager.currentStacks = client.player.currentScreenHandler.getStacks();

        try {
            SmoothSwapManager.currentCursorStackLock.lock();
            ItemStack cursorStack = client.player.currentScreenHandler.getCursorStack();
            ItemStack prevStack = SmoothSwapManager.currentCursorStack.get();
            if (prevStack == null || (prevStack.getCount() != cursorStack.getCount() || prevStack.getItem() != cursorStack.getItem())) {
                SmoothSwapManager.currentCursorStack.set(cursorStack.copy());
            }
        } finally {
            SmoothSwapManager.currentCursorStackLock.unlock();
        }

        Screen screen = client.currentScreen;

        if (SmoothSwapManager.clickSwap) {
            SmoothSwapManager.clickSwap = false;
            SwapUtil.copyStacks(SmoothSwapManager.currentStacks, SmoothSwapManager.oldStacks);
            return;
        }

        if (smooth_Swapping$currentScreen != screen) {
            SmoothSwapManager.swaps.clear();
            SwapUtil.copyStacks(SmoothSwapManager.currentStacks, SmoothSwapManager.oldStacks);
            smooth_Swapping$currentScreen = screen;
            return;
        }

        Map<Integer, ItemStack> changedStacks = smooth_Swapping$getChangedStacks(SmoothSwapManager.oldStacks, SmoothSwapManager.currentStacks);
        if (!SmoothSwapManager.clickSwap) {
            int changedStacksSize = changedStacks.size();
            if (changedStacksSize > 1) {
                List<SwapStacks> moreStacks = new ArrayList<>();
                List<SwapStacks> lessStacks = new ArrayList<>();

                int totalAmount = 0;
                for (Map.Entry<Integer, ItemStack> stackEntry : changedStacks.entrySet()) {
                    int slotID = stackEntry.getKey();
                    ItemStack newStack = stackEntry.getValue();
                    ItemStack oldStack = SmoothSwapManager.oldStacks.get(slotID);

                    if (getCount(newStack) > getCount(oldStack) && handler.getSlot(slotID).canTakePartial(client.player)) {
                        moreStacks.add(new SwapStacks(slotID, oldStack, newStack, getCount(oldStack) - getCount(newStack)));
                        totalAmount += getCount(newStack) - getCount(oldStack);
                    } else if (getCount(newStack) < getCount(oldStack) && handler.getSlot(slotID).canTakePartial(client.player) && SmoothSwapManager.clickSwapStack == null) {
                        lessStacks.add(new SwapStacks(slotID, oldStack, newStack, getCount(oldStack) - getCount(newStack)));
                    }
                }
                if (SmoothSwapManager.clickSwapStack != null) {
                    lessStacks.clear();
                    ItemStack newStack = handler.getSlot(SmoothSwapManager.clickSwapStack).getStack();
                    ItemStack oldStack = SmoothSwapManager.oldStacks.get(SmoothSwapManager.clickSwapStack);
                    lessStacks.add(new SwapStacks(SmoothSwapManager.clickSwapStack, oldStack, newStack, totalAmount));
                    SmoothSwapManager.clickSwapStack = null;
                }
                if (moreStacks.isEmpty()) {
                    SwapUtil.assignI2CSwaps(lessStacks, new Vec2(mouseX - x, mouseY - y), handler);
                } else {
                    SwapUtil.assignI2ISwaps(moreStacks, lessStacks, handler);
                }
            } else if (changedStacksSize == 1) {
                ItemStack currentCursorStack = SmoothSwapManager.currentCursorStack.get();
                ItemStack oldCursorStack = SmoothSwapManager.oldCursorStack;
                if (currentCursorStack != null && oldCursorStack != null && currentCursorStack.getItem() == oldCursorStack.getItem() && currentCursorStack.getCount() != oldCursorStack.getCount()) {
                    changedStacks.entrySet().stream().findFirst().ifPresent(changedStack -> {
                        ItemStack oldStack = SmoothSwapManager.oldStacks.get(changedStack.getKey());
                        ItemStack currentStack = SmoothSwapManager.currentStacks.get(changedStack.getKey());
                        int cursorStackCountDiff = currentCursorStack.getCount() - SmoothSwapManager.oldCursorStack.getCount();

                        if ((oldStack.getItem() == currentStack.getItem() && oldStack.getCount() - currentStack.getCount() == cursorStackCountDiff) || currentStack.getItem() == Items.AIR) {
                            SwapStacks lessStack = new SwapStacks(changedStack.getKey(), oldStack, currentStack, getCount(oldStack) - getCount(currentStack));
                            SwapUtil.assignI2CSwaps(List.of(lessStack), new Vec2(mouseX - x, mouseY - y), handler);
                        }
                    });
                }
            }
        }

        if (!smooth_Swapping$areStacksEqual(SmoothSwapManager.oldStacks, SmoothSwapManager.currentStacks)) {
            SwapUtil.copyStacks(SmoothSwapManager.currentStacks, SmoothSwapManager.oldStacks);
            SmoothSwapManager.oldCursorStack = SmoothSwapManager.currentCursorStack.get();
        }
    }

    @Unique
    private Map<Integer, ItemStack> smooth_Swapping$getChangedStacks(DefaultedList<ItemStack> oldStacks, DefaultedList<ItemStack> newStacks) {
        Map<Integer, ItemStack> changedStacks = new HashMap<>();
        for (int slotID = 0; slotID < oldStacks.size(); slotID++) {
            ItemStack newStack = newStacks.get(slotID);
            ItemStack oldStack = oldStacks.get(slotID);
            if (!ItemStack.areEqual(oldStack, newStack)) {
                changedStacks.put(slotID, newStack.copy());
            }
        }
        return changedStacks;
    }

    @Unique
    private boolean smooth_Swapping$areStacksEqual(DefaultedList<ItemStack> oldStacks, DefaultedList<ItemStack> newStacks) {
        if (oldStacks == null || newStacks == null || (oldStacks.size() != newStacks.size())) {
            return false;
        } else {
            for (int slotID = 0; slotID < oldStacks.size(); slotID++) {
                ItemStack newStack = newStacks.get(slotID);
                ItemStack oldStack = oldStacks.get(slotID);
                if (!ItemStack.areEqual(oldStack, newStack)) {
                    return false;
                }
            }
        }
        return true;
    }
}
