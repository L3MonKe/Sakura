#version 150

layout(std140) uniform HealthBarUniforms {
    vec2 resolution;
    float time;
    float hueMin;
    float hueMax;
    float satMin;
    float satMax;
    float valMin;
    float valMax;
    float barPosX;
    float barPosY;
    float barWidth;
    float barHeight;
    float barRadius;
};

in vec2 vUv;
out vec4 fragColor;

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float range(float val, float mi, float ma) {
    return val * (ma - mi) + mi;
}

float roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {
    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;
}

void main() {
    vec2 fragCoord = gl_FragCoord.xy;

    vec2 center = vec2(barPosX + barWidth / 2.0, barPosY + barHeight / 2.0);
    vec2 halfSize = vec2(barWidth / 2.0, barHeight / 2.0);
    float dist = roundedBoxSDF(fragCoord - center, halfSize, barRadius);

    if (dist > 1.0) {
        discard;
    }

    vec2 p = -1.0 + 2.0 * fragCoord.xy / resolution.xy;
    float t = time / 5.0;

    float x = p.x;
    float y = p.y;

    float mov0 = x + y + cos(sin(t) * 2.0) * 100.0 + sin(x / 100.0) * 1000.0;
    float mov1 = y / 0.3 + t;
    float mov2 = x / 0.2;

    float c1 = abs(sin(mov1 + t) / 2.0 + mov2 / 2.0 - mov1 - mov2 + t);
    float c2 = abs(sin(c1 + sin(mov0 / 1000.0 + t) + sin(y / 40.0 + t) + sin((x + y) / 100.0) * 3.0));
    float c3 = abs(sin(c2 + cos(mov1 + mov2 + c2) + cos(mov2) + sin(x / 1000.0)));

    vec3 col = hsv2rgb(vec3(range(c2, hueMin, hueMax), range(c3, satMin, satMax), range(c3, valMin, valMax)));

    float alpha = 1.0 - smoothstep(0.0, 1.0, dist);
    fragColor = vec4(col, alpha);
}
