#version 150

layout(std140) uniform SplashUniforms {
    vec2 resolution;
    float time;
    float progress;
    float fadeOut;
    float zoom;
    float _pad0;
    float _pad1;
};

in vec2 vUv;

out vec4 fragColor;

#define S(a,b,c) smoothstep(a,b,c)
#define sat(a) clamp(a,0.0,1.0)
#define PI 3.14159265359

vec4 sakura(vec2 uv, float blur, float rotation) {
    float c = cos(rotation);
    float s = sin(rotation);
    uv = mat2(c, -s, s, c) * uv;

    float angle = atan(uv.y, uv.x);
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
    sakuraCol += (0.5 - dist) * 0.2;

    vec3 outlineCol = vec3(1.0, 0.3, 0.3);
    float outlineMask = S(0.5 - blur, 0.5, sakuraDist + 0.045);

    float polarSpace = angle * 1.9098 + 0.5;
    float polarPistil = fract(polarSpace) - 0.5;

    outlineMask += S(0.035 + blur, 0.035 - blur, dist);

    float petalBlur = blur * 2.0;
    float pistilMask = S(0.12 + blur, 0.12, dist) * S(0.05, 0.05 + blur, dist);

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

float progressRing(vec2 uv, float radius, float thickness, float prog) {
    float dist = length(uv);
    float angle = atan(uv.y, uv.x);

    float normalizedAngle = (-angle + PI * 0.5) / (2.0 * PI);
    normalizedAngle = fract(normalizedAngle);

    float ring = S(radius + thickness * 0.5 + 0.005, radius + thickness * 0.5, dist) *
                 S(radius - thickness * 0.5, radius - thickness * 0.5 + 0.005, dist);

    float progressMask = S(prog - 0.01, prog, normalizedAngle);
    progressMask = 1.0 - progressMask;

    return ring * progressMask;
}

float ringTrack(vec2 uv, float radius, float thickness) {
    float dist = length(uv);
    float ring = S(radius + thickness * 0.5 + 0.005, radius + thickness * 0.5, dist) *
                 S(radius - thickness * 0.5, radius - thickness * 0.5 + 0.005, dist);
    return ring;
}

void main() {
    vec2 fragCoord = gl_FragCoord.xy;
    vec2 uv = (fragCoord - 0.5 * resolution) / min(resolution.x, resolution.y);

    vec3 bgColor = vec3(1.0, 0.7529, 0.8235) - 0.15;

    float currentZoom = max(1.0, zoom);

    float zoomProgress = smoothstep(1.0, 8.0, currentZoom);
    float rotationSpeed = mix(0.5, 1.0, zoomProgress);
    float rotation = time * rotationSpeed;

    float scale = 2.5 / currentZoom;

    vec4 sakuraResult = sakura(uv * scale, 0.02 / currentZoom, rotation);

    float fadeAlpha = 1.0 - smoothstep(1.0, 6.0, currentZoom);
    fadeAlpha *= (1.0 - fadeOut);

    sakuraResult.a *= fadeAlpha;

    vec3 col = mix(bgColor, sakuraResult.rgb, sakuraResult.a);

    float overallAlpha = 1.0 - smoothstep(1.0, 8.0, currentZoom);
    overallAlpha *= (1.0 - fadeOut);

    float ringRadius = 0.28;
    float ringThickness = 0.012;

    vec2 ringUV = uv * (1.0 + (currentZoom - 1.0) * 0.5);

    float ringAlpha = 1.0 - smoothstep(1.0, 5.0, currentZoom);
    ringAlpha *= (1.0 - fadeOut);

    if (ringAlpha > 0.01) {
        float track = ringTrack(ringUV, ringRadius, ringThickness);
        vec3 trackColor = vec3(1.0, 0.9, 0.95);
        col = mix(col, trackColor, track * 0.3 * ringAlpha);

        float progressBar = progressRing(ringUV, ringRadius, ringThickness, progress);

        float angle = atan(ringUV.y, ringUV.x);
        float normalizedAngle = (-angle + PI * 0.5) / (2.0 * PI);
        normalizedAngle = fract(normalizedAngle);
        vec3 progressColor1 = vec3(1.0, 0.7, 0.8);
        vec3 progressColor2 = vec3(1.0, 0.4, 0.6);
        vec3 progressColor = mix(progressColor1, progressColor2, normalizedAngle);

        float glow = progressBar * 1.2 * ringAlpha;
        col = mix(col, progressColor, progressBar * ringAlpha);
        col += progressColor * glow * 0.15;

        if (progress > 0.01) {
            float headAngle = -progress * 2.0 * PI + PI * 0.5;
            vec2 headPos = vec2(cos(headAngle), sin(headAngle)) * ringRadius;
            float headDist = length(ringUV - headPos);
            float headDot = S(0.02, 0.015, headDist) * ringAlpha;
            col = mix(col, vec3(1.0), headDot);
        }
    }

    fragColor = vec4(col, overallAlpha);
}
