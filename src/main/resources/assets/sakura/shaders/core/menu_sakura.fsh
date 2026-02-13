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

// --- Ethereal Sakura Dreamscape (V4) ---
// Concept: Abstract, Flowing, Bokeh, Elegant Pink/Purple Gradient
// Fix: Added Mouse Interaction (Liquid Distortion)

#define PI 3.14159265

// --- Random & Noise Utils ---
float hash(float n) { return fract(sin(n) * 43758.5453123); }
float hash2(vec2 p) { 
    p = 50.0 * fract(p * 0.3183099 + vec2(0.71, 0.113));
    return -1.0 + 2.0 * fract(p.x * p.y * (p.x + p.y));
}

// Gradient Noise
float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash2(i + vec2(0.0,0.0)), 
                   hash2(i + vec2(1.0,0.0)), u.x),
               mix(hash2(i + vec2(0.0,1.0)), 
                   hash2(i + vec2(1.0,1.0)), u.x), u.y);
}

// FBM with time-varying evolution
float fbm(vec2 p) {
    float f = 0.0;
    float amp = 0.5;
    mat2 m = mat2(1.6, 1.2, -1.2, 1.6);
    
    // Time evolution factor
    float t = time * 0.15; 
    
    for(int i=0; i<4; i++) {
        float n = noise(p + vec2(t * (float(i)+1.0)*0.2)); 
        f += amp * n;
        p = m * p;
        amp *= 0.5;
    }
    return f;
}

// Domain Warping for "Silk" effect
float pattern(vec2 p, out vec2 q, out vec2 r) {
    q.x = fbm(p + vec2(0.0, 0.0));
    q.y = fbm(p + vec2(5.2, 1.3));
    
    float t = time * 0.05; 
    vec2 s1 = vec2(cos(t), sin(t)) * 0.5;
    vec2 s2 = vec2(sin(t*0.7), cos(t*0.7)) * 0.5;
    
    r.x = fbm(p + 4.0 * q + vec2(1.7, 9.2) + s1);
    r.y = fbm(p + 4.0 * q + vec2(8.3, 2.8) + s2);
    
    return fbm(p + 4.0 * r);
}

// --- Bokeh Particle System ---
float bokehLayer(vec2 uv, float size, float speed, float brightness) {
    vec2 grid = uv * size;
    vec2 id = floor(grid);
    vec2 f = fract(grid) - 0.5;
    
    float n = fract(sin(dot(id, vec2(12.9898, 78.233))) * 43758.5453);
    
    // Animate position
    float t = time * speed + n * 10.0;
    vec2 offset = vec2(sin(t), cos(t * 0.8)) * 0.4;
    vec2 pos = f - offset;
    
    float d = length(pos);
    float circle = smoothstep(0.4, 0.3, d);
    
    float randomSize = 0.5 + 0.5 * sin(time * speed + n * 100.0);
    circle *= smoothstep(0.5, 0.0, d / (0.2 + 0.3 * randomSize));
    
    return circle * brightness * n;
}

// --- Detailed Sakura Petal SDF ---
float petalSDF(vec2 p, float s) {
    p /= s;
    p.y -= 0.3;
    float a = atan(p.x, p.y) / PI;
    float r = length(p);
    float h = abs(a);
    float d = (13.0*h - 22.0*h*h + 10.0*h*h*h)/(6.0-5.0*h);
    return r - d;
}

// Foreground Petals
vec3 fallingPetals(vec2 uv, vec3 col) {
    for(int i=0; i<12; i++) {
        float fi = float(i);
        float speed = 0.1 + hash(fi) * 0.1;
        float offset = hash(fi * 12.34) * 10.0;
        
        vec2 pos;
        pos.x = sin(time * 0.2 + offset) * 0.5 + (hash(fi * 56.78) - 0.5) * 2.5;
        pos.y = 1.2 - mod(time * speed + offset, 2.4);
        
        pos.x += sin(pos.y * 5.0 + time) * 0.1;
        
        float rot = time * (hash(fi)*2.0 - 1.0) + offset;
        vec2 p = uv - pos;
        float s = sin(rot), c = cos(rot);
        p = mat2(c, -s, s, c) * p;
        
        float flip = sin(time * 2.0 + offset);
        p.x /= (0.1 + 0.9 * abs(flip));
        
        float size = 0.03 + hash(fi) * 0.02;
        float d = petalSDF(p, size);
        
        float alpha = smoothstep(0.005, -0.005, d);
        
        vec3 pCol = vec3(1.0, 0.9, 0.95);
        float grad = length(p) / size;
        pCol = mix(vec3(1.0, 0.6, 0.7), pCol, smoothstep(0.0, 0.8, grad));
        
        pCol *= 0.9 + 0.2 * flip;
        col = mix(col, pCol, alpha * 0.9);
    }
    return col;
}

void main() {
    vec2 uv = (gl_FragCoord.xy - 0.5 * resolution.xy) / resolution.y;
    
    // --- Mouse Interaction ---
    // Calculate mouse influence
    vec2 mouseUV = mouse * vec2(resolution.x/resolution.y, 1.0); // Assuming mouse is 0-1, correct aspect
    // Actually uv is centered at 0,0. mouse is likely 0..1 from top-left?
    // Let's assume mouse uniform is 0..1.
    // Center mouse coordinates to match uv space (-aspect/2 to aspect/2, -0.5 to 0.5)
    vec2 mPos = (mouse - 0.5) * vec2(resolution.x/resolution.y, 1.0); 
    // Invert Y if needed (OpenGL coords vs Mouse coords)
    // Usually handled in Java, but let's see. 
    // If mouse is at top-left (0,0), mPos would be (-aspect, 1.0).
    // Let's just use distance for now.
    
    float dist = length(uv - mPos);
    // Create a "push" or "ripple" effect
    float interact = smoothstep(0.5, 0.0, dist);
    
    // Distort UV based on mouse
    vec2 warpUV = uv * 2.0; 
    warpUV -= (uv - mPos) * interact * 0.2; // Push away from mouse
    
    // --- 1. Background: Fluid Silk (Evolving) ---
    vec2 q, r;
    
    float fluid = pattern(warpUV, q, r);
    
    // Color Palette
    vec3 colDark = vec3(0.2, 0.1, 0.35);  
    vec3 colMid  = vec3(0.8, 0.3, 0.6);   
    vec3 colLight= vec3(1.0, 0.9, 0.95);  
    
    vec3 bgCol = mix(colDark, colMid, smoothstep(0.0, 1.0, fluid));
    
    // Enhanced highlights
    float highlight = smoothstep(0.4, 1.0, r.x); 
    // Add mouse glow to highlights
    highlight += interact * 0.3;
    
    bgCol = mix(bgCol, colLight, highlight * 0.8); 
    bgCol += colMid * smoothstep(0.6, 0.9, fluid) * 0.3;
    
    // Vignette
    float vig = 1.0 - length(uv) * 0.5;
    bgCol *= vig;
    
    // --- 2. Bokeh Particles ---
    vec3 bokehCol = vec3(0.0);
    // Apply slight parallax to bokeh based on mouse
    vec2 bokehOffset = (mouse - 0.5) * 0.1;
    
    float b1 = bokehLayer(uv + bokehOffset * 0.5, 8.0, 0.5, 0.25);
    bokehCol += vec3(1.0, 0.8, 0.9) * b1;
    
    float b2 = bokehLayer(uv * 0.8 + 0.5 + bokehOffset * 0.3, 5.0, 0.8, 0.2);
    bokehCol += vec3(1.0, 0.6, 0.8) * b2;
    
    float b3 = bokehLayer(uv * 0.5 + 0.2 + bokehOffset * 0.1, 3.0, 1.0, 0.1);
    bokehCol += vec3(1.0, 0.9, 1.0) * b3;
    
    bgCol += bokehCol;
    
    // --- 3. Foreground Petals ---
    vec3 finalCol = fallingPetals(uv + bokehOffset * 0.8, bgCol);
    
    // --- 4. Color Grading ---
    finalCol = finalCol * 1.1; 
    finalCol = pow(finalCol, vec3(1.2)); 
    finalCol = mix(finalCol, finalCol * vec3(1.05, 1.0, 1.1), 0.1); 
    
    // --- Transition ---
    float tVal = clamp(transition, 0.0, 1.0);
    float easeT = tVal < 0.5 ? 2.0 * tVal * tVal : 1.0 - pow(-2.0 * tVal + 2.0, 2.0) / 2.0;
    
    vec2 aspectUV = uv;
    aspectUV.x *= resolution.x / resolution.y;
    float distCenter = length(uv);
    float expandRadius = easeT * 3.5;
    float mask = 1.0 - smoothstep(expandRadius - 0.5, expandRadius + 0.5, distCenter);
    
    finalCol = mix(vec3(0.0), finalCol, mask);
    
    fragColor = vec4(finalCol, 1.0);
}
