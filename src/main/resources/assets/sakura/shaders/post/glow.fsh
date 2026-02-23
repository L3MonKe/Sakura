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

void main() {
    float radius = Params3.x; // Reusing LineWidth as Radius
    vec2 direction = Params3.zw; // x, y direction
    vec3 color = OutlineColor.rgb; // Glow color
    float exposure = Params2.y; // Reusing Factor as Exposure

    vec4 centerCol = texture(DiffuseSampler, texCoord);
    
    if (centerCol.a != 0) {
        fragColor = centerCol; // Keep original entity? Or just the glow? 1.8.9 usually draws glow behind.
        // If this is the first pass (horizontal), we might just be blurring the alpha.
        // If this is the final pass, we composite.
        // For simplicity, let's assume we are blurring the alpha/color.
    }

    // Gaussian Blur
    vec4 sum = vec4(0.0);
    float totalWeight = 0.0;
    float sigma = radius / 2.0;

    // Center pixel
    float centerWeight = 1.0 / (2.50662827463 * sigma); // 1 / sqrt(2*pi) * sigma
    sum += texture(DiffuseSampler, texCoord) * centerWeight;
    totalWeight += centerWeight;

    for (float i = 1.0; i <= radius; i += 1.0) {
        float weight = (1.0 / (2.50662827463 * sigma)) * exp(-(i * i) / (2.0 * sigma * sigma));
        
        vec2 offset = direction * i * oneTexel;
        sum += texture(DiffuseSampler, texCoord + offset) * weight;
        sum += texture(DiffuseSampler, texCoord - offset) * weight;
        totalWeight += weight * 2.0;
    }

    vec4 blurred = sum / totalWeight;

    // Apply exposure/color
    if (blurred.a > 0.0) {
         // If we are drawing the glow, we want to apply the color.
         // In 1.8.9: 
         // glowShader.setUniformf("color", ...);
         // The shader likely outputs vec4(color.rgb, blurred.a * exposure)
         
         fragColor = vec4(color, blurred.a * exposure);
    } else {
        fragColor = vec4(0.0);
    }
}
