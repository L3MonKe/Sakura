#version 150

uniform sampler2D InputSampler;
uniform vec2 InputResolution;
uniform vec2 uSize;
uniform vec2 uLocation;

uniform float radius;
uniform float Brightness;
uniform float Quality; // Glow Strength/Radius
uniform vec4 color1;

out vec4 fragColor;

float roundedBoxSDF(vec2 center, vec2 size, float radius) {
    return length(max(abs(center) - size + radius, 0.0)) - radius;
}

void main() {
    vec2 halfSize = uSize / 2.0;
    // Calculate SDF for the rounded rectangle
    float d = roundedBoxSDF(gl_FragCoord.xy - uLocation - halfSize, halfSize, radius);
    
    // Create a glow effect based on distance (SDF)
    // We want the glow to be strong near the edge (d=0) and fade out as d increases.
    // Quality parameter controls the glow falloff/radius.
    
    float glowRadius = Quality * 10.0; // Scale up for visibility
    
    // Inverse distance field for glow
    // We want alpha = 1 at d=0, and alpha = 0 at d = glowRadius
    
    float alpha = 1.0 - smoothstep(0.0, glowRadius, d);
    
    // Optional: Make it exponential or squared for "hotter" core
    alpha = pow(alpha, 2.0);
    
    // Mask out the inside to make it "hollow"
    // Use step function for hard edge: 0.0 if d < 0.0, 1.0 if d >= 0.0
    float mask = step(0.0, d);
    
    // Apply mask
    alpha *= mask;
    
    // Double check with discard for d <= 0.0 (internal)
    if (d <= 0.0) {
        discard;
    }

    // Apply brightness
    alpha *= Brightness;
    
    fragColor = vec4(color1.rgb, color1.a * alpha);
}
