package dev.sakura.client.auth.net;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.niurendeobf.ZKMIndy;

import java.util.Objects;

@Native
@ZKMIndy
public record AuthVerifyResult(boolean ok, String token, String error) {
    public static AuthVerifyResult ok(String token) {
        return new AuthVerifyResult(true, Objects.requireNonNullElse(token, ""), "");
    }

    public static AuthVerifyResult fail(String error) {
        return new AuthVerifyResult(false, "", Objects.requireNonNullElse(error, "UNKNOWN"));
    }
}
