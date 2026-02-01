package dev.mahiro.client.auth.crypto;

import dev.mahiro.niurendeobf.ZKMIndy;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

@ZKMIndy
public final class AesGcm {
    private AesGcm() {
    }

    public static Encrypted encrypt(byte[] key32, byte[] plaintext, byte[] aad) {
        try {
            byte[] iv = Bytes.random(12);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec key = new SecretKeySpec(key32, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));
            if (aad != null && aad.length > 0) cipher.updateAAD(aad);
            byte[] ct = cipher.doFinal(plaintext == null ? new byte[0] : plaintext);
            return new Encrypted(iv, ct);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] decrypt(byte[] key32, byte[] iv12, byte[] ciphertext, byte[] aad) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec key = new SecretKeySpec(key32, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, iv12));
            if (aad != null && aad.length > 0) cipher.updateAAD(aad);
            return cipher.doFinal(ciphertext == null ? new byte[0] : ciphertext);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public record Encrypted(byte[] iv, byte[] ciphertext) {
        public Encrypted {
            iv = Arrays.copyOf(iv, iv.length);
            ciphertext = Arrays.copyOf(ciphertext, ciphertext.length);
        }
    }
}

