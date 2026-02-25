package dev.sakura.client.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sakura.client.utils.animations.AnimationUtil;
import net.minecraft.client.gl.GlUsage;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

import java.io.IOException;

import static dev.sakura.client.Sakura.mc;

public class MainMenuShader {
    // 共享的SAKURA着色器实例（用于 SplashOverlay和TitleScreen之间的过渡）
    private static MainMenuShader sharedInstance;

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

    private int programId;
    private int timeUniform;
    private int resolutionUniform;
    private int transitionUniform;
    private int mouseUniform;
    private float accumulatedTime;
    private float transitionValue = 1.0f; // 1.0 = 正常显示
    private float mouseOffsetX = 0f;
    private VertexBuffer vertexBuffer;
    private MainMenuShaderType currentShaderType;
    private boolean useAlternativeUniforms;

    public MainMenuShader(MainMenuShaderType shaderType) {
        this.accumulatedTime = 0f;
        this.currentShaderType = shaderType;

        try {
            this.programId = createShaderProgram(shaderType);
            this.setupVertexBuffer();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load main menu shader: " + shaderType.getDisplayName(), e);
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


    private int createShaderProgram(MainMenuShaderType shaderType) throws IOException {
        int program = GL20.glCreateProgram();

        int vertexShader = createShader(ShaderProgram.PASSTHROUGH, GL20.GL_VERTEX_SHADER);
        int fragmentShader = createShader(shaderType.getSource(), GL20.GL_FRAGMENT_SHADER);

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
        this.transitionUniform = GL20.glGetUniformLocation(program, "transition");
        this.mouseUniform = GL20.glGetUniformLocation(program, "mouse");
        this.useAlternativeUniforms = false;
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
        render(width, height, this.transitionValue);
    }

    public void render(int width, int height, float transition) {
        if (this.programId == 0 || this.vertexBuffer == null) return;

        RenderSystem.disableCull();
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();

        GL20.glUseProgram(this.programId);

        float scaleFactor = (float) mc.getWindow().getScaleFactor();
        GL20.glUniform2f(this.resolutionUniform, width * scaleFactor, height * scaleFactor);

        int uSizeUniform = GL20.glGetUniformLocation(this.programId, "uSize");
        if (uSizeUniform != -1) {
            GL20.glUniform2f(uSizeUniform, width * scaleFactor, height * scaleFactor);
        }

        int TimeUniform = GL20.glGetUniformLocation(this.programId, "Time");

        if (useAlternativeUniforms) {
            accumulatedTime += (float) (0.55 * AnimationUtil.deltaTime());
            GL20.glUniform1f(this.timeUniform, accumulatedTime);
        } else {
            accumulatedTime += (float) (1.0 * AnimationUtil.deltaTime());
            GL20.glUniform1f(this.timeUniform, accumulatedTime);
        }

        if (TimeUniform != -1) {
            GL20.glUniform1f(TimeUniform, accumulatedTime);
        }

        // 设置过渡参数
        if (this.transitionUniform >= 0) {
            GL20.glUniform1f(this.transitionUniform, transition);
        }

        if (this.mouseUniform >= 0) {
            GL20.glUniform2f(this.mouseUniform, mouseOffsetX, 0.5f);
        }

        this.vertexBuffer.bind();
        this.vertexBuffer.draw();
        VertexBuffer.unbind();

        GL20.glUseProgram(0);
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
    }

    public void setTransition(float transition) {
        this.transitionValue = transition;
    }

    public float getTransition() {
        return this.transitionValue;
    }

    public void setMouseOffset(float x) {
        this.mouseOffsetX = x;
    }

    public void switchShaderType(MainMenuShaderType newType) {
        if (this.currentShaderType == newType) {
            return;
        }

        // 清理旧的资源
        if (this.programId != 0) {
            GL20.glDeleteProgram(this.programId);
            this.programId = 0;
        }

        this.currentShaderType = newType;

        try {
            this.accumulatedTime = 0f;
            this.programId = createShaderProgram(newType);
            if (this.vertexBuffer == null) {
                this.setupVertexBuffer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (this.programId != 0) {
            GL20.glDeleteProgram(this.programId);
            this.programId = 0;
        }
        if (this.vertexBuffer != null) {
            this.vertexBuffer.close();
            this.vertexBuffer = null;
        }
    }

    public enum MainMenuShaderType {
        SAKURA(ShaderProgram.SAKURA, "樱花效果"),
        CUTE(ShaderProgram.CUTE, "可爱效果"),
        SEA(ShaderProgram.SEA, "海洋效果"),
        MOON(ShaderProgram.MOON, "月球飞行");

        private final String source;
        private final String displayName;

        MainMenuShaderType(String source, String displayName) {
            this.source = source;
            this.displayName = displayName;
        }

        public String getSource() {
            return source;
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
