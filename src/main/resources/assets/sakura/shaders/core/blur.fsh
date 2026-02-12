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

vec4 blurWeighted(vec2 uv, vec2 radius) {
    #define TAU 6.28318530718

    float w0 = texture(MaskSampler, uv).r;
    vec4 c = texture(InputSampler, uv) * w0;
    float total = w0;

    float step = TAU / 16.0;
    for (float d = 0.0; d < TAU; d += step) {
        vec2 dir = vec2(cos(d), sin(d));
        for (float i = 0.2; i <= 1.0; i += 0.2) {
            vec2 uv2 = uv + dir * radius * i;
            float w = texture(MaskSampler, uv2).r;
            c += texture(InputSampler, uv2) * w;
            total += w;
        }
    }

    c /= max(1e-5, total);
    return (c + Color1);
}

void main() {
    float Brightness = Params1.w;
    vec2 inputResolution = Params1.xy;
    float Quality = Params1.z;
    vec2 radius = Quality / inputResolution.xy;

    vec2 uv = gl_FragCoord.xy / inputResolution.xy;
    float maskAlpha = texture(MaskSampler, uv).r;
    vec3 blurred = blurWeighted(uv, radius).rgb;
    fragColor = vec4(blurred, maskAlpha * Brightness);
}
