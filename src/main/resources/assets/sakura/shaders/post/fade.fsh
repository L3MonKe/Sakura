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

vec3 wave(vec2 pos, vec3 a, vec3 b, float time) {
    return mix(a, b, sin((distance(vec2(0.0), pos) - time * 60.0) / 60.0) * 0.5 + 0.5);
}

void main() {
    int quality = int(Params2.w);
    int lineWidth = int(Params3.x);
    float alpha0 = Params1.x;
    float fillAlpha = Params1.w;
    float time = Params2.x;

    vec4 outlinecolor = OutlineColor;
    vec3 primary = PrimaryColor.rgb;
    vec3 secondary = SecondaryColor.rgb;

    vec4 centerCol = texture(DiffuseSampler, texCoord);
    if (centerCol.a != 0.0) {
        fragColor = vec4(wave(gl_FragCoord.xy, primary, secondary, time), fillAlpha);
        return;
    }

    float alphaOutline = 0.0;
    vec3 colorFinal = vec3(-1.0);
    for (int x = -quality; x < quality; x++) {
        for (int y = -quality; y < quality; y++) {
            vec2 offset = vec2(x, y);
            vec2 coord = texCoord + offset * oneTexel;
            vec4 t = texture(DiffuseSampler, coord);
            if (t.a == 0.0) continue;

            if (alpha0 == -1.0) {
                if (colorFinal.x < 0.0) {
                    colorFinal = outlinecolor.rgb;
                }
                alphaOutline += outlinecolor.a * 255.0 > 0.0 ? max(0.0, (lineWidth - distance(vec2(x, y), vec2(0.0))) / (outlinecolor.a * 255.0)) : 1.0;
            } else {
                fragColor = vec4(outlinecolor.rgb, alpha0);
                return;
            }
        }
    }
    fragColor = vec4(colorFinal, alphaOutline);
}
