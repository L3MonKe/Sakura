#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

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
    float radius = Params3.x;
    vec2 direction = Params3.zw;
    vec3 color = OutlineColor.rgb;
    float exposure = Params2.y;
    float isGradient = Params1.w;
    float gradientSpeed = Params2.z;

    vec4 centerCol = texture(DiffuseSampler, texCoord);

    if (centerCol.a != 0) {
        fragColor = centerCol;
    }

    vec4 sum = vec4(0.0);
    float totalWeight = 0.0;
    float sigma = radius / 2.0;

    float centerWeight = 1.0 / (2.50662827463 * sigma);
    sum += texture(DiffuseSampler, texCoord) * centerWeight;
    totalWeight += centerWeight;

    for (float i = 1.0; i <= radius; i += 1.0) {
        float weight = (1.0 / (2.50662827463 * sigma)) * exp(-(i * i) / (2.0 * sigma * sigma));

        vec2 offset = direction * i * oneTexel;
        sum += texture(DiffuseSampler, texCoord + offset) * weight;
        sum += texture(DiffuseSampler, texCoord - offset) * weight;
        totalWeight += weight * 2.0;
    }

    vec4 blurred = sum / totalWeight;

    if (blurred.a > 0.0) {
        vec3 finalColor = color;
        if (isGradient > 0.5) {
            float t = sin((1.0 - gl_FragCoord.y / Resolution.y) * 6.28318530 + Params2.x * gradientSpeed * 0.5) * 0.5 + 0.5;
            finalColor = mix(OutlineColor1.rgb, OutlineColor2.rgb, t);
        }
        fragColor = vec4(finalColor, blurred.a * exposure);
    } else {
        fragColor = vec4(0.0);
    }
}
