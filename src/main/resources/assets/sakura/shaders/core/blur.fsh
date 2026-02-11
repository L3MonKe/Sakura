#version 150

uniform sampler2D InputSampler;
uniform sampler2D MaskSampler;
layout(std140) uniform BlurUniforms {
    vec4 Params1;
    vec4 Params2;
    vec4 Color1;
    vec4 Params3;
    vec4 Params4;
    vec4 SegRects[64];
    vec4 SegExtra[64];
};

out vec4 fragColor;

float roundedBoxSDF(vec2 center, vec2 size, float radius) {
    return length(max(abs(center) - size + radius, 0.0)) - radius;
}

vec4 blur() {
    #define TAU 6.28318530718

    vec2 inputResolution = Params1.xy;
    float Quality = Params1.z;
    vec2 Radius = Quality / inputResolution.xy;

    vec2 uv = gl_FragCoord.xy / inputResolution.xy;
    vec4 Color = texture(InputSampler, uv);

    float step =  TAU / 16.0;

    for (float d = 0.0; d < TAU; d += step) {
        for (float i = 0.2; i <= 1.0; i += 0.2) {
            vec2 uv2 = uv + vec2(cos(d), sin(d)) * Radius * i;
            Color += texture(InputSampler, uv2);
        }
    }

    Color /= 81.0;
    return (Color + Color1);
}

vec4 blurMasked() {
    #define TAU 6.28318530718

    vec2 inputResolution = Params1.xy;
    float Quality = Params1.z;
    vec2 Radius = Quality / inputResolution.xy;

    vec2 uv = gl_FragCoord.xy / inputResolution.xy;
    float w0 = texture(MaskSampler, uv).r;
    vec4 Color = texture(InputSampler, uv) * w0;
    float total = w0;

    float step =  TAU / 16.0;

    for (float d = 0.0; d < TAU; d += step) {
        for (float i = 0.2; i <= 1.0; i += 0.2) {
            vec2 uv2 = uv + vec2(cos(d), sin(d)) * Radius * i;
            float w = texture(MaskSampler, uv2).r;
            Color += texture(InputSampler, uv2) * w;
            total += w;
        }
    }

    Color /= max(1.0, total);
    return (Color + Color1);
}

void main() {
    vec2 uSize = Params2.xy;
    vec2 uLocation = Params2.zw;
    float radius = Params3.x;
    float Brightness = Params1.w;

    vec2 halfSize = uSize / 2.0;
    float smoothedAlpha = (1.0 - smoothstep(0.0, 1.0, roundedBoxSDF(gl_FragCoord.xy - uLocation - halfSize, halfSize, radius)));

    int count = int(Params4.x);
    if (count > 0) {
        vec2 uv = gl_FragCoord.xy / Params1.xy;
        float maskAlpha = texture(MaskSampler, uv).r;
        fragColor = vec4(blurMasked().rgb, maskAlpha * Brightness);
    } else {
        fragColor = vec4(blur().rgb, smoothedAlpha * Brightness);
    }
}
