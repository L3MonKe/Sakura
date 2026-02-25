package dev.mzc.client.mixin.render;

import dev.mzc.client.module.impl.misc.itemphysics.IItemEntityRenderStateExtender;
import dev.mzc.client.module.impl.misc.itemphysics.PhysicsLogic;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemEntityRenderState.class)
public class MixinItemEntityRenderState implements IItemEntityRenderStateExtender {
    @Unique
    private float rotX;
    @Unique
    private float rotY;
    @Unique
    private boolean isBlock;
    @Unique
    private boolean additionalOffset;
    @Unique
    private ItemStack stack = ItemStack.EMPTY;
    @Unique
    private ItemEntity entity;

    @Override
    public boolean isBlock() {
        return isBlock;
    }

    @Override
    public float getXRot() {
        return rotX;
    }

    @Override
    public float getYRot() {
        return rotY;
    }

    @Override
    public boolean hasAdditionalOffset() {
        return additionalOffset;
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public ItemEntity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(ItemEntity entity) {
        this.entity = entity;
    }

    @Override
    public void extractPhysic(ItemEntity entity) {
        ItemEntityRenderState state = (ItemEntityRenderState) (Object) this;
        
        this.setStack(entity.getStack());
        this.setEntity(entity);
        
        // Approximate isBlock logic using simple instanceof check
        // Original logic used render layers, but we can't access them easily
        this.isBlock = entity.getStack().getItem() instanceof BlockItem;
        
        PhysicsLogic.calculateRotation(entity, state);
        
        // Simplified check for additional offset (skipping config block tags for now)
        this.additionalOffset = false; 
        
        this.rotX = entity.getPitch();
        this.rotY = entity.getYaw();
    }
}
