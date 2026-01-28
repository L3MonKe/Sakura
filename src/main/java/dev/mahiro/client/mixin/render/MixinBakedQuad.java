package dev.mahiro.client.mixin.render;

import dev.mahiro.client.interfaces.IBakedQuad;
import net.minecraft.client.render.model.BakedQuad;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BakedQuad.class)
public abstract class MixinBakedQuad implements IBakedQuad {
    @Shadow @Final protected int[] vertexData;

    @Override
    public float getX(int vertexI) {
        return Float.intBitsToFloat(vertexData[vertexI * 8]);
    }

    @Override
    public float getY(int vertexI) {
        return Float.intBitsToFloat(vertexData[vertexI * 8 + 1]);
    }

    @Override
    public float getZ(int vertexI) {
        return Float.intBitsToFloat(vertexData[vertexI * 8 + 2]);
    }
}
