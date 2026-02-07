package dev.sakura.client.mixin.render;

import dev.sakura.client.Sakura;
import dev.sakura.client.module.impl.render.Rainy;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.state.WeatherRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticlesMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(WeatherRendering.class)
public abstract class MixinWeatherRendering {
    @Final
    @Shadow
    private float[] NORMAL_LINE_DX;

    @Final
    @Shadow
    private float[] NORMAL_LINE_DZ;

    @Shadow
    protected abstract WeatherRendering.Piece createSnowPiece(Random random, int ticks, int x, int yMin, int yMax, int z, int light, float tickProgress);

    @Unique
    private static final Identifier SAKURA_WEATHER_TEXTURE = Identifier.ofVanilla("textures/environment/snow.png");

    @Inject(method = "buildPrecipitationPieces", at = @At("HEAD"), cancellable = true)
    private void onBuildPrecipitationPieces(World world, int ticks, float tickProgress, Vec3d cameraPos, WeatherRenderState state, CallbackInfo ci) {
        if (Sakura.MODULES == null) return;
        Rainy rainy = Sakura.MODULES.getModule(Rainy.class);
        if (rainy == null || !rainy.isEnabled()) return;

        if (rainy.isSakura()) {
            state.clear();
            ci.cancel();
            return;
        }
        if (!rainy.isSnow()) {
            return;
        }

        state.rainPieces.clear();
        state.snowPieces.clear();

        state.intensity = 1.0f;
        state.radius = MinecraftClient.getInstance().options.getWeatherRadius().getValue();

        int i = MathHelper.floor(cameraPos.x);
        int j = MathHelper.floor(cameraPos.y);
        int k = MathHelper.floor(cameraPos.z);

        Random random = Random.create();
        for (int z = k - state.radius; z <= k + state.radius; ++z) {
            for (int x = i - state.radius; x <= i + state.radius; ++x) {
                int topY = world.getTopY(Heightmap.Type.MOTION_BLOCKING, x, z);
                int yMin = Math.max(j - state.radius, topY);
                int yMax = Math.max(j + state.radius, topY);
                if (yMax - yMin == 0) continue;

                int q = x * x * 3121 + x * 45238971 ^ z * z * 418711 + z * 13761;
                random.setSeed(q);
                int light = WorldRenderer.getLightmapCoordinates(world, new net.minecraft.util.math.BlockPos.Mutable(x, Math.max(j, topY), z));

                state.snowPieces.add(this.createSnowPiece(random, ticks, x, yMin, yMax, z, light, tickProgress));
            }
        }

        ci.cancel();
    }

    @Inject(method = "renderPrecipitation", at = @At("HEAD"), cancellable = true)
    private void onRenderPrecipitation(VertexConsumerProvider vertexConsumers, Vec3d pos, WeatherRenderState state, CallbackInfo ci) {
        Rainy rainy = Sakura.MODULES.getModule(Rainy.class);
        if (rainy == null || !rainy.isEnabled()) return;

        if (rainy.isSakura()) {
            ci.cancel();
            return;
        }
        if (!rainy.isSnow()) {
            return;
        }

        if (state.snowPieces.isEmpty()) {
            ci.cancel();
            return;
        }

        RenderLayer renderLayer = RenderLayers.weather(SAKURA_WEATHER_TEXTURE, MinecraftClient.usesImprovedTransparency());
        VertexConsumer buffer = vertexConsumers.getBuffer(renderLayer);

        float baseIntensity = 0.8f;
        rainy.renderSakura(buffer, state.snowPieces, pos, baseIntensity, state.radius, state.intensity, false, false, Color.WHITE, Color.WHITE, this.NORMAL_LINE_DX, this.NORMAL_LINE_DZ);

        ci.cancel();
    }

    @Inject(method = "addParticlesAndSound", at = @At("HEAD"), cancellable = true)
    private void onAddParticlesAndSound(ClientWorld world, Camera camera, int ticks, ParticlesMode particlesMode, int weatherRadius, CallbackInfo ci) {
        Rainy rainy = Sakura.MODULES.getModule(Rainy.class);
        if (rainy == null || !rainy.isEnabled() || !rainy.isSakura()) return;

        double density = rainy.getDensity();
        if (density <= 0.0) {
            ci.cancel();
            return;
        }

        int baseCount = (int) Math.floor(density);
        double fraction = density - baseCount;

        Random random = Random.create((long) ticks * 312987231L);
        if (random.nextDouble() < fraction) baseCount++;

        if (particlesMode == ParticlesMode.DECREASED) {
            baseCount = baseCount / 2;
        } else if (particlesMode == ParticlesMode.MINIMAL) {
            baseCount = Math.min(1, baseCount / 4);
        }
        if (baseCount <= 0) {
            ci.cancel();
            return;
        }

        Vec3d camPos = camera.getCameraPos();
        int radius = rainy.getRadius();
        int height = rainy.getHeight();
        boolean onlyOutside = rainy.isOnlyOutside();

        for (int i = 0; i < baseCount; i++) {
            int offX = random.nextInt(radius * 2 + 1) - radius;
            int offZ = random.nextInt(radius * 2 + 1) - radius;

            double x = camPos.x + offX + random.nextDouble();
            double z = camPos.z + offZ + random.nextDouble();
            double y = camPos.y + 1.0 + random.nextDouble() * height;

            if (onlyOutside) {
                if (!world.isSkyVisible(BlockPos.ofFloored(x, y, z))) continue;
            }

            world.addParticleClient(ParticleTypes.CHERRY_LEAVES, x, y, z, 0.0, 0.0, 0.0);
        }

        ci.cancel();
    }
}
