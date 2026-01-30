package dev.mahiro.client.auth.net;

import dev.mahiro.obf.ZKMIndy;

import java.util.Arrays;

@ZKMIndy
record SecureSession(String sessionId, byte[] key32, byte[] aad) {
    SecureSession {
        key32 = Arrays.copyOf(key32, key32.length);
        aad = Arrays.copyOf(aad, aad.length);
    }
}

