package com.zeta.client.auth.crypto;

import jnic.JNICInclude;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

@JNICInclude
public final class HkdfSha256 {
    private HkdfSha256() {
    }

    public static byte[] deriveKey(byte[] ikm, byte[] salt, byte[] info, int len) {
        byte[] prk = extract(ikm, salt);
        return expand(prk, info, len);
    }

    private static byte[] extract(byte[] ikm, byte[] salt) {
        byte[] s = salt == null ? new byte[32] : salt;
        return hmac(s, ikm == null ? new byte[0] : ikm);
    }

    private static byte[] expand(byte[] prk, byte[] info, int len) {
        if (len <= 0) throw new IllegalArgumentException("len");
        int hashLen = 32;
        int n = (int) Math.ceil((double) len / (double) hashLen);
        if (n > 255) throw new IllegalArgumentException("len too large");
        byte[] out = new byte[len];
        byte[] t = new byte[0];
        int off = 0;
        for (int i = 1; i <= n; i++) {
            byte[] input = Bytes.concat(t, info == null ? new byte[0] : info, new byte[]{(byte) i});
            t = hmac(prk, input);
            int copy = Math.min(hashLen, len - off);
            System.arraycopy(t, 0, out, off, copy);
            off += copy;
        }
        Arrays.fill(prk, (byte) 0);
        return out;
    }

    private static byte[] hmac(byte[] key, byte[] data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data == null ? new byte[0] : data);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

