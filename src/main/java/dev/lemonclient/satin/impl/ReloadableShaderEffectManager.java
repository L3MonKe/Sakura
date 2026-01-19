package dev.lemonclient.satin.impl;

import dev.lemonclient.client.LemonClient;
import dev.lemonclient.client.shaders.WindowResizeCallback;
import dev.lemonclient.satin.api.ManagedCoreShader;
import dev.lemonclient.satin.api.ShaderEffectManager;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.function.Consumer;

public final class ReloadableShaderEffectManager implements ShaderEffectManager {
    public static final ReloadableShaderEffectManager INSTANCE = new ReloadableShaderEffectManager();

    public ReloadableShaderEffectManager() {
        WindowResizeCallback.EVENT.register((client, window) -> {
            onResolutionChanged(window.getFramebufferWidth(), window.getFramebufferHeight());
        });
    }

    private final Set<ResettableManagedShaderBase<?>> managedShaders = new ReferenceOpenHashSet<>();

    @Override
    public ManagedCoreShader manageCoreShader(Identifier location) {
        return manageCoreShader(location, VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);
    }

    @Override
    public ManagedCoreShader manageCoreShader(Identifier location, VertexFormat vertexFormat) {
        return manageCoreShader(location, vertexFormat, (s) -> {
        });
    }

    @Override
    public ManagedCoreShader manageCoreShader(Identifier location, VertexFormat vertexFormat, Consumer<ManagedCoreShader> initCallback) {
        ResettableManagedCoreShader ret = new ResettableManagedCoreShader(location, vertexFormat, initCallback);
        managedShaders.add(ret);
        return ret;
    }

    public void reload(ResourceFactory shaderResources) {
        for (ResettableManagedShaderBase<?> ss : managedShaders) {
            try {
                ss.initializeOrLog(shaderResources);
            } catch (Exception e) {
                LemonClient.LOGGER.error("Failed to reload shader: {}", ss.getLocation(), e);
            }
        }
    }

    public void onResolutionChanged(int newWidth, int newHeight) {
        runShaderSetup(newWidth, newHeight);
    }

    private void runShaderSetup(int newWidth, int newHeight) {
        if (!managedShaders.isEmpty()) {
            for (ResettableManagedShaderBase<?> ss : managedShaders) {
                if (ss.isInitialized()) {
                    ss.setup(newWidth, newHeight);
                }
            }
        }
    }
}
