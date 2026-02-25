package dev.sakura.client.shaders;

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

    public static final String MOON = """
            #version 150
            
            uniform vec2 resolution;
            uniform float time;
            
            out vec4 fragColor;
            
            #define EPS     .001
            #define PI      3.14159265359
            #define RADIAN  180. / PI
            #define SPEED   25.
            
            float hash(in float n) {
                return fract(sin(n)*43758.5453123);
            }
            
            float hash(vec2 p) {
                return fract(sin(dot(p,vec2(127.1,311.7))) * 43758.5453123);
            }
            
            float noise(vec2 p) {
                vec2 i = floor(p), f = fract(p);\s
                f *= f*(3.-2.*f);
            
                vec2 c = vec2(0,1);
            
                return mix(mix(hash(i + c.xx),\s
                               hash(i + c.yx), f.x),
                           mix(hash(i + c.xy),\s
                               hash(i + c.yy), f.x), f.y);
            }
            
            float fbm(in vec2 p) {
                return  .5000 * noise(p)
                       +.2500 * noise(p * 2.)
                       +.1250 * noise(p * 4.)
                       +.0625 * noise(p * 8.);
            }
            
            float dst(vec3 p) {
                return dot(vec3(p.x, p.y
                                + 0.45 * fbm(p.zx)\s
                                + 2.55 * noise(.1 * p.xz)\s
                                + 0.83 * noise(.4 * p.xz)
                                + 3.33 * noise(.001 * p.xz)
                                + 3.59 * noise(.0005 * (p.xz + 132.453))\s
                                , p.z),  vec3(0.,1.,0.));   \s
            }
            
            vec3 nrm(vec3 p, float d) {
                return normalize(
                        vec3(dst(vec3(p.x + EPS, p.y, p.z)),
                             dst(vec3(p.x, p.y + EPS, p.z)),
                             dst(vec3(p.x, p.y, p.z + EPS))) - d);
            }
            
            bool rmarch(vec3 ro, vec3 rd, out vec3 p, out vec3 n) {
                p = ro;
                vec3 pos = p;
                float d = 1.;
            
                for (int i = 0; i < 64; i++) {
                    d = dst(pos);
            
                    if (d < EPS) {
                        p = pos;
                        break;
                    }
                    pos += d * rd;
                }
            
                n = nrm(p, d);
                return d < EPS;
            }
            
            vec4 render(vec2 uv) {
                float t = time;
            
                vec2 uvn = (uv) * vec2(resolution.x / resolution.y, 1.);
            
                float vel = SPEED * t;
            
                vec3 cu = vec3(2. * noise(vec2(.3 * t)) - 1.,1., 1. * fbm(vec2(.8 * t)));
                vec3 cp = vec3(0, 3.1 + noise(vec2(t)) * 3.1, vel);
                vec3 ct = vec3(1.5 * sin(t),\s
                               -2. + cos(t) + fbm(cp.xz) * .4, 13. + vel);
            
                vec3 ro = cp,
                     rd = normalize(vec3(uvn, 1. / tan(60. * RADIAN)));
            
                vec3 cd = ct - cp,
                     rz = normalize(cd),
                     rx = normalize(cross(rz, cu)),
                     ry = normalize(cross(rx, rz));
            
                rd = normalize(mat3(rx, ry, rz) * rd);
            
            
                vec3 sp, sn;
                vec3 col = (rmarch(ro, rd, sp, sn) ?
                      vec3(.6) * dot(sn, normalize(vec3(cp.x, cp.y + .5, cp.z) - sp))
                    : vec3(0.));
            
                return vec4(col, length(ro-sp));
            }
            
            void main() {
                vec2 fragCoord = gl_FragCoord.xy;
                vec2 uv = fragCoord.xy / resolution.xy * 2. - 1.;
            
                vec4 res = render(uv);
            
                vec3 col = res.xyz;
            
                col *= 1.75 * smoothstep(length(uv) * .35, .75, .4);
                float n = hash((hash(uv.x) + uv.y) * time) * .15;
                col += n;
                col *= smoothstep(EPS, 3.5, time);
            
                fragColor = vec4(col, 1);
            }
            
            """;

    public static final String SEA = """
            #version 150
            
            uniform vec2 resolution;
            uniform float time;
            
            out vec4 fragColor;
            
            const int NUM_STEPS = 32;
            const float PI	 	= 3.141592;
            const float EPSILON	= 1e-3;
            #define EPSILON_NRM (0.1 / resolution.x)
            
            const int ITER_GEOMETRY = 3;
            const int ITER_FRAGMENT = 5;
            const float SEA_HEIGHT = 0.6;
            const float SEA_CHOPPY = 4.0;
            const float SEA_SPEED = 0.8;
            const float SEA_FREQ = 0.16;
            const vec3 SEA_BASE = vec3(0.0,0.09,0.18);
            const vec3 SEA_WATER_COLOR = vec3(0.8,0.9,0.6)*0.6;
            #define SEA_TIME (1.0 + time * SEA_SPEED)
            const mat2 octave_m = mat2(1.6,1.2,-1.2,1.6);
            
            mat3 fromEuler(vec3 ang) {
            	vec2 a1 = vec2(sin(ang.x),cos(ang.x));
                vec2 a2 = vec2(sin(ang.y),cos(ang.y));
                vec2 a3 = vec2(sin(ang.z),cos(ang.z));
                mat3 m;
                m[0] = vec3(a1.y*a3.y+a1.x*a2.x*a3.x,a1.y*a2.x*a3.x+a3.y*a1.x,-a2.y*a3.x);
            	m[1] = vec3(-a2.y*a1.x,a1.y*a2.y,a2.x);
            	m[2] = vec3(a3.y*a1.x*a2.x+a1.y*a3.x,a1.x*a3.x-a1.y*a3.y*a2.x,a2.y*a3.y);
            	return m;
            }
            float hash( vec2 p ) {
            	float h = dot(p,vec2(127.1,311.7));
                return fract(sin(h)*43758.5453123);
            }
            float noise( in vec2 p ) {
                vec2 i = floor( p );
                vec2 f = fract( p );
            	vec2 u = f*f*(3.0-2.0*f);
                return -1.0+2.0*mix( mix( hash( i + vec2(0.0,0.0) ),
                                 hash( i + vec2(1.0,0.0) ), u.x),
                            mix( hash( i + vec2(0.0,1.0) ),
                                 hash( i + vec2(1.0,1.0) ), u.x), u.y);
            }
            
            float diffuse(vec3 n,vec3 l,float p) {
                return pow(dot(n,l) * 0.4 + 0.6,p);
            }
            float specular(vec3 n,vec3 l,vec3 e,float s) {
                float nrm = (s + 8.0) / (PI * 8.0);
                return pow(max(dot(reflect(e,n),l),0.0),s) * nrm;
            }
            
            vec3 getSkyColor(vec3 e) {
                e.y = (max(e.y,0.0)*0.8+0.2)*0.8;
                return vec3(pow(1.0-e.y,2.0), 1.0-e.y, 0.6+(1.0-e.y)*0.4) * 1.1;
            }
            
            float sea_octave(vec2 uv, float choppy) {
                uv += noise(uv);
                vec2 wv = 1.0-abs(sin(uv));
                vec2 swv = abs(cos(uv));
                wv = mix(wv,swv,wv);
                return pow(1.0-pow(wv.x * wv.y,0.65),choppy);
            }
            
            float map(vec3 p) {
                float freq = SEA_FREQ;
                float amp = SEA_HEIGHT;
                float choppy = SEA_CHOPPY;
                vec2 uv = p.xz; uv.x *= 0.75;
            
                float d, h = 0.0;
                for(int i = 0; i < ITER_GEOMETRY; i++) {
                	d = sea_octave((uv+SEA_TIME)*freq,choppy);
                	d += sea_octave((uv-SEA_TIME)*freq,choppy);
                    h += d * amp;
                	uv *= octave_m; freq *= 1.9; amp *= 0.22;
                    choppy = mix(choppy,1.0,0.2);
                }
                return p.y - h;
            }
            
            float map_detailed(vec3 p) {
                float freq = SEA_FREQ;
                float amp = SEA_HEIGHT;
                float choppy = SEA_CHOPPY;
                vec2 uv = p.xz; uv.x *= 0.75;
            
                float d, h = 0.0;
                for(int i = 0; i < ITER_FRAGMENT; i++) {
                	d = sea_octave((uv+SEA_TIME)*freq,choppy);
                	d += sea_octave((uv-SEA_TIME)*freq,choppy);
                    h += d * amp;
                	uv *= octave_m; freq *= 1.9; amp *= 0.22;
                    choppy = mix(choppy,1.0,0.2);
                }
                return p.y - h;
            }
            
            vec3 getSeaColor(vec3 p, vec3 n, vec3 l, vec3 eye, vec3 dist) {
                float fresnel = clamp(1.0 - dot(n, -eye), 0.0, 1.0);
                fresnel = min(fresnel * fresnel * fresnel, 0.5);
            
                vec3 reflected = getSkyColor(reflect(eye, n));
                vec3 refracted = SEA_BASE + diffuse(n, l, 80.0) * SEA_WATER_COLOR * 0.12;
            
                vec3 color = mix(refracted, reflected, fresnel);
            
                float atten = max(1.0 - dot(dist, dist) * 0.001, 0.0);
                color += SEA_WATER_COLOR * (p.y - SEA_HEIGHT) * 0.18 * atten;
            
                color += specular(n, l, eye, 600.0 * inversesqrt(dot(dist,dist)));
            
                return color;
            }
            
            vec3 getNormal(vec3 p, float eps) {
                vec3 n;
                n.y = map_detailed(p);
                n.x = map_detailed(vec3(p.x+eps,p.y,p.z)) - n.y;
                n.z = map_detailed(vec3(p.x,p.y,p.z+eps)) - n.y;
                n.y = eps;
                return normalize(n);
            }
            
            float heightMapTracing(vec3 ori, vec3 dir, out vec3 p) {
                float tm = 0.0;
                float tx = 1000.0;
                float hx = map(ori + dir * tx);
                if(hx > 0.0) {
                    p = ori + dir * tx;
                    return tx;
                }
                float hm = map(ori);
                for(int i = 0; i < NUM_STEPS; i++) {
                    float tmid = mix(tm, tx, hm / (hm - hx));
                    p = ori + dir * tmid;
                    float hmid = map(p);
                    if(hmid < 0.0) {
                        tx = tmid;
                        hx = hmid;
                    } else {
                        tm = tmid;
                        hm = hmid;
                    }
                    if(abs(hmid) < EPSILON) break;
                }
                return mix(tm, tx, hm / (hm - hx));
            }
            
            vec3 getPixel(in vec2 coord, float t) {
                vec2 uv = coord / resolution.xy;
                uv = uv * 2.0 - 1.0;
                uv.x *= resolution.x / resolution.y;
            
                vec3 ang = vec3(sin(t*3.0)*0.1,sin(t)*0.2+0.3,t);
                vec3 ori = vec3(0.0,3.5,t*5.0);
                vec3 dir = normalize(vec3(uv.xy,-2.0)); dir.z += length(uv) * 0.14;
                dir = normalize(dir) * fromEuler(ang);
            
                vec3 p;
                heightMapTracing(ori,dir,p);
                vec3 dist = p - ori;
                vec3 n = getNormal(p, dot(dist,dist) * EPSILON_NRM);
                vec3 light = normalize(vec3(0.0,1.0,0.8));
            
                return mix(
                    getSkyColor(dir),
                    getSeaColor(p,n,light,dir,dist),
                	pow(smoothstep(0.0,-0.02,dir.y),0.2));
            }
            
            void main() {
                vec2 fragCoord = gl_FragCoord.xy;
                float t = time * 0.3;
            
            #ifdef AA
                vec3 color = vec3(0.0);
                for(int i = -1; i <= 1; i++) {
                    for(int j = -1; j <= 1; j++) {
                    	vec2 uv = fragCoord+vec2(i,j)/3.0;
                		color += getPixel(uv, t);
                    }
                }
                color /= 9.0;
            #else
                vec3 color = getPixel(fragCoord, t);
            #endif
            
            	fragColor = vec4(pow(color,vec3(0.65)), 1.0);
            }
            
            """;

}