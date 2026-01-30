package dev.mahiro.client.auth.crypto;

import dev.mahiro.obf.ZKMIndy;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

@ZKMIndy
public final class X25519 {
    private X25519() {
    }

    public static KeyPair generate() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("X25519");
            return kpg.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static PublicKey decodePublicX509(byte[] x509) {
        try {
            KeyFactory kf = KeyFactory.getInstance("X25519");
            return kf.generatePublic(new X509EncodedKeySpec(x509));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] agree(PrivateKey priv, PublicKey pub) {
        try {
            KeyAgreement ka = KeyAgreement.getInstance("X25519");
            ka.init(priv);
            ka.doPhase(pub, true);
            byte[] secret = ka.generateSecret();
            return Arrays.copyOf(secret, secret.length);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

