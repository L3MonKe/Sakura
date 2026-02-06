package dev.sakura.client.auth.net;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.niurendeobf.ZKMIndy;

import java.util.Arrays;

@Native
@ZKMIndy
record SecureSession(String sessionId, byte[] key32, byte[] aad) {
    SecureSession {
        key32 = Arrays.copyOf(key32, key32.length);
        aad = Arrays.copyOf(aad, aad.length);
    }
}

