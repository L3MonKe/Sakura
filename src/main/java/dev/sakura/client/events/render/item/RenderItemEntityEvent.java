package dev.sakura.client.events.render.item;

import dev.sakura.client.events.Cancellable;
import dev.sakura.client.interfaces.IEntityRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;

public class RenderItemEntityEvent extends Cancellable {
    public ItemEntity itemEntity;
    public ItemEntityRenderState renderState;
    public float tickDelta;
    public MatrixStack matrixStack;
    public VertexConsumerProvider vertexConsumerProvider;
    public int light;
    public ItemModelManager itemModelManager;
    public OrderedRenderCommandQueue renderCommandQueue;

    public RenderItemEntityEvent(ItemEntityRenderState renderState, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, ItemModelManager itemModelManager, OrderedRenderCommandQueue renderCommandQueue) {
        this.itemEntity = (ItemEntity) ((IEntityRenderState) renderState).getEntity();
        this.renderState = renderState;
        this.tickDelta = tickDelta;
        this.matrixStack = matrixStack;
        this.vertexConsumerProvider = vertexConsumerProvider;
        this.light = light;
        this.itemModelManager = itemModelManager;
        this.renderCommandQueue = renderCommandQueue;
    }
}
