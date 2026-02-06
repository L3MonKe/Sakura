package dev.sakura.client.auth;

import by.radioegor146.nativeobfuscator.Native;
import dev.sakura.niurendeobf.ZKMIndy;

@Native
@ZKMIndy
public record AuthPass(String token, long expiresAtMillis) {
}
