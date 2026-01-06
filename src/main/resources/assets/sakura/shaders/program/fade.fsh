#version 150

in vec2 texCoord;
out vec4 fragColor;

uniform sampler2D InSampler;
uniform sampler2D MaskSampler;
uniform vec4 primaryColor;
uniform vec4 secondaryColor;
uniform float time;
uniform vec2 InSize;

uniform vec4 outlinecolor;
uniform float alpha0;
uniform float fillAlpha;
uniform int quality;
uniform int lineWidth;
in vec2 oneTexel;

vec3 wave(vec2 pos)
{
    return mix(primaryColor.rgb, secondaryColor.rgb, sin((distance(vec2(0), pos) - time * 60.0) / 60.) * 0.5 + 0.5);
}

void main()
{
    vec4 sceneCol = texture(InSampler, texCoord);
    vec4 centerCol = texture(MaskSampler, texCoord);

    if (centerCol.a != 0) {
        vec3 fillRgb = wave(gl_FragCoord.xy);
        vec3 outRgb = mix(sceneCol.rgb, fillRgb, clamp(fillAlpha, 0.0, 1.0));
        fragColor = vec4(outRgb, 1.0);
        return;
    } else {
        float alphaOutline = 0.0;
        vec3 colorFinal = vec3(-1.0);
        for (int x = -quality; x < quality; x++) {
            for (int y = -quality; y < quality; y++) {
                vec2 offset = vec2(x, y);
                vec2 coord = texCoord + offset * oneTexel;
                vec4 t = texture(MaskSampler, coord);
                if (t.a != 0.0){
                    if (alpha0 == -1.0) {
                        if (colorFinal[0] == -1.0) {
                            colorFinal = outlinecolor.rgb;
                        }
                        alphaOutline += outlinecolor.a * 255.0 > 0 ? max(0.0, (lineWidth - distance(vec2(x, y), vec2(0))) / (outlinecolor.a * 255.0)) : 1.0;
                    }
                    else {
                        alphaOutline = alpha0;
                        colorFinal = outlinecolor.rgb;
                        x = quality;
                        break;
                    }
                }
            }
        }
        if (alphaOutline <= 0.0) {
            fragColor = vec4(sceneCol.rgb, 1.0);
            return;
        }
        vec3 outlineRgb = colorFinal[0] == -1.0 ? outlinecolor.rgb : colorFinal;
        vec3 outRgb = mix(sceneCol.rgb, outlineRgb, clamp(alphaOutline, 0.0, 1.0));
        fragColor = vec4(outRgb, 1.0);
    }
}
