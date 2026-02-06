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

float random(vec2 pos) {
    return fract(sin(dot(pos.xy, vec2(12.9898, 78.233))) * 43758.5453123);
}

float noise(vec2 pos) {
    vec2 i = floor(pos);
    vec2 f = fract(pos);
    float a = random(i + vec2(0.0, 0.0));
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));
    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
}

float fbm(vec2 pos, int oct) {
    float v = 0.0;
    float a = 0.5;
    for (int i = 0; i < oct; i++) {
        v += a * noise(pos);
        a *= 0.5;
    }
    return v;
}

vec3 getColor(vec4 centerCol, vec2 resolution, float time, float factor, float moreGradient, int oct) {
    vec2 p = (((vec2(2.0, 2.0) * gl_FragCoord.xy) - resolution.xy) * vec2((moreGradient / min(resolution.x, resolution.y)), (moreGradient / min(resolution.x, resolution.y))));
    float t = 0.0;
    float time2 = 3.0 * time / 2.0;
    vec2 q = vec2(0.0);
    q.x = fbm(p + 0.00, oct);
    q.y = fbm(p + vec2(1.0), oct);
    vec4 temp = vec4(vec3(noise(p + vec2(1.0)), noise(p + factor * q + vec2(1.7, 9.2) + 0.15 * time2), noise(p + factor * q + vec2(8.3, 2.8) + 0.126 * time2)), Params1.z);
    return vec3(temp[0], temp[1], temp[2]);
}

void main() {
    int quality = int(Params2.w);
    int lineWidth = int(Params3.x);
    int oct = int(Params3.y);

    float alpha0 = Params1.x;
    float alpha1 = Params1.y;
    float alpha2 = Params1.z;
    float time = Params2.x;
    float factor = Params2.y;
    float moreGradient = Params2.z;

    vec2 resolution = Resolution.xy;

    vec4 centerCol = texture(DiffuseSampler, texCoord);

    if (centerCol.a != 0.0) {
        fragColor = vec4(getColor(centerCol, resolution, time, factor, moreGradient, oct), alpha2);
        return;
    }

    float alphaOutline = 0.0;
    vec3 colorFinal = vec3(-1.0);

    for (int x = -quality; x < quality; x++) {
        for (int y = -quality; y < quality; y++) {
            vec2 offset = vec2(x, y);
            vec4 t = texture(DiffuseSampler, texCoord + offset * oneTexel);
            if (t.a == 0.0) continue;

            if (alpha0 == -1.0) {
                alphaOutline += alpha1 * 255.0 > 0.0 ? max(0.0, (lineWidth - distance(vec2(x, y), vec2(0.0))) / (alpha1 * 255.0)) : 1.0;
            } else {
                fragColor = vec4(getColor(centerCol, resolution, time, factor, moreGradient, oct), alpha0);
                return;
            }
        }
    }

    if (alphaOutline > 0.0) {
        colorFinal = getColor(centerCol, resolution, time, factor, moreGradient, oct);
    }

    fragColor = vec4(colorFinal, alphaOutline);
}
