package dev.mzc.client.module.impl.misc.itemphysics;

import dev.mzc.client.mixin.accessor.IEntity;
import dev.mzc.client.module.impl.misc.ItemPhysics;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Vector3f;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.entity.LivingEntity;

public class PhysicsLogic {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final float BASE_MULTIPLIER = 0.25F;
    private static final double RANDOM_Y_OFFSET_SCALE = 0.05 / (Math.PI * 2);

    public static void calculateRotation(ItemEntity entity, ItemEntityRenderState state) {
        if (ItemPhysics.INSTANCE == null || !ItemPhysics.INSTANCE.isEnabled()) return;

        float rotateBy = mc.getRenderTickCounter().getLastFrameDuration() * BASE_MULTIPLIER * ItemPhysics.INSTANCE.rotateSpeed.get().floatValue();
        
        if (mc.isPaused()) rotateBy = 0;

        Vec3d motionMultiplier = ((IEntity) entity).getMovementMultiplier();

        IItemEntityRenderStateExtender extender = (IItemEntityRenderStateExtender) state;
        boolean isBlock = extender.isBlock();

        if (isBlock) {
            if (!entity.isOnGround()) {
                rotateBy *= 2;
                Fluid fluid = calculateFluid(entity, false);
                if (fluid == null) fluid = calculateFluid(entity, true);
                
                if (fluid != null) {
                    rotateBy /= 2.0f; // Simple drag
                }

                entity.setPitch(entity.getPitch() + rotateBy);
            } else if (ItemPhysics.INSTANCE.oldRotation.get()) {
                for (int side = 0; side < 4; side++) {
                    double rotation = side * 90;
                    double range = 5;
                    if (entity.getPitch() > rotation - range && entity.getPitch() < rotation + range)
                        entity.setPitch((float) rotation);
                }
                
                float pitch = entity.getPitch();
                if (pitch != 0 && pitch != 90 && pitch != 180 && pitch != 270) {
                    double dist0 = Math.abs(pitch);
                    double dist90 = Math.abs(pitch - 90);
                    double dist180 = Math.abs(pitch - 180);
                    double dist270 = Math.abs(pitch - 270);
                    
                    if (dist0 <= dist90 && dist0 <= dist180 && dist0 <= dist270)
                        entity.setPitch(pitch < 0 ? pitch + rotateBy : pitch - rotateBy);
                    else if (dist90 < dist0 && dist90 <= dist180 && dist90 <= dist270)
                        entity.setPitch(pitch - 90 < 0 ? pitch + rotateBy : pitch - rotateBy);
                    else if (dist180 < dist90 && dist180 < dist0 && dist180 <= dist270)
                        entity.setPitch(pitch - 180 < 0 ? pitch + rotateBy : pitch - rotateBy);
                    else if (dist270 < dist90 && dist270 < dist180 && dist270 < dist0)
                        entity.setPitch(pitch - 270 < 0 ? pitch + rotateBy : pitch - rotateBy);
                }
            }
        } else if (!Double.isNaN(entity.getX()) && !Double.isNaN(entity.getY()) && !Double.isNaN(entity.getZ()) && entity.getWorld() != null) {
            if (entity.isOnGround()) {
                if (!isBlock) entity.setPitch(0);
            } else {
                rotateBy *= 2;
                Fluid fluid = calculateFluid(entity, false);
                if (fluid != null) rotateBy /= 2.0f;
                entity.setPitch(entity.getPitch() + rotateBy);
            }
        }
    }

    public static boolean render(ItemEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Random random, ItemEntity entity) {
        if (ItemPhysics.INSTANCE == null || !ItemPhysics.INSTANCE.isEnabled()) return false;
        
        random.setSeed(entity.getId());
        
        matrices.push();
        
        // Use random from state or entity (passed random is fine)
        
        IItemEntityRenderStateExtender extender = (IItemEntityRenderStateExtender) state;
        boolean isBlock = extender.isBlock();
        ItemStack stack = extender.getStack();
        int count = ItemPhysics.INSTANCE.fastRender.get() ? 1 : getModelCount(stack.getCount());
        
        if (stack.isEmpty()) {
            matrices.pop();
            return false;
        }

        BakedModel model = null;
        Vector3f scale = new Vector3f(0.25f, 0.25f, 0.25f);
        
        matrices.multiply(RotationAxis.POSITIVE_X.rotation((float) Math.PI / 2));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation(extender.getYRot()));
        
        if (isBlock || mc.options != null) {
             if (isBlock) {
                 matrices.translate(0, -0.2, -0.08);
             } else if (extender.hasAdditionalOffset()) {
                 matrices.translate(0, 0.0, -0.14);
             } else {
                 matrices.translate(0, 0, -0.04);
             }
             
             float height = scale.y;
             
             if (isBlock) matrices.translate(0, height, 0);
             matrices.multiply(RotationAxis.POSITIVE_Y.rotation(extender.getXRot()));
             if (isBlock) matrices.translate(0, -height, 0);
        }
        
        if (!isBlock) {
            float f7 = -0.0F * (count - 1) * 0.5F;
            float f8 = -0.0F * (count - 1) * 0.5F;
            float f9 = -0.09375F * (count - 1) * 0.5F;
            matrices.translate(f7, f8, f9);
        }
        
        float sx = scale.x;
        float sy = scale.y;
        float sz = scale.z;
        
        for (int k = 0; k < count; ++k) {
            matrices.push();
            if (k > 0) {
                if (isBlock) {
                    float f11 = (random.nextFloat() * 2.0F - 1.0F) * sx;
                    float f13 = (random.nextFloat() * 2.0F - 1.0F) * sy;
                    float f10 = (random.nextFloat() * 2.0F - 1.0F) * sz;
                    matrices.translate(f11, f13, f10);
                }
            }
            
            mc.getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
            
            matrices.pop();
            if (!isBlock) {
                matrices.translate(0.0F * sx, 0.0F * sy, 0.09375F * sz);
            }
        }
        
        matrices.pop();
        return true;
    }

    public static int getModelCount(int count) {
        if (count > 48) return 5;
        if (count > 32) return 4;
        if (count > 16) return 3;
        if (count > 1) return 2;
        return 1;
    }

    private static Fluid calculateFluid(ItemEntity item, boolean below) {
        if (item.getWorld() == null) return null;
        
        double y = item.getPos().y;
        BlockPos pos = item.getBlockPos();
        if (below) pos = pos.down();
        
        FluidState state = item.getWorld().getFluidState(pos);
        Fluid fluid = state.getFluid();
        if (fluid == null || state.isEmpty()) return null;
        
        if (below) return fluid;
        
        float filled = state.getHeight(item.getWorld(), pos);
        if (y - pos.getY() - 0.2 <= filled) return fluid;
        
        return null;
    }
}
