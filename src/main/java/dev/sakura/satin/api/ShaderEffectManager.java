package dev.sakura.satin.api;

import dev.sakura.satin.impl.ReloadableShaderEffectManager;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public interface ShaderEffectManager {
    static ShaderEffectManager getInstance() {
        return ReloadableShaderEffectManager.INSTANCE;
    }

    ManagedCoreShader manageCoreShader(Identifier location);

    ManagedCoreShader manageCoreShader(Identifier location, VertexFormat vertexFormat);

    ManagedCoreShader manageCoreShader(Identifier location, VertexFormat vertexFormat, Consumer<ManagedCoreShader> initCallback);
}
