package dev.sakura.client.shaders;

import dev.sakura.client.shaders.program.HealthBarProgram;

public class HealthBarShader {
    private static final HealthBarProgram PROGRAM = new HealthBarProgram();

    public static void render(float x, float y, float width, float height, float radius,
                              float hueMin, float hueMax, float satMin, float satMax,
                              float valMin, float valMax, float speed) {
        PROGRAM.render(x, y, width, height, radius, hueMin, hueMax, satMin, satMax, valMin, valMax, speed);
    }
}
