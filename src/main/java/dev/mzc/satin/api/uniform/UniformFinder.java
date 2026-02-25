package dev.mzc.satin.api.uniform;

public interface UniformFinder {
    Uniform1i findUniform1i(String uniformName);

    Uniform2i findUniform2i(String uniformName);

    Uniform3i findUniform3i(String uniformName);

    Uniform4i findUniform4i(String uniformName);

    Uniform1f findUniform1f(String uniformName);

    Uniform2f findUniform2f(String uniformName);

    Uniform3f findUniform3f(String uniformName);

    Uniform4f findUniform4f(String uniformName);

    UniformMat4 findUniformMat4(String uniformName);

    SamplerUniform findSampler(String samplerName);

}
