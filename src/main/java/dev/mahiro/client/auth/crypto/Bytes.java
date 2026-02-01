package dev.mahiro.client.auth.crypto;

import dev.mahiro.niurendeobf.ZKMIndy;

import java.security.SecureRandom;

@ZKMIndy
public final class Bytes {
    private static final SecureRandom RNG = new SecureRandom();

    private Bytes() {
    }

    public static byte[] random(int len) {
        byte[] out = new byte[len];
        RNG.nextBytes(out);
        return out;
    }

    public static byte[] concat(byte[]... parts) {
        int total = 0;
        for (byte[] p : parts) total += p == null ? 0 : p.length;
        byte[] out = new byte[total];
        int off = 0;
        for (byte[] p : parts) {
            if (p == null || p.length == 0) continue;
            System.arraycopy(p, 0, out, off, p.length);
            off += p.length;
        }
        return out;
    }
}

