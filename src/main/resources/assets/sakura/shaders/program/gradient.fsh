#version 150

uniform sampler2D InSampler;
uniform sampler2D MaskSampler;
in vec2 texCoord;
in vec2 oneTexel;
out vec4 fragColor;
uniform int quality;
uniform int lineWidth;
uniform vec2 InSize;

uniform float alpha0;
uniform float alpha1;
uniform float alpha2;

uniform vec2 resolution;
uniform float time;
uniform int oct;


uniform float factor;
uniform float moreGradient;


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

float fbm(vec2 pos) {
    float v = 0.0;
    float a = 0.5;
    mat2 rot = mat2(cos(0.1), sin(0.5), -sin(0.5), cos(0.5));
    for (int i=0; i < oct; i++) {
        v += a * noise(pos);
        a *= 0.5;
    }
    return v;
}

vec3 getColor(vec4 centerCol) {
    float minrz = min(resolution.x, resolution.y);
    vec2 p = (((vec2(2.0, 2.0) * gl_FragCoord.xy) - resolution.xy) * vec2((moreGradient / min(resolution.x, resolution.y)), (moreGradient / min(resolution.x, resolution.y))));

    float t = 0.0;

    float time2 = 3.0 * time / 2.0;

    vec2 q = vec2(0.0);
    q.x = fbm(p + 0.00);
    q.y = fbm(p + vec2(1.0));

    vec4 temp = vec4(vec3(noise(p + vec2(1.0)), noise(p + factor * q + vec2(1.7, 9.2) + 0.15 * time2), noise(p + factor * q + vec2(8.3, 2.8) + 0.126 * time2)), alpha2);
    return vec3(temp[0], temp[1], temp[2]);
}

void main() {
    vec4 sceneCol = texture(InSampler, texCoord);
    vec4 centerCol = texture(MaskSampler, texCoord);

    if (centerCol.a != 0) {
        vec3 fillRgb = getColor(centerCol);
        vec3 outRgb = mix(sceneCol.rgb, fillRgb, clamp(alpha2, 0.0, 1.0));
        fragColor = vec4(outRgb, 1.0);
        return;
    }

    float alphaOutline = 0.0;
    bool found = false;
    for (int x = -quality; x < quality; x++) {
        for (int y = -quality; y < quality; y++) {
            vec2 offset = vec2(x, y);
            vec2 coord = texCoord + offset * oneTexel;
            vec4 t = texture(MaskSampler, coord);

            if (t.a != 0.0) {
                found = true;
                if (alpha0 == -1.0) {
                    alphaOutline += alpha1 * 255.0 > 0 ? max(0.0, (lineWidth - distance(vec2(x, y), vec2(0))) / (alpha1 * 255.0)) : 1.0;
                } else {
                    alphaOutline = alpha0;
                    x = quality;
                    break;
                }
            }
        }
    }

    if (!found || alphaOutline <= 0.0) {
        fragColor = vec4(sceneCol.rgb, 1.0);
        return;
    }

    vec3 outlineRgb = getColor(centerCol);
    vec3 outRgb = mix(sceneCol.rgb, outlineRgb, clamp(alphaOutline, 0.0, 1.0));
    fragColor = vec4(outRgb, 1.0);
}
