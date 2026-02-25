package dev.mzc.satin.api.uniform;

import org.joml.Vector4f;

public interface Uniform4f {
    void set(float value0, float value1, float value2, float value3);

    void set(Vector4f value);
}
