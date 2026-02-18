#version 150

layout(std140) uniform MenuUniforms {
    vec2 resolution;
    float time;
    float transition;
    vec2 mouse;
    vec2 uSize;
};

in vec2 vUv;
out vec4 fragColor;

#define S(a,b,c) smoothstep(a,b,c)
#define sat(a) clamp(a,0.0,1.0)

vec4 N14(float t) {
    return fract(sin(t*vec4(123., 104., 145., 24.))*vec4(657., 345., 879., 154.));
}

vec4 sakura(vec2 uv, vec2 id, float blur) {
    float t = time + 45.0;

    vec4 rnd = N14(mod(id.x, 500.0) * 5.4 + mod(id.y, 500.0) * 13.67);

    uv *= mix(0.75, 1.3, rnd.y);
    uv.x += sin(t * rnd.z * 0.3) * 0.6;
    uv.y += sin(t * rnd.w * 0.45) * 0.4;

    float angle = atan(uv.y, uv.x) + rnd.x * 421.47 + t * mix(-0.6, 0.6, rnd.x);

    float dist = length(uv);

    float petal = 1.0 - abs(sin(angle * 2.5));
    float sqPetal = petal * petal;
    petal = mix(petal, sqPetal, 0.7);
    float petal2 = 1.0 - abs(sin(angle * 2.5 + 1.5));
    petal += petal2 * 0.2;

    float sakuraDist = dist + petal * 0.25;

    float shadowblur = 0.3;
    float shadow = S(0.5 + shadowblur, 0.5 - shadowblur, sakuraDist) * 0.4;

    float sakuraMask = S(0.5 + blur, 0.5 - blur, sakuraDist);

    vec3 sakuraCol = vec3(1.0, 0.6, 0.7);
    sakuraCol += (0.5 -  dist) * 0.2;

    vec3 outlineCol = vec3(1.0, 0.3, 0.3);
    float outlineMask = S(0.5 - blur, 0.5, sakuraDist + 0.045);

    float polarSpace = angle * 1.9098 + 0.5;
    float polarPistil = fract(polarSpace) - 0.5;

    outlineMask += S(0.035 + blur, 0.035 - blur, dist);

    float petalBlur = blur * 2.0;
    float pistilMask = S(0.12 + blur, 0.12, dist) * S(0.05, 0.05 + blur , dist);

    float barW = 0.2 - dist * 0.7;
    float pistilBar = S(-barW, -barW + petalBlur, polarPistil) * S(barW + petalBlur, barW, polarPistil);

    float pistilDotLen = length(vec2(polarPistil * 0.10, dist) - vec2(0, 0.16)) * 9.0;
    float pistilDot = S(0.1 + petalBlur, 0.1 - petalBlur, pistilDotLen);

    outlineMask += pistilMask * pistilBar + pistilDot;
    sakuraCol = mix(sakuraCol, outlineCol, sat(outlineMask) * 0.5);

    sakuraCol = mix(vec3(0.4, 0.4, 0.8) * shadow, sakuraCol, sakuraMask);

    sakuraMask = sat(sakuraMask + shadow);

    return vec4(sakuraCol, sakuraMask);
}

vec3 premulMix(vec4 src, vec3 dst) {
    return dst.rgb * (1.0 - src.a) + src.rgb;
}

vec4 premulMix(vec4 src, vec4 dst) {
    vec4 res;
    res.rgb = premulMix(src, dst.rgb);
    res.a = 1.0 - (1.0 - src.a) * (1.0 - dst.a);
    return res;
}

vec4 layer(vec2 uv, float blur)
{
    vec2 cellUV = fract(uv) - 0.5;
    vec2 cellId = floor(uv);

    vec4 accum = vec4(0.0);

    for (float y = -1.0; y <= 1.0; y++)
    {
        for (float x = -1.0; x <= 1.0; x++)
        {
            vec2 offset = vec2(x, y);
            vec4 sakura = sakura(cellUV - offset, cellId + offset, blur);
            accum = premulMix(sakura, accum);
        }
    }

    return accum;
}

void main() {
    vec2 fragCoord = gl_FragCoord.xy;
    vec2 nominalUV = fragCoord/resolution.xy;

    vec2 uv = nominalUV - 0.5;
    float aspectRatio = resolution.x / resolution.y;
    uv.x *= aspectRatio;

    vec2 originalUV = uv;

    float t = clamp(transition, 0.0, 1.0);

    float easeT = t < 0.5 ? 2.0 * t * t : 1.0 - pow(-2.0 * t + 2.0, 2.0) / 2.0;

    float centerDist = length(originalUV);

    float expandRadius = easeT * 3.5;
    float expandMask = smoothstep(expandRadius - 0.5, expandRadius + 0.5, centerDist);
    expandMask = 1.0 - expandMask;

    float alpha = smoothstep(0.0, 0.25, t);

    float scaleT = easeT;
    float scaleTransition = mix(0.1, 1.0, scaleT);

    uv *= 4.3 * scaleTransition;

    uv.y += time * 0.1;
    uv.x -= time * 0.03 + sin(time) * 0.1;

    float screenY = nominalUV.y;
    vec3 bgColor = vec3(1.0, 0.7529, 0.8235) - 0.15;
    vec3 col = bgColor;

    float blur = abs(nominalUV.y - 0.5) * 1.4;
    blur *= blur * 0.15;

    float parallax = mouse.x * 0.001;

    vec4 layer1 = layer(uv - vec2(parallax * 0.5, 0.0), 0.015 + blur);
    vec4 layer2 = layer(uv * 1.4 + vec2(124.5, 89.30) - vec2(parallax * 1.0, 0.0), 0.05 + blur);
    layer2.rgb *= mix(0.7, 0.95, screenY);
    vec4 layer3 = layer(uv * 2.3 + vec2(463.5, -987.30) - vec2(parallax * 1.5, 0.0), 0.08 + blur);
    layer3.rgb *= mix(0.55, 0.85, screenY);

    vec3 sakuraCol = bgColor;
    sakuraCol = premulMix(layer3, sakuraCol);
    sakuraCol = premulMix(layer2, sakuraCol);
    sakuraCol = premulMix(layer1, sakuraCol);
    sakuraCol += -0.15;

    float finalMask = expandMask * alpha;

    col = mix(bgColor, sakuraCol, finalMask);

    fragColor = vec4(col, 1.0);
}
