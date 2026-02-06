package dev.sakura.client.nanovg.util.state;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public class States {
    public static final States INSTANCE = new States();

    private final State state;

    private States() {
        int glVersion = getGLVersion();
        state = new State(glVersion);
    }

    public void push() {
        state.push();
    }

    public void pop() {
        state.pop();
    }

    private int getGLVersion() {
        GLCapabilities caps = GL.getCapabilities();
        if (caps.OpenGL33) return 330;
        if (caps.OpenGL32) return 320;
        if (caps.OpenGL31) return 310;
        if (caps.OpenGL30) return 300;
        if (caps.OpenGL21) return 210;
        if (caps.OpenGL20) return 200;
        return 110;
    }
}
