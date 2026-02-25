#version 150

uniform sampler2D InputSampler;
uniform vec2 InputResolution;
uniform vec2 uSize;
uniform vec2 uLocation;

uniform float radius;
uniform float Brightness;
uniform float Quality;
uniform vec4 color1;
uniform float RefractionAmount;
uniform float RefractionBand;
uniform float RefractionStrength;
uniform float LensCurvature;

out vec4 fragColor;

float roundedBoxSDF(vec2 center, vec2 size, float radius) {
    return length(max(abs(center) - size + radius, 0.0)) - radius;
}

vec4 blur() {
    #define TAU 6.28318530718

    vec2 Radius = Quality / InputResolution.xy;
    vec2 halfSize = uSize / 2.0;
    vec2 center = uLocation + halfSize;
    float distToEdge = roundedBoxSDF(gl_FragCoord.xy - center, halfSize, radius);
    float band = 1.0 - smoothstep(-RefractionBand, 0.0, distToEdge);
    vec2 baseUV = gl_FragCoord.xy / InputResolution.xy;

    vec2 local = (gl_FragCoord.xy - center) / halfSize;
    float r = clamp(length(local), 0.0, 1.0);
    float lensFactor = pow(1.0 - r, max(LensCurvature, 0.0001));
    float k = RefractionAmount * RefractionStrength * lensFactor;
    float r2 = r * r;
    vec2 distortedLocal = local * (1.0 + k * (1.0 - r2));
    vec2 refractedUV = (center + distortedLocal * halfSize) / InputResolution.xy;

    vec4 blurred = texture(InputSampler, baseUV);

    float step =  TAU / 16.0;
    for (float d = 0.0; d < TAU; d += step) {
        for (float i = 0.2; i <= 1.0; i += 0.2) {
            blurred += texture(InputSampler, baseUV + vec2(cos(d), sin(d)) * Radius * i);
        }
    }

    blurred /= 81.0;
    vec4 refracted = texture(InputSampler, refractedUV);
    float mixFactor = clamp((lensFactor + band * 0.5) * RefractionStrength, 0.0, 1.0);
    vec4 mixed = mix(blurred, refracted, mixFactor);
    return mixed + (color1 * color1.a);
}

void main() {
    vec2 halfSize = uSize / 2.0;
    float smoothedAlpha = 1.0 - smoothstep(0.0, 1.0, roundedBoxSDF(gl_FragCoord.xy - uLocation - halfSize, halfSize, radius));
    fragColor = vec4(blur().rgb, smoothedAlpha * Brightness);
}
