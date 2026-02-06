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

float random(in vec2 st) {
    return fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 43758.5453123);
}

float noise(in vec2 st) {
    vec2 i = floor(st);
    vec2 f = fract(st);
    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));
    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
}

float fbm(in vec2 st, int oct) {
    float v = 0.0;
    float a = 0.5;
    vec2 shift = vec2(100.0);
    mat2 rot = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.50));
    for (int i = 0; i < oct; ++i) {
        v += a * noise(st);
        st = rot * st * 2.0 + shift;
        a *= 0.5;
    }
    return v;
}

vec3 getColor(vec4 centerCol, vec2 resolution, float time, vec4 first, vec3 second, vec3 third, int oct) {
    vec2 st = gl_FragCoord.xy / resolution.xy * 3.;
    vec3 color = vec3(0.0);
    vec2 q = vec2(0.);
    q.x = fbm(st, oct);
    q.y = fbm(st + vec2(1.0), oct);
    vec2 r = vec2(0.);
    r.x = fbm(st + 1.0 * q + vec2(1.7, 9.2) + 0.15 * time, oct);
    r.y = fbm(st + 1.0 * q + vec2(8.3, 2.8) + 0.126 * time, oct);
    float f = fbm(st + r, oct);
    color = vec3(first[0], first[1], first[2]);
    color = mix(color, vec3(second[0], second[1], second[2]), clamp(length(q), 0.0, 1.0));
    color = mix(color, vec3(third[0], third[1], third[2]), clamp(length(r.x), 0.0, 1.0));
    vec4 outputLol = vec4((f * f * f + .6 * f * f + .5 * f) * color, first[3]);
    return vec3(outputLol[0], outputLol[1], outputLol[2]);
}

vec3 getFillColor(vec4 centerCol, vec2 resolution, float time, vec4 ffirst, vec3 fsecond, vec3 fthird, int oct) {
    vec2 st = gl_FragCoord.xy / resolution.xy * 3.;
    vec3 color = vec3(0.0);
    vec2 q = vec2(0.);
    q.x = fbm(st, oct);
    q.y = fbm(st + vec2(1.0), oct);
    vec2 r = vec2(0.);
    r.x = fbm(st + 1.0 * q + vec2(1.7, 9.2) + 0.15 * time, oct);
    r.y = fbm(st + 1.0 * q + vec2(8.3, 2.8) + 0.126 * time, oct);
    float f = fbm(st + r, oct);
    color = vec3(ffirst[0], ffirst[1], ffirst[2]), clamp((f * f) * 4.0, 0.0, 1.0);
    color = mix(color, vec3(fsecond[0], fsecond[1], fsecond[2]), clamp(length(q), 0.0, 1.0));
    color = mix(color, vec3(fthird[0], fthird[1], fthird[2]), clamp(length(r.x), 0.0, 1.0));
    vec4 outputLol = vec4((f * f * f + .6 * f * f + .5 * f) * color, ffirst[3]);
    return vec3(outputLol[0], outputLol[1], outputLol[2]);
}

void main() {
    int quality = int(Params2.w);
    int lineWidth = int(Params3.x);
    int oct = int(Params3.y);

    float alpha0 = Params1.x;
    float alpha1 = Params1.y;
    float time = Params2.x;

    vec2 resolution = Resolution.xy;

    vec4 first = OutlineColor;
    vec3 second = OutlineColor1.rgb;
    vec3 third = OutlineColor2.rgb;

    vec4 ffirst = FillColor1;
    vec3 fsecond = FillColor2.rgb;
    vec3 fthird = FillColor3.rgb;

    vec4 centerCol = texture(DiffuseSampler, texCoord);
    if (centerCol.a != 0.0) {
        fragColor = vec4(getFillColor(centerCol, resolution, time, ffirst, fsecond, fthird, oct), alpha1);
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
                alphaOutline += first.a * 255.0 > 0.0 ? max(0.0, (lineWidth - distance(vec2(x, y), vec2(0.0))) / (first.a * 255.0)) : 1.0;
            } else {
                fragColor = vec4(getColor(centerCol, resolution, time, first, second, third, oct), alpha0);
                return;
            }
        }
    }

    if (alphaOutline > 0.0) {
        colorFinal = getColor(centerCol, resolution, time, first, second, third, oct);
    }
    fragColor = vec4(colorFinal, alphaOutline);
}
