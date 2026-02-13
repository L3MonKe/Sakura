package dev.sakura.client.shaders;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.utils.animations.AnimationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.MappableRingBuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class MainMenuShader {
    private static MainMenuShader sharedInstance;

    private static final int UNIFORMS_SIZE = new Std140SizeCalculator().putVec4().putVec4().get();

    public static MainMenuShader getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new MainMenuShader(MainMenuShaderType.SAKURA);
        }
        return sharedInstance;
    }

    public static void cleanupSharedInstance() {
        if (sharedInstance != null) {
            sharedInstance.cleanup();
            sharedInstance = null;
        }
    }

    private final EnumMap<MainMenuShaderType, RenderPipeline> pipelines = new EnumMap<>(MainMenuShaderType.class);
    private MainMenuShaderType currentShaderType;
    private MappableRingBuffer uniforms;
    private float timeSeconds;
    private float transitionValue = 1.0f;
    private float mouseX;
    private float mouseY;

    public MainMenuShader(MainMenuShaderType shaderType) {
        this.currentShaderType = shaderType;
    }

    public void render(int width, int height) {
        render(width, height, this.transitionValue);
    }

    public void render(int width, int height, float transition) {
        RenderPipeline pipeline = this.getPipeline(this.currentShaderType);
        if (pipeline == null) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        Framebuffer framebuffer = client.getFramebuffer();
        float scaleFactor = (float) client.getWindow().getScaleFactor();
        float pxWidth = width * scaleFactor;
        float pxHeight = height * scaleFactor;

        this.timeSeconds += AnimationUtil.deltaTime();

        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        try (GpuBuffer.MappedView view = encoder.mapBuffer(this.uniforms.getBlocking(), false, true)) {
            Std140Builder builder = Std140Builder.intoBuffer(view.data());
            builder.putVec2(pxWidth, pxHeight);
            builder.putFloat(this.timeSeconds);
            builder.putFloat(transition);
            builder.putVec2(this.mouseX, this.mouseY);
            builder.putVec2(pxWidth, pxHeight);
        }

        try (RenderPass renderPass = encoder.createRenderPass(
                () -> "Sakura MainMenu",
                framebuffer.getColorAttachmentView(),
                OptionalInt.empty(),
                framebuffer.useDepthAttachment ? framebuffer.getDepthAttachmentView() : null,
                OptionalDouble.empty()
        )) {
            renderPass.setPipeline(pipeline);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("MenuUniforms", this.uniforms.getBlocking());
            renderPass.draw(0, 3);
        }
        this.uniforms.rotate();
    }

    public void setTransition(float transition) {
        this.transitionValue = transition;
    }

    public float getTransition() {
        return this.transitionValue;
    }

    public void setMouse(float x, float y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    public void switchShaderType(MainMenuShaderType newType) {
        if (this.currentShaderType == newType) {
            return;
        }
        this.currentShaderType = newType;
        this.timeSeconds = 0.0f;
    }

    public MainMenuShaderType getCurrentShaderType() {
        return currentShaderType;
    }

    public void nextShader() {
        MainMenuShaderType next = currentShaderType.next();
        switchShaderType(next);
    }

    public void previousShader() {
        MainMenuShaderType prev = currentShaderType.previous();
        switchShaderType(prev);
    }

    public void cleanup() {
        if (this.uniforms != null) {
            this.uniforms.close();
            this.uniforms = null;
        }
    }

    private RenderPipeline getPipeline(MainMenuShaderType type) {
        if (this.uniforms == null) {
            this.uniforms = new MappableRingBuffer(() -> "Sakura MenuUniforms", GpuBuffer.USAGE_MAP_WRITE | GpuBuffer.USAGE_UNIFORM, UNIFORMS_SIZE);
        }
        return this.pipelines.computeIfAbsent(type, t -> RenderPipeline.builder(RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET)
                .withLocation(Identifier.of("sakura", "pipeline/menu/" + t.name().toLowerCase()))
                .withVertexShader(Identifier.of("sakura", "core/screen_triangle"))
                .withFragmentShader(t.fragmentShader)
                .withUniform("MenuUniforms", UniformType.UNIFORM_BUFFER)
                .withCull(false)
                .build());
    }

    public enum MainMenuShaderType {
        SAKURA(Identifier.of("sakura", "core/menu_sakura"), "樱花效果"),
        CUTE(Identifier.of("sakura", "core/menu_cute"), "可爱效果");

        private final Identifier fragmentShader;
        private final String displayName;

        MainMenuShaderType(Identifier fragmentShader, String displayName) {
            this.fragmentShader = fragmentShader;
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public MainMenuShaderType next() {
            MainMenuShaderType[] values = values();
            return values[(this.ordinal() + 1) % values.length];
        }

        public MainMenuShaderType previous() {
            MainMenuShaderType[] values = values();
            return values[(this.ordinal() - 1 + values.length) % values.length];
        }

        public static MainMenuShaderType fromName(String name) {
            for (MainMenuShaderType type : values()) {
                if (type.name().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return null;
        }
    }
}
