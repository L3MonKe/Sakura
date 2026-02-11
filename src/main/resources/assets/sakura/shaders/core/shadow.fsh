#version 150

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

float blurMask(vec2 uv) {
    #define TAU 6.28318530718

    vec2 inputResolution = Params1.xy;
    float Quality = Params1.z;
    vec2 Radius = Quality / inputResolution.xy;

    float v = texture(MaskSampler, uv).r;

    float step = TAU / 16.0;
    for (float d = 0.0; d < TAU; d += step) {
        for (float i = 0.2; i <= 1.0; i += 0.2) {
            vec2 uv2 = uv + vec2(cos(d), sin(d)) * Radius * i;
            v += texture(MaskSampler, uv2).r;
        }
    }

    return v / 81.0;
}

void main() {
    vec2 inputResolution = Params1.xy;
    float strength = Params1.w;
    float topY = Params2.x;
    float bottomY = Params2.y;
    float mode = Params3.w;
    vec3 endColor = Params3.rgb;

    vec2 uv = gl_FragCoord.xy / inputResolution.xy;
    float m0 = texture(MaskSampler, uv).r;
    float mb = blurMask(uv);

    float outside = clamp(mb - m0, 0.0, 1.0);
    float t = 0.0;
    float denom = max(1.0, bottomY - topY);
    t = clamp((gl_FragCoord.y - topY) / denom, 0.0, 1.0);
    vec3 shadowColor = mode < 0.5 ? Color1.rgb : mix(Color1.rgb, endColor, t);
    fragColor = vec4(shadowColor, outside * strength);
}
