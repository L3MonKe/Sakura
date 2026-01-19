package dev.lemonclient.client.auth.net;

import java.util.Arrays;

record SecureSession(String sessionId, byte[] key32, byte[] aad) {
    SecureSession {
        key32 = Arrays.copyOf(key32, key32.length);
        aad = Arrays.copyOf(aad, aad.length);
    }
}

