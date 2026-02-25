package dev.mzc.client.module.impl.misc.itemphysics;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;

public interface IItemEntityRenderStateExtender {
    boolean isBlock();
    float getXRot();
    float getYRot();
    boolean hasAdditionalOffset();
    void extractPhysic(ItemEntity entity);
    
    ItemStack getStack();
    void setStack(ItemStack stack);
    
    ItemEntity getEntity();
    void setEntity(ItemEntity entity);
}
