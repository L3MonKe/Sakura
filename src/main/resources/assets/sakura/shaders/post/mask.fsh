#version 150

uniform sampler2D DiffuseSampler;
in vec2 texCoord;

layout(std140) uniform ShaderParams {
    vec4 InSize;
    vec4 Resolution;
    vec4 Color;
    vec4 OutlineColor;
    vec4 OutlineColor1;
    vec4 OutlineColor2;
    vec4 FillColor1;
    vec4 FillColor2;
    vec4 FillColor3;
    vec4 PrimaryColor;
    vec4 SecondaryColor;
    vec4 Params1;
    vec4 Params2;
    vec4 Params3;
};

out vec4 fragColor;

void main() {
    fragColor = texture(DiffuseSampler, texCoord);
}
