package dev.mahiro.client.auth.crypto;

import dev.mahiro.obf.ZKMIndy;

import java.util.Base64;

@ZKMIndy
public final class B64 {
    private B64() {
    }

    public static String enc(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] dec(String b64) {
        return Base64.getDecoder().decode(b64);
    }
}

