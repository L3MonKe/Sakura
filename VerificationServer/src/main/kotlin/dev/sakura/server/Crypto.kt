package dev.sakura.server

import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.time.Instant
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

internal val JSON = kotlinx.serialization.json.Json {
    ignoreUnknownKeys = true
    encodeDefaults = false
    explicitNulls = false
}

internal val B64: Base64.Encoder = Base64.getEncoder()
internal val B64D: Base64.Decoder = Base64.getDecoder()
internal val RNG = SecureRandom()

internal data class SigningKeys(
    val privateKey: PrivateKey,
    val publicKey: PublicKey
)

internal data class SecureSession(
    val sid: String,
    val key32: ByteArray,
    val createdAt: Long
)

internal fun randomBytes(n: Int): ByteArray = ByteArray(n).also { RNG.nextBytes(it) }

internal fun concat(vararg parts: ByteArray): ByteArray {
    val out = ByteArray(parts.sumOf { it.size })
    var off = 0
    for (p in parts) {
        System.arraycopy(p, 0, out, off, p.size)
        off += p.size
    }
    return out
}

internal fun x25519Generate(): KeyPair = KeyPairGenerator.getInstance("X25519").generateKeyPair()

internal fun x25519DecodePublicX509(x509: ByteArray): PublicKey =
    KeyFactory.getInstance("X25519").generatePublic(X509EncodedKeySpec(x509))

internal fun x25519Agree(priv: PrivateKey, pub: PublicKey): ByteArray {
    val ka = javax.crypto.KeyAgreement.getInstance("X25519")
    ka.init(priv)
    ka.doPhase(pub, true)
    return ka.generateSecret()
}

internal fun hkdfSha256(ikm: ByteArray, salt: ByteArray, info: ByteArray, len: Int): ByteArray {
    val prk = hmacSha256(salt, ikm)
    val out = ByteArray(len)
    var t = ByteArray(0)
    var off = 0
    var c = 1
    while (off < len) {
        val input = concat(t, info, byteArrayOf(c.toByte()))
        t = hmacSha256(prk, input)
        val copy = minOf(t.size, len - off)
        System.arraycopy(t, 0, out, off, copy)
        off += copy
        c++
    }
    return out
}

internal fun hmacSha256(key: ByteArray, data: ByteArray): ByteArray {
    val mac = Mac.getInstance("HmacSHA256")
    mac.init(SecretKeySpec(key, "HmacSHA256"))
    return mac.doFinal(data)
}

internal fun aesGcmEncrypt(key32: ByteArray, plaintext: ByteArray, aad: ByteArray): Pair<ByteArray, ByteArray> {
    val iv = randomBytes(12)
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key32, "AES"), GCMParameterSpec(128, iv))
    cipher.updateAAD(aad)
    val ct = cipher.doFinal(plaintext)
    return iv to ct
}

internal fun aesGcmDecrypt(key32: ByteArray, iv: ByteArray, ciphertext: ByteArray, aad: ByteArray): ByteArray {
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key32, "AES"), GCMParameterSpec(128, iv))
    cipher.updateAAD(aad)
    return cipher.doFinal(ciphertext)
}

internal fun ed25519Sign(priv: PrivateKey, msg: ByteArray): ByteArray {
    val s = Signature.getInstance("Ed25519")
    s.initSign(priv)
    s.update(msg)
    return s.sign()
}

internal fun ed25519Verify(pub: PublicKey, msg: ByteArray, sig: ByteArray): Boolean {
    return try {
        val s = Signature.getInstance("Ed25519")
        s.initVerify(pub)
        s.update(msg)
        s.verify(sig)
    } catch (_: Exception) {
        false
    }
}

internal fun loadSigningKeys(privatePkcs8B64: String?, publicX509B64: String?): SigningKeys {
    if (!privatePkcs8B64.isNullOrBlank() && !publicX509B64.isNullOrBlank()) {
        val priv =
            KeyFactory.getInstance("Ed25519").generatePrivate(PKCS8EncodedKeySpec(B64D.decode(privatePkcs8B64.trim())))
        val pub =
            KeyFactory.getInstance("Ed25519").generatePublic(X509EncodedKeySpec(B64D.decode(publicX509B64.trim())))
        return SigningKeys(priv, pub)
    }
    if (!privatePkcs8B64.isNullOrBlank() && publicX509B64.isNullOrBlank()) {
        throw IllegalStateException("AUTH_SIGNING_PUBLIC_KEY_X509_B64 is required when private key is provided")
    }
    if (privatePkcs8B64.isNullOrBlank() && !publicX509B64.isNullOrBlank()) {
        throw IllegalStateException("AUTH_SIGNING_PRIVATE_KEY_PKCS8_B64 is required when public key is provided")
    }
    val kp = KeyPairGenerator.getInstance("Ed25519").generateKeyPair()
    return SigningKeys(kp.private, kp.public)
}

internal fun signToken(priv: PrivateKey, licenseKey: String, expiresAt: Long): String {
    val payload =
        """{"lic":"$licenseKey","exp":$expiresAt,"iat":${Instant.now().epochSecond}}""".toByteArray(StandardCharsets.UTF_8)
    val sig = ed25519Sign(priv, payload)
    val p = Base64.getUrlEncoder().withoutPadding().encodeToString(payload)
    val s = Base64.getUrlEncoder().withoutPadding().encodeToString(sig)
    return "$p.$s"
}

internal fun verifyTokenPayload(pub: PublicKey, token: String): Pair<TokenPayload?, String?> {
    val parts = token.split('.')
    if (parts.size != 2) return null to "INVALID_TOKEN"
    val payload = try {
        Base64.getUrlDecoder().decode(parts[0])
    } catch (_: Exception) {
        return null to "INVALID_TOKEN"
    }
    val sig = try {
        Base64.getUrlDecoder().decode(parts[1])
    } catch (_: Exception) {
        return null to "INVALID_TOKEN"
    }
    val okSig = ed25519Verify(pub, payload, sig)
    if (!okSig) return null to "BAD_SIGNATURE"
    val payloadObj = try {
        JSON.decodeFromString(TokenPayload.serializer(), payload.toString(StandardCharsets.UTF_8))
    } catch (_: Exception) {
        return null to "INVALID_TOKEN"
    }
    return payloadObj to null
}

internal fun normalizeUsername(username: String?): String? {
    val v = username?.replace('\u0000', ' ')?.replace(Regex("\\s+"), " ")?.trim()?.lowercase(Locale.ROOT)
    return if (v.isNullOrBlank()) null else v
}

internal fun normalizeDeviceId(deviceId: String): String {
    val v = deviceId.replace('\u0000', ' ').replace(Regex("\\s+"), " ").trim()
    return v.lowercase(Locale.ROOT)
}

internal fun pbkdf2(password: String, salt: ByteArray, iterations: Int, outLen: Int): ByteArray {
    val spec = PBEKeySpec(password.toCharArray(), salt, iterations, outLen * 8)
    val f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    return f.generateSecret(spec).encoded
}

internal fun constantTimeEq(a: ByteArray, b: ByteArray): Boolean {
    if (a.size != b.size) return false
    var r = 0
    for (i in a.indices) {
        r = r or (a[i].toInt() xor b[i].toInt())
    }
    return r == 0
}

