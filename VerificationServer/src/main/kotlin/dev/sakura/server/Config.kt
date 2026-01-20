package dev.sakura.server

import java.util.*

internal data class ServerConfig(
    val host: String,
    val port: Int,
    val dataDir: String,
    val requireTls: Boolean,
    val requireClientAuth: Boolean,
    val keystorePath: String?,
    val keystorePassword: String?,
    val truststorePath: String?,
    val truststorePassword: String?,
    val signingKeyPkcs8B64: String?,
    val signingPublicKeyX509B64: String?
)

internal fun loadConfig(): ServerConfig {
    fun env(name: String): String? =
        System.getenv(name)?.takeIf { it.isNotBlank() } ?: System.getProperty(name)?.takeIf { it.isNotBlank() }

    val host = env("AUTH_HOST") ?: "0.0.0.0"
    val port = (env("AUTH_PORT") ?: "8443").toInt()
    val dataDir = env("AUTH_DATA_DIR") ?: "data"
    val requireTls = (env("AUTH_REQUIRE_TLS") ?: "false").lowercase(Locale.ROOT) == "true"
    val requireClientAuth = (env("AUTH_REQUIRE_CLIENT_AUTH") ?: "false").lowercase(Locale.ROOT) == "true"
    val keystorePath = env("AUTH_TLS_KEYSTORE_PATH")
    val keystorePassword = env("AUTH_TLS_KEYSTORE_PASSWORD")
    val truststorePath = env("AUTH_TLS_TRUSTSTORE_PATH")
    val truststorePassword = env("AUTH_TLS_TRUSTSTORE_PASSWORD")
    val signingKeyPkcs8B64 = env("AUTH_SIGNING_PRIVATE_KEY_PKCS8_B64")
    val signingPublicKeyX509B64 = env("AUTH_SIGNING_PUBLIC_KEY_X509_B64")
    return ServerConfig(
        host = host,
        port = port,
        dataDir = dataDir,
        requireTls = requireTls,
        requireClientAuth = requireClientAuth,
        keystorePath = keystorePath,
        keystorePassword = keystorePassword,
        truststorePath = truststorePath,
        truststorePassword = truststorePassword,
        signingKeyPkcs8B64 = signingKeyPkcs8B64,
        signingPublicKeyX509B64 = signingPublicKeyX509B64
    )
}
