package com.zeta.client.auth.crypto;

import jnic.JNICInclude;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

@JNICInclude
public final class Ed25519 {
    private Ed25519() {
    }

    public static PublicKey decodePublicX509(byte[] x509) {
        try {
            KeyFactory kf = KeyFactory.getInstance("Ed25519");
            return kf.generatePublic(new X509EncodedKeySpec(x509));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean verify(PublicKey pub, byte[] msg, byte[] sig) {
        try {
            Signature s = Signature.getInstance("Ed25519");
            s.initVerify(pub);
            s.update(msg);
            return s.verify(sig);
        } catch (Exception e) {
            return false;
        }
    }
}

