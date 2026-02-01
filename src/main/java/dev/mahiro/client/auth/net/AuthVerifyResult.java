package dev.mahiro.client.auth.net;

import dev.mahiro.niurendeobf.ZKMIndy;

import java.util.Objects;

@ZKMIndy
public record AuthVerifyResult(boolean ok, String token, String error) {
    public static AuthVerifyResult ok(String token) {
        return new AuthVerifyResult(true, Objects.requireNonNullElse(token, ""), "");
    }

    public static AuthVerifyResult fail(String error) {
        return new AuthVerifyResult(false, "", Objects.requireNonNullElse(error, "UNKNOWN"));
    }
}
