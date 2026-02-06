package dev.sakura.client.events.render.item;

import dev.sakura.client.events.Cancellable;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class HeldItemRendererEvent extends Cancellable {
    private final Hand hand;
    private final ItemStack item;
    private final float equipProgress;
    private final MatrixStack matrices;

    public HeldItemRendererEvent(Hand hand, ItemStack item, float equipProgress, MatrixStack matrices) {
        this.hand = hand;
        this.item = item;
        this.equipProgress = equipProgress;
        this.matrices = matrices;
    }

    public Hand getHand() {
        return hand;
    }

    public ItemStack getItem() {
        return item;
    }

    public float getEquipProgress() {
        return equipProgress;
    }

    public MatrixStack getMatrices() {
        return matrices;
    }
}
