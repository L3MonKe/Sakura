package dev.mzc.satin.api.uniform;

import org.joml.Matrix4f;

public interface UniformMat4 {
    void set(Matrix4f value);

    void setFromArray(float[] values);
}
