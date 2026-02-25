package dev.mzc.client.shaders;

public class ShaderProgram {
    public static final String PASSTHROUGH = """
            #version 150
            
            in vec3 Position;
            
            void main() {
                gl_Position = vec4(Position, 1.0);
            }
            
            """;

    public static final String SPLASH = """
            #version 150
            
            uniform vec2 resolution;
            uniform float time;
            uniform float progress;
            uniform float fadeOut;
            uniform float zoom;
            
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
            
                float bgAlpha = fadeAlpha;
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
            
            """;

    public static final String CUTE = """
            #version 150
            
            uniform float time;
            uniform vec2 resolution;
            
            out vec4 fragColor;
            
            vec3 hsv2rgb(vec3 c) {
                vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
                vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
                return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
            }
            
            float range(float val, float mi, float ma) {
                return val * (ma - mi) + mi;
            }
            
            void main() {
                vec2 fragCoord = gl_FragCoord.xy;
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
            
                vec3 col = hsv2rgb(vec3(range(c2, 0.85, 0.95), range(c3, 0.5, 0.55), range(c3, 1.0, 0.75)));
            
                fragColor = vec4(col, 1.0);
            }
            
            """;
    public static final String SAKURA = """
            #version 150
            
            uniform vec2 resolution;
            uniform float time;
            uniform float transition;
            uniform vec2 mouse;
            
            out vec4 fragColor;
            
            #define S(a,b,c) smoothstep(a,b,c)
            #define sat(a) clamp(a,0.0,1.0)
            
            vec4 N14(float t) {
            	return fract(sin(t*vec4(123., 104., 145., 24.))*vec4(657., 345., 879., 154.));
            }
            
            vec4 sakura(vec2 uv, vec2 id, float blur) {
                float t = time + 45.0;\s
            
                vec4 rnd = N14(mod(id.x, 500.0) * 5.4 + mod(id.y, 500.0) * 13.67);\s
            
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
                float polarPistil = fract(polarSpace) - 0.5;\s
            
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
            
            """;



    public static final String PULSATING = """
            #version 150
            
            uniform float time;
            uniform vec2 resolution;
            uniform vec2 mouse;
            
            out vec4 fragColor;
            
            #define iTime time
            #define iResolution vec3(resolution, 0.0)
            
            void mainImage(out vec4 O, vec2 I) 
            { 
                //Vector for scaling and turbulence 
                vec2 v = iResolution.xy, 
                //Centered and scaled coordinates 
                p = (I+I-v)/v.y/.3; 
                 
                //Iterators for layers and turbulence frequency 
                float i=0., f; 
                for(O=vec4(0.);i++<9.; 
                    //Add coloring, attenuating with turbulent coordinates 
                    O += (cos(i+vec4(0,1,2,3))+1.)/6./length(v)) 
                    //Turbulence loop 
                    // `https://mini.gmshaders.com/p/turbulence`  
                    for(v=p,f=0.;f++<9.;v+=sin(v.yx*f+i+iTime)/f); 
                 
                //Tanh tonemapping 
                // `https://www.shadertoy.com/view/ms3BD7`  
                O = tanh(O*O); 
            }
            
            void main() {
                mainImage(fragColor, gl_FragCoord.xy);
                fragColor.a = 1.0;
            }
            """;

    public static final String BLACK_HOLE = """
            #version 150
            
            uniform float time;
            uniform vec2 resolution;
            uniform vec2 mouse;
            
            out vec4 fragColor;
            
            #define iTime time
            #define iResolution vec3(resolution, 0.0)
            #define iMouse vec4(mouse * resolution, 0.0, 0.0)
            
            // Black Hole shader inspired by Interstellar
            
            const float AA = 1.0; 
            
            mat2 rotate(float a) {
                float c = cos(a), s = sin(a);
                return mat2(c, -s, s, c);
            }
            
            float hash(float n) { return fract(sin(n) * 43758.5453123); }
            
            float noise(vec3 x) {
                vec3 p = floor(x);
                vec3 f = fract(x);
                f = f * f * (3.0 - 2.0 * f);
                float n = p.x + p.y * 57.0 + 113.0 * p.z;
                return mix(mix(mix(hash(n + 0.0), hash(n + 1.0), f.x),
                               mix(hash(n + 57.0), hash(n + 58.0), f.x), f.y),
                           mix(mix(hash(n + 113.0), hash(n + 114.0), f.x),
                               mix(hash(n + 170.0), hash(n + 171.0), f.x), f.y), f.z);
            }
            
            float fbm(vec3 p) {
                float f = 0.0;
                f += 0.50000 * noise(p); p = p * 2.02;
                f += 0.25000 * noise(p); p = p * 2.03;
                f += 0.12500 * noise(p); p = p * 2.01;
                f += 0.06250 * noise(p);
                return f;
            }
            
            void mainImage(out vec4 fragColor, in vec2 fragCoord) {
                vec2 uv = (fragCoord - 0.5 * iResolution.xy) / iResolution.y;
                
                // Camera setup
                float t = iTime * 0.1;
                vec2 m = iMouse.xy / iResolution.xy;
                
                // Mouse control or auto rotate
                float camYaw = (iMouse.x > 0.0) ? m.x * 6.28 : t * 0.5;
                float camPitch = (iMouse.y > 0.0) ? (m.y - 0.5) * 3.14 : 0.2;
                
                vec3 ro = vec3(0.0, 1.5, -8.0); // Origin
                // Rotate camera
                ro.yz *= rotate(camPitch);
                ro.xz *= rotate(camYaw);
                
                vec3 ta = vec3(0.0, 0.0, 0.0); // Target
                vec3 fwd = normalize(ta - ro);
                vec3 right = normalize(cross(vec3(0, 1, 0), fwd));
                vec3 up = cross(fwd, right);
                vec3 rd = normalize(fwd + uv.x * right + uv.y * up);
                
                vec3 col = vec3(0.0);
                
                // Raymarching
                vec3 p = ro;
                float totDist = 0.0;
                
                // Black hole properties
                float bhRadius = 1.0;
                float diskInner = 1.5;
                float diskOuter = 6.0;
                
                for(int i=0; i<100; i++) {
                    float r = length(p);
                    
                    // Gravitational Lensing (Approximate)
                    // Pull ray towards center
                    vec3 force = -normalize(p) * (bhRadius * bhRadius * 1.5) / (r * r + 0.1);
                    rd += force * 0.05;
                    rd = normalize(rd);
                    
                    float stepSize = max(0.05, r * 0.05);
                    p += rd * stepSize;
                    totDist += stepSize;
                    
                    // Event Horizon
                    if(r < bhRadius) {
                        col = vec3(0.0);
                        break;
                    }
                    
                    // Accretion Disk (Volumetric-ish)
                    // Disk lies on XZ plane (y=0)
                    float distToPlane = abs(p.y);
                    if(distToPlane < 0.5 && r > diskInner && r < diskOuter) {
                        float density = (1.0 - distToPlane / 0.5) * smoothstep(diskOuter, diskOuter - 1.0, r) * smoothstep(diskInner, diskInner + 0.5, r);
                        
                        // Rotate texture
                        float angle = atan(p.z, p.x);
                        float speed = 2.0 / sqrt(r);
                        float turb = fbm(vec3(p.x, p.z, iTime * speed + angle * 2.0));
                        
                        density *= turb * 1.5;
                        
                        // Color: Orange/Gold core, fading to red/white
                        vec3 diskCol = mix(vec3(1.0, 0.1, 0.0), vec3(1.0, 0.8, 0.4), density);
                        
                        // Add glow
                        col += diskCol * density * 0.05 * (1.0 / (r * r));
                    }
                    
                    if(totDist > 20.0) {
                         // Refined Exquisite Stars - Deep Black Background
                         vec3 sDir = normalize(rd);
                         vec3 starColorTotal = vec3(0.0);
                         
                         // 1. Background Dust (Very faint, high density)
                         float nDust = noise(sDir * 300.0);
                         starColorTotal += vec3(0.6, 0.7, 0.9) * pow(max(0.0, nDust), 80.0) * 0.4;
                         
                         // 2. Small Sharp Stars (White/Blueish)
                         float nSmall = noise(sDir * 200.0 + vec3(13.5));
                         starColorTotal += vec3(0.9, 0.95, 1.0) * pow(max(0.0, nSmall), 120.0) * 0.8;
                         
                         // 3. Medium Bright Stars
                         float nMed = noise(sDir * 100.0 + vec3(44.2));
                         float valMed = pow(max(0.0, nMed), 150.0);
                         if (valMed > 0.0) {
                             vec3 medCol = mix(vec3(1.0), vec3(0.5, 0.8, 1.0), noise(sDir*10.0));
                             starColorTotal += medCol * valMed * 1.5;
                         }

                         // 4. Rare Bright Stars with Diffraction Spikes (Simulated by glare)
                         float nLarge = noise(sDir * 40.0 + vec3(99.9));
                         float valLarge = pow(max(0.0, nLarge), 200.0);
                         if (valLarge > 0.001) {
                             // Color variation for bright stars (Blue giants vs Red dwarfs)
                             float tempNoise = noise(sDir * 5.0);
                             vec3 starTemp = mix(vec3(0.8, 0.9, 1.0), vec3(1.0, 0.7, 0.5), tempNoise);
                             
                             // Twinkle
                             float twinkle = 0.7 + 0.3 * sin(iTime * 3.0 + nLarge * 50.0);
                             
                             starColorTotal += starTemp * valLarge * 2.5 * twinkle;
                         }
                         
                         // No nebula - pure black space
                         col += starColorTotal;
                         break;
                    }
                }
                
                // Tonemapping
                col = vec3(1.0) - exp(-col * 2.0);
                col = pow(col, vec3(0.4545)); // Gamma correction
                
                fragColor = vec4(col, 1.0);
            }
            
            void main() {
                mainImage(fragColor, gl_FragCoord.xy);
                fragColor.a = 1.0;
            }
            """;

    public static final String RAINBOW = """
            #version 150
            
            uniform float time;
            uniform vec2 resolution;
            uniform vec2 mouse;
            
            out vec4 fragColor;
            
            #define iTime time
            #define iResolution vec3(resolution, 0.0)
            #define iMouse vec4(mouse * resolution, 0.0, 0.0)
            
            // --- 在这里粘贴 Shadertoy 代码 (mainImage 函数) ---
            
            // 示例：简单的彩虹等离子效果
            void mainImage( out vec4 fragColor, in vec2 fragCoord )
            {
                vec2 uv = fragCoord/iResolution.xy;
                vec3 col = 0.5 + 0.5*cos(iTime+uv.xyx+vec3(0,2,4));
                fragColor = vec4(col,1.0);
            }
            
            // ------------------------------------------------
            
            void main() {
                mainImage(fragColor, gl_FragCoord.xy);
                fragColor.a = 1.0;
            }
            """;

    public static final String GALAXY = """
            #version 150
            
            uniform float time;
            uniform vec2 resolution;
            uniform vec2 mouse;
            
            out vec4 fragColor;
            
            #define iTime time
            #define iResolution vec3(resolution, 0.0)
            #define iMouse vec4(mouse * resolution, 0.0, 0.0)

            // Star Nest by Pablo Roman Andrioli (https://www.shadertoy.com/view/XlfGRj)
            // MIT License

            #define iterations 17
            #define formuparam 0.53

            #define volsteps 20
            #define stepsize 0.1

            #define zoom   0.800
            #define tile   0.850
            #define speed  0.010 

            #define brightness 0.0015
            #define darkmatter 0.300
            #define distfading 0.730
            #define saturation 0.850


            void mainImage( out vec4 fragColor, in vec2 fragCoord )
            {
                //get coords and direction
                vec2 uv=fragCoord.xy/iResolution.xy-.5;
                uv.y*=iResolution.y/iResolution.x;
                vec3 dir=vec3(uv*zoom,1.);
                float time=iTime*speed+.25;

                //mouse rotation
                float a1=.5+iMouse.x/iResolution.x*2.;
                float a2=.8+iMouse.y/iResolution.y*2.;
                mat2 rot1=mat2(cos(a1),sin(a1),-sin(a1),cos(a1));
                mat2 rot2=mat2(cos(a2),sin(a2),-sin(a2),cos(a2));
                dir.xz*=rot1;
                dir.xy*=rot2;
                vec3 from=vec3(1.,.5,0.5);
                from+=vec3(time*2.,time,-2.);
                from.xz*=rot1;
                from.xy*=rot2;
                
                //volumetric rendering
                float s=0.1,fade=1.;
                vec3 v=vec3(0.);
                for (int r=0; r<volsteps; r++) {
                    vec3 p=from+s*dir*.5;
                    p = abs(vec3(tile)-mod(p,vec3(tile*2.))); // tiling fold
                    float pa,a=pa=0.;
                    for (int i=0; i<iterations; i++) { 
                        p=abs(p)/dot(p,p)-formuparam; // the magic formula
                        a+=abs(length(p)-pa); // absolute sum of average change
                        pa=length(p);
                    }
                    float dm=max(0.,darkmatter-a*a*.001); //dark matter
                    a*=a*a; // add contrast
                    if (r>6) fade*=1.-dm; // dark matter, don't render near
                    //v+=vec3(dm,dm*.5,0.);
                    v+=fade;
                    v+=vec3(s,s*s,s*s*s*s)*a*brightness*fade; // coloring based on distance
                    fade*=distfading; // distance fading
                    s+=stepsize;
                }
                v=mix(vec3(length(v)),v,saturation); //color adjust
                fragColor = vec4(v*.01,1.);	
            }
            
            void main() {
                mainImage(fragColor, gl_FragCoord.xy);
                fragColor.a = 1.0;
            }
            """;

    public static final String BLACK_HOLE_2 = """
            #version 150
            
            uniform float time;
            uniform vec2 resolution;
            uniform vec2 mouse;
            
            out vec4 fragColor;
            
            #define iTime time
            #define iResolution vec3(resolution, 0.0)
            #define iMouse vec4(mouse * resolution, 0.0, 0.0)

            /*
             "Singularity" by @XorDev
             A whirling blackhole.
             Modified for deep purple theme.
            */
            
            float hash(vec2 p) { return fract(sin(dot(p, vec2(123.4, 789.0)))* 43758.5453); }

            vec3 getStars(vec2 uv) {
                vec3 col = vec3(0.0);
                float t = iTime * 0.15;
                
                for(float i=0.; i<3.; i++) {
                    vec2 scale = uv * (12.0 + i * 10.0);
                    vec2 id = floor(scale);
                    vec2 gv = fract(scale) - 0.5;
                    
                    float n = hash(id + i * 55.1);
                    
                    if(n > 0.98) { 
                         float d = length(gv);
                         float brightness = smoothstep(0.1, 0.0, d);
                         brightness *= (0.5 + 0.5 * sin(t + n * 6.28));
                         col += vec3(brightness);
                    }
                }
                return col;
            }

            void mainImage(out vec4 O, vec2 F)
            {
                //Iterator and attenuation (distance-squared)
                float i = .2, a;
                
                //Resolution for scaling and centering
                vec2 r = iResolution.xy,
                
                //Centered ratio-corrected coordinates
                p = ( F+F - r ) / r.y / .7,
                
                //Diagonal vector for skewing
                d = vec2(-1,1),
                
                //Blackhole center
                b = p - i*d,
                
                //Rotate and apply perspective
                c = p * mat2(1, 1, d/(.1 + i/dot(b,b))),
                
                //Rotate into spiraling coordinates
                v = c * mat2(cos(.5*log(a=dot(c,c)) + iTime*i + vec4(0,33,11,0)))/i,
                
                //Waves cumulative total for coloring
                w;
                
                //Loop through waves
                for(; i++<9.; w += 1.+sin(v) )
                
                //Distort coordinates
                v += .7* sin(v.yx*i+iTime) / i + .5;
                
                //Acretion disk radius
                i = length( sin(v/.3)*.4 + c*(3.+d) );
                
                // Dark Blue and Light Blue Gradient
                vec4 mask = 1. - exp( -exp( c.x * vec4(0.6, -0.6, 0.0, 0.0) )
                
                //Wave coloring 
                /  w.xyyx 
                
                //Acretion disk brightness 
                / ( 2. + i*i/4. - i ) 
                
                //Center darkness 
                / ( .5 + 1. / a ) 
                
                //Rim highlight 
                / ( .03 + abs( length(p)-.7 ) ) 
                
                );

                vec3 darkBlue = vec3(0.05, 0.1, 0.5); // Deep Blue
                vec3 lightBlue = vec3(0.2, 0.8, 1.0); // Cyan/Light Blue
                
                vec3 nebula = mask.r * lightBlue + mask.g * darkBlue;
                vec3 stars = getStars(p);
                
                // Mask stars:
                // 1. Center hole (Event Horizon): smoothstep based on distance to center 'p'
                // The rim highlight is at length(p) ~ 0.7, so the hole is inside that radius.
                float hole = smoothstep(0.6, 0.75, length(p));
                
                // 2. Nebula brightness: stars should be obscured by the bright gas
                float brightness = dot(nebula, vec3(0.33));
                float occlusion = 1.0 - smoothstep(0.1, 0.8, brightness);
                
                O = vec4(nebula + stars * hole * occlusion, 1.0);
            }
            
            void main() {
                mainImage(fragColor, gl_FragCoord.xy);
                fragColor.a = 1.0;
            }
            """;
}