#version 330

uniform sampler2D InSampler;
uniform sampler2D MaskSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform float width;
uniform vec2 direction;

out vec4 fragColor;

void main() {
    float radiusMul = texture(MaskSampler, texCoord).a;

    if (radiusMul < 0.001) {
        fragColor = texture(InSampler, texCoord);
        return;
    }

    float fullRadius = width * radiusMul;
    vec4 originalColor = texture(InSampler, texCoord);

    if (fullRadius < 0.5) {
        fragColor = originalColor;
        return;
    }

    vec2 offsetStep = oneTexel * direction;
    float sigma = fullRadius * 0.5;
    float invTwoSigmaSq = 1.0 / (2.0 * sigma * sigma + 0.001);

    vec4 blurred = originalColor;
    float weightSum = 1.0;

    int intRadius = int(ceil(fullRadius));

    for (int j = 1; j <= intRadius; ++j) {
        float x = float(j);
        float weight = exp(-x * x * invTwoSigmaSq);

        if (weight < 0.001) break;

        vec2 offset = offsetStep * x;
        blurred += texture(InSampler, texCoord + offset) * weight;
        blurred += texture(InSampler, texCoord - offset) * weight;
        weightSum += 2.0 * weight;
    }

    fragColor = blurred / weightSum;
}