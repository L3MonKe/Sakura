package dev.mahiro.client.events.render.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.Objects;

public class UpdateHeldItemEvent {
    private final Hand hand;
    private ItemStack item;

    public UpdateHeldItemEvent(Hand hand, ItemStack item) {
        this.hand = hand;
        this.item = item;
    }

    public Hand getHand() {
        return this.hand;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof UpdateHeldItemEvent other)) {
            return false;
        } else if (!other.canEqual(this)) {
            return false;
        } else {
            Object this$hand = this.getHand();
            Object other$hand = other.getHand();
            if (Objects.equals(this$hand, other$hand)) {
                Object this$item = this.getItem();
                Object other$item = other.getItem();
                return Objects.equals(this$item, other$item);
            } else {
                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof UpdateHeldItemEvent;
    }

    @Override
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $hand = this.getHand();
        result = result * 59 + ($hand == null ? 43 : $hand.hashCode());
        Object $item = this.getItem();
        return result * 59 + ($item == null ? 43 : $item.hashCode());
    }

    @Override
    public String toString() {
        return "EventUpdateHeldItem(hand=" + this.getHand() + ", item=" + this.getItem() + ")";
    }
}
