package dev.sakura.server

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HandshakeRequest(
    val v: String = "v1",
    val clientEphemeralPub: String,
    val nonce: String
)

@Serializable
internal data class HandshakeResponse(
    val sid: String,
    val serverEphemeralPub: String,
    val nonce: String,
    val sig: String
)

@Serializable
internal data class EncryptedRequest(
    val sid: String,
    val iv: String,
    val ct: String
)

@Serializable
internal data class EncryptedResponse(
    val iv: String,
    val ct: String
)

@Serializable
internal data class UserRegisterPlainRequest(
    val username: String,
    val password: String,
    val licenseKey: String,
    val deviceId: String,
    val clientVersion: String? = null,
    val ts: Long
)

@Serializable
internal data class UserLoginPlainRequest(
    val username: String,
    val password: String,
    val deviceId: String,
    val clientVersion: String? = null,
    val ts: Long
)

@Serializable
internal data class UserAuthPlainResponse(
    val ok: Boolean,
    val error: String? = null,
    val token: String? = null,
    val licenseKey: String? = null,
    val expiresAt: Long? = null
)

@Serializable
internal data class TokenVerifyPlainRequest(
    val token: String,
    val deviceId: String? = null,
    val ts: Long
)

@Serializable
internal data class TokenVerifyPlainResponse(
    val ok: Boolean,
    val error: String? = null,
    val licenseKey: String? = null,
    val expiresAt: Long? = null
)

@Serializable
internal data class TokenPayload(
    @SerialName("lic") val licenseKey: String,
    @SerialName("exp") val expiresAt: Long,
    @SerialName("iat") val issuedAt: Long
)

@Serializable
internal data class UserRecord(
    val id: Long,
    val username: String,
    val saltB64: String,
    val hashB64: String,
    val iterations: Int,
    val createdAt: Long
)

@Serializable
internal data class LicenseRecord(
    val key: String,
    val status: String,
    val userId: Long? = null,
    val validDays: Int,
    val createdAt: Long,
    val activatedAt: Long? = null,
    val expiresAt: Long? = null
)

@Serializable
internal data class BindingRecord(
    val licenseKey: String,
    val saltB64: String,
    val hashB64: String,
    val iterations: Int,
    val createdAt: Long
)
