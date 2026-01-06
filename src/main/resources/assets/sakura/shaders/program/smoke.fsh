
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


uniform vec2 resolution;
uniform float time;

uniform vec4 first;
uniform vec3 second;
uniform vec3 third;

uniform vec4 ffirst;
uniform vec3 fsecond;
uniform vec3 fthird;

uniform int oct;


float random (in vec2 _st) {
    return fract(sin(dot(_st.xy, vec2(12.9898,78.233)))*43758.5453123);
}

float noise (in vec2 _st) {
    vec2 i = floor(_st);
    vec2 f = fract(_st);
    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));
    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(a, b, u.x) + (c - a)* u.y * (1.0 - u.x) + (d - b) * u.x * u.y;
}

float fbm( in vec2 _st) {
    float v = 0.0;
    float a = 0.5;
    vec2 shift = vec2(100.0);
    mat2 rot = mat2(cos(0.5), sin(0.5),
    -sin(0.5), cos(0.50));
    for (int i = 0; i < oct; ++i) {
        v += a * noise(_st);
        _st = rot * _st * 2.0 + shift;
        a *= 0.5;
    }
    return v;
}


vec3 getColor(vec4 centerCol) {
    vec2 st = gl_FragCoord.xy / resolution.xy*3.;
    vec3 color = vec3(0.0);
    vec2 q = vec2(0.);
    q.x = fbm(st);
    q.y = fbm( st + vec2(1.0));
    vec2 r = vec2(0.);
    r.x = fbm( st + 1.0*q + vec2(1.7,9.2)+ 0.15*time );
    r.y = fbm( st + 1.0*q + vec2(8.3,2.8)+ 0.126*time);
    float f = fbm(st+r);
    color = vec3(first[0],first[1],first[2]);
    color = mix(color, vec3(second[0],second[1],second[2]), clamp(length(q),0.0,1.0));
    color = mix(color, vec3(third[0],third[1],third[2]), clamp(length(r.x),0.0,1.0));
    vec4 outputLol = vec4((f*f*f+.6*f*f+.5*f)*color,first[3]);
    return vec3(outputLol[0], outputLol[1], outputLol[2]);
}

vec3 getFillColor(vec4 centerCol) {
    vec2 st = gl_FragCoord.xy / resolution.xy*3.;
    vec3 color = vec3(0.0);
    vec2 q = vec2(0.);
    q.x = fbm(st);
    q.y = fbm( st + vec2(1.0));
    vec2 r = vec2(0.);
    r.x = fbm( st + 1.0*q + vec2(1.7,9.2)+ 0.15*time );
    r.y = fbm( st + 1.0*q + vec2(8.3,2.8)+ 0.126*time);
    float f = fbm(st+r);
    color = vec3(ffirst[0],ffirst[1],ffirst[2]);
    color = mix(color, vec3(fsecond[0],fsecond[1],fsecond[2]), clamp(length(q),0.0,1.0));
    color = mix(color, vec3(fthird[0],fthird[1],fthird[2]), clamp(length(r.x),0.0,1.0));
    vec4 outputLol = vec4((f*f*f+.6*f*f+.5*f)*color,ffirst[3]);
    return vec3(outputLol[0], outputLol[1], outputLol[2]);
}

void main() {
    vec4 sceneCol = texture(InSampler, texCoord);
    vec4 centerCol = texture(MaskSampler, texCoord);

    if (centerCol.a != 0) {
        vec3 fillRgb = getFillColor(centerCol);
        vec3 outRgb = mix(sceneCol.rgb, fillRgb, clamp(alpha1, 0.0, 1.0));
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
            if (t.a != 0) {
                found = true;
                if (alpha0 == -1.0) {
                    alphaOutline += first.a * 255.0 > 0 ? max(0.0, (lineWidth - distance(vec2(x, y), vec2(0))) / (first.a * 255.0)) : 1.0;
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

