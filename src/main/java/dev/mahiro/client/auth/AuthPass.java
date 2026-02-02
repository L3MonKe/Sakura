package dev.mahiro.client.auth;

import by.radioegor146.nativeobfuscator.Native;
import dev.mahiro.niurendeobf.ZKMIndy;

@Native
@ZKMIndy
public record AuthPass(String token, long expiresAtMillis) {
}
