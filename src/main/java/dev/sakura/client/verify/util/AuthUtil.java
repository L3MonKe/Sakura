package dev.sakura.client.verify.util;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.niurendeobf.ZKMIndy;

import java.util.concurrent.atomic.AtomicReference;

@Native
@ZKMIndy
public final class AuthUtil {
    public static final AtomicReference<String> authed = new AtomicReference<>("");
    public static final String AUTH_OK_TOKEN = "SakuraVerifyToken0123456789ABCDE";
}
