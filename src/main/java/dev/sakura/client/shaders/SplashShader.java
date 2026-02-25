package dev.sakura.client.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.GlUsage;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

import java.io.IOException;

import static dev.sakura.client.Sakura.mc;

public class SplashShader {
    private static SplashShader INSTANCE;

    private int programId;
    private int timeUniform;
    private int resolutionUniform;
    private int progressUniform;
    private int fadeOutUniform;
    private int zoomUniform;
    private float accumulatedTime;
    private float currentProgress = 0f;
    private VertexBuffer vertexBuffer;
    private boolean initialized = false;

    private boolean transitionStarted = false;
    private float accumulatedTransitionTime = 0f;
    private static final float TRANSITION_DURATION = 2.0f; // 2秒过渡

    private long lastFrameTime = System.nanoTime();

    public static SplashShader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SplashShader();
        }
        return INSTANCE;
    }

    private SplashShader() {
        this.accumulatedTime = 0f;
    }

    public void init() {
        if (initialized) return;
        try {
            this.programId = createShaderProgram();
            this.setupVertexBuffer();
            this.initialized = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupVertexBuffer() {
        this.vertexBuffer = new VertexBuffer(GlUsage.STATIC_WRITE);

        MatrixStack identityStack = new MatrixStack();
        Matrix4f matrix = identityStack.peek().getPositionMatrix();

        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        bufferBuilder.vertex(matrix, -1.0f, -1.0f, 0.0f);
        bufferBuilder.vertex(matrix, -1.0f, 1.0f, 0.0f);
        bufferBuilder.vertex(matrix, 1.0f, 1.0f, 0.0f);
        bufferBuilder.vertex(matrix, 1.0f, -1.0f, 0.0f);

        BuiltBuffer builtBuffer = bufferBuilder.end();
        this.vertexBuffer.bind();
        this.vertexBuffer.upload(builtBuffer);
        VertexBuffer.unbind();
    }


    private int createShaderProgram() throws IOException {
        int program = GL20.glCreateProgram();

        int vertexShader = createShader(ShaderProgram.PASSTHROUGH, GL20.GL_VERTEX_SHADER);
        int fragmentShader = createShader(ShaderProgram.SPLASH, GL20.GL_FRAGMENT_SHADER);

        GL20.glAttachShader(program, vertexShader);
        GL20.glAttachShader(program, fragmentShader);
        GL20.glLinkProgram(program);

        int linked = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);
        if (linked == 0) {
            throw new IllegalStateException("Shader failed to link");
        }

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        GL20.glUseProgram(program);
        this.timeUniform = GL20.glGetUniformLocation(program, "time");
        this.resolutionUniform = GL20.glGetUniformLocation(program, "resolution");
        this.progressUniform = GL20.glGetUniformLocation(program, "progress");
        this.fadeOutUniform = GL20.glGetUniformLocation(program, "fadeOut");
        this.zoomUniform = GL20.glGetUniformLocation(program, "zoom");
        GL20.glUseProgram(0);

        return program;
    }

    private int createShader(String source, int shaderType) {
        int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, source);
        GL20.glCompileShader(shader);

        int compiled = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
        if (compiled == 0) {
            String log = GL20.glGetShaderInfoLog(shader);
            throw new IllegalStateException("Failed to compile shader: " + log);
        }

        return shader;
    }

    public void render(int width, int height) {
        render(width, height, this.currentProgress, 0f, 1.0f);
    }

    public void render(int width, int height, float progress) {
        render(width, height, progress, 0f, 1.0f);
    }

    public void render(int width, int height, float progress, float fadeOut, float zoom) {
        if (!initialized) {
            init();
        }
        if (this.programId == 0 || this.vertexBuffer == null) return;

        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) / 1_000_000_000f;
        lastFrameTime = currentTime;

        if (transitionStarted && accumulatedTransitionTime < TRANSITION_DURATION) {
            accumulatedTransitionTime += deltaTime;
            if (accumulatedTransitionTime > TRANSITION_DURATION) {
                accumulatedTransitionTime = TRANSITION_DURATION;
            }
        }

        this.currentProgress = progress;

        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();

        if (zoom > 1.0f) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        } else {
            RenderSystem.disableBlend();
        }

        GL20.glUseProgram(this.programId);

        float scaleFactor = (float) mc.getWindow().getScaleFactor();
        GL20.glUniform2f(this.resolutionUniform, width * scaleFactor, height * scaleFactor);

        accumulatedTime += deltaTime;
        GL20.glUniform1f(this.timeUniform, accumulatedTime);
        GL20.glUniform1f(this.progressUniform, progress);
        GL20.glUniform1f(this.fadeOutUniform, fadeOut);
        GL20.glUniform1f(this.zoomUniform, zoom);

        this.vertexBuffer.bind();
        this.vertexBuffer.draw();
        VertexBuffer.unbind();

        GL20.glUseProgram(0);
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    public void startTransition() {
        this.transitionStarted = true;
        this.accumulatedTransitionTime = 0f;
    }

    public boolean isTransitionStarted() {
        return transitionStarted;
    }

    public float getTransitionProgress() {
        if (!transitionStarted) return 0f;
        return Math.min(1f, accumulatedTransitionTime / TRANSITION_DURATION);
    }

    public boolean isTransitionComplete() {
        return transitionStarted && accumulatedTransitionTime >= TRANSITION_DURATION;
    }

    public float getAccumulatedTime() {
        return accumulatedTime;
    }

    public void reset() {
        this.transitionStarted = false;
        this.accumulatedTime = 0f;
        this.accumulatedTransitionTime = 0f;
    }

    public void cleanup() {
        if (this.programId != 0) {
            GL20.glDeleteProgram(this.programId);
            this.programId = 0;
        }
        if (this.vertexBuffer != null) {
            this.vertexBuffer.close();
            this.vertexBuffer = null;
        }
        this.initialized = false;
        INSTANCE = null;
    }
}
