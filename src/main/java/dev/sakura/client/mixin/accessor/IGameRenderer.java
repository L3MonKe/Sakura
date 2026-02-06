package dev.sakura.client.mixin.accessor;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Pool;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface IGameRenderer {
    @Accessor("pool")
    Pool getPool();

    @Invoker("renderHand")
    void sakura$renderHand(float tickProgress, boolean sleeping, Matrix4f positionMatrix);
}
