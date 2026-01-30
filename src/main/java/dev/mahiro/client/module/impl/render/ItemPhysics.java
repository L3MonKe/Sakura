package dev.mahiro.client.module.impl.render;

import dev.mahiro.client.events.render.item.ApplyTransformationEvent;
import dev.mahiro.client.events.render.item.RenderItemEntityEvent;
import dev.mahiro.client.interfaces.IBakedQuad;
import dev.mahiro.client.module.Category;
import dev.mahiro.client.module.Module;
import dev.mahiro.client.values.impl.BoolValue;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

public class ItemPhysics extends Module {
    private static final Direction[] FACES = {null, Direction.UP, Direction.DOWN, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.WEST};
    private static final float PIXEL_SIZE = 1f / 16f;
    private final Random random = Random.createLocal();
    private boolean renderingItem;

    private final BoolValue randomRotation = new BoolValue("Random Rotation", "随机旋转", true);

    public ItemPhysics() {
        super("ItemPhysics", "物品物理", Category.Render);
    }

    @EventHandler
    private void onRenderItemEntity(RenderItemEntityEvent event) {
        MatrixStack matrices = event.matrixStack;
        matrices.push();

        ItemStack itemStack = event.itemEntity.getStack();
        BakedModel model = event.renderState.itemRenderState.new LayerRenderState().model;
        if (model == null) return;
        ModelInfo info = getInfo(model);

        random.setSeed(itemStack.isEmpty() ? 187 : Item.getRawId(itemStack.getItem()) + itemStack.getDamage());

        applyTransformation(matrices, model);
        matrices.translate(0, info.offsetY, 0);
        offsetInWater(matrices, event.itemEntity);
        preventZFighting(matrices, event.itemEntity);

        if (info.flat) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            matrices.translate(0, 0, info.offsetZ);
        }

        if (randomRotation.get()) {
            RotationAxis axis = RotationAxis.POSITIVE_Y;
            if (info.flat) axis = RotationAxis.POSITIVE_Z;

            float degrees = (random.nextFloat() * 2 - 1) * 90;
            matrices.multiply(axis.rotationDegrees(degrees));
        }

        renderItem(event, matrices, itemStack, model, info);

        matrices.pop();
        event.cancel();
    }

    @EventHandler
    private void onApplyTransformation(ApplyTransformationEvent event) {
        if (renderingItem) event.cancel();
    }

    private void renderItem(RenderItemEntityEvent event, MatrixStack matrices, ItemStack itemStack, BakedModel model, ModelInfo info) {
        renderingItem = true;
        int count = getRenderedCount(itemStack);

        for (int i = 0; i < count; i++) {
            matrices.push();

            if (i > 0) {
                float x = (random.nextFloat() * 2 - 1) * 0.25f;
                float z = (random.nextFloat() * 2 - 1) * 0.25f;
                translate(matrices, info, x, 0, z);
            }

            //todo Mojang你妈死了。。  java.lang.IllegalStateException: Pose stack not empty
            //event.itemRenderer.renderItem(event.itemEntity.getControllingPassenger(), itemStack, ModelTransformationMode.GROUND, false, matrices, event.vertexConsumerProvider, event.itemEntity.getWorld(), event.light, OverlayTexture.DEFAULT_UV, random.);

            event.renderState.itemRenderState.render(matrices, event.vertexConsumerProvider, event.light, OverlayTexture.DEFAULT_UV);
            //event.itemRenderer.itemRenderState.render(matrices, event.vertexConsumerProvider, event.light, OverlayTexture.DEFAULT_UV);

            matrices.pop();

            float y = Math.max(random.nextFloat() * PIXEL_SIZE, PIXEL_SIZE / 2f);
            translate(matrices, info, 0, y, 0);
        }

        renderingItem = false;
    }

    private void translate(MatrixStack matrices, ModelInfo info, float x, float y, float z) {
        if (info.flat) {
            float temp = y;
            y = z;
            z = -temp;
        }

        matrices.translate(x, y, z);
    }

    private int getRenderedCount(ItemStack stack) {
        int i = 1;

        if (stack.getCount() > 48) i = 5;
        else if (stack.getCount() > 32) i = 4;
        else if (stack.getCount() > 16) i = 3;
        else if (stack.getCount() > 1) i = 2;

        return i;
    }

    private void applyTransformation(MatrixStack matrices, BakedModel model) {
        Transformation transformation = model.getTransformation().ground();

        float prevY = transformation.translation.y;
        transformation.translation.y = 0;

        transformation.apply(false, matrices);

        transformation.translation.y = prevY;
    }

    private void offsetInWater(MatrixStack matrices, ItemEntity entity) {
        if (entity.isTouchingWater()) {
            matrices.translate(0, 0.333f, 0);
        }
    }

    private void preventZFighting(MatrixStack matrices, ItemEntity entity) {
        float offset = 0.0001f;

        float distance = (float) mc.gameRenderer.getCamera().getPos().distanceTo(entity.getPos());
        offset = Math.min(offset * Math.max(1, distance), 0.01f); // Ensure distance is at least 1 and that final offset is not bigger than 0.01

        matrices.translate(0, offset, 0);
    }

    private ModelInfo getInfo(BakedModel model) {
        Random random = Random.createLocal();

        float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;
        float minZ = Float.MAX_VALUE, maxZ = Float.MIN_VALUE;

        for (Direction face : FACES) {
            for (BakedQuad _quad : model.getQuads(null, face, random)) {
                IBakedQuad quad = (IBakedQuad) _quad;

                for (int i = 0; i < 4; i++) {
                    switch (_quad.getFace()) {
                        case DOWN -> minY = Math.min(minY, quad.getY(i));
                        case UP -> maxY = Math.max(maxY, quad.getY(i));
                        case NORTH -> minZ = Math.min(minZ, quad.getZ(i));
                        case SOUTH -> maxZ = Math.max(maxZ, quad.getZ(i));
                        case WEST -> minX = Math.min(minX, quad.getX(i));
                        case EAST -> maxX = Math.max(maxX, quad.getX(i));
                    }
                }
            }
        }

        if (minX == Float.MAX_VALUE) minX = 0;
        if (minY == Float.MAX_VALUE) minY = 0;
        if (minZ == Float.MAX_VALUE) minZ = 0;

        if (maxX == Float.MIN_VALUE) maxX = 1;
        if (maxY == Float.MIN_VALUE) maxY = 1;
        if (maxZ == Float.MIN_VALUE) maxZ = 1;

        float x = maxX - minX;
        float y = maxY - minY;
        float z = maxZ - minZ;

        boolean flat = (x > PIXEL_SIZE && y > PIXEL_SIZE && z <= PIXEL_SIZE);

        return new ModelInfo(flat, 0.5f - minY, minZ - minY);
    }

    record ModelInfo(boolean flat, float offsetY, float offsetZ) {
    }
}