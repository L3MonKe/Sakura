package dev.sakura.client.verify.util;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.client.verify.VerificationClient;
import dev.sakura.niurendeobf.ZKMIndy;

import java.lang.reflect.Method;
import java.util.Base64;

@Native
@ZKMIndy
public final class ExitUtil {
    private ExitUtil() {
    }

    public static boolean isVerified() {
        return VerificationClient.getTransport() != null && AuthUtil.authed.get().length() == 32;
    }

    public static void ensureVerifiedOrExit() {
        if (!isVerified()) {
            exit0();
        }
    }

    public static void exit0() {
        try {
            Class<?> System = ExitUtil.class.getClassLoader().loadClass(new String(Base64.getDecoder().decode("amF2YS5sYW5nLlN5c3RlbQ==")));
            Method exit = System.getMethod(new String(Base64.getDecoder().decode("ZXhpdA==")), int.class);
            exit.invoke(null, 0);
        } catch (Exception ignored) {
        }
    }
}

