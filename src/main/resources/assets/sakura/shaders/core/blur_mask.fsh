#version 150

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

void main() {
    int count = int(Params4.x);
    float d = 1e20;

    for (int i = 0; i < 64; i++) {
        if (i >= count) break;
        vec4 rect = SegRects[i];
        float r = SegExtra[i].x;
        vec2 h = rect.zw / 2.0;
        float di = roundedBoxSDF(gl_FragCoord.xy - rect.xy - h, h, r);
        d = min(d, di);
    }

    float mask = (1.0 - smoothstep(-1.0, 1.0, d));
    fragColor = vec4(mask, mask, mask, 1.0);
}
