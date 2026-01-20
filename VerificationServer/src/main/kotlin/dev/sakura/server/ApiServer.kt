package dev.sakura.server

import com.sun.net.httpserver.*
import kotlinx.serialization.encodeToString
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.time.Instant
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

internal data class AppState(
    val config: ServerConfig,
    val store: JsonStore,
    val signing: SigningKeys,
    val sessions: ConcurrentHashMap<String, SecureSession>
)

internal fun startApiServer(state: AppState): HttpServer {
    val server = createServer(state)
    server.executor = Executors.newFixedThreadPool(8)
    server.start()
    return server
}

private fun createServer(state: AppState): HttpServer {
    val tlsEnabled = state.config.keystorePath != null &&
            state.config.keystorePassword != null &&
            state.config.truststorePath != null &&
            state.config.truststorePassword != null

    if (!tlsEnabled && state.config.requireTls) {
        throw IllegalStateException("TLS is required but TLS config is missing")
    }

    val addr = InetSocketAddress(state.config.host, state.config.port)
    val server: HttpServer = if (tlsEnabled) {
        val https = HttpsServer.create(addr, 0)
        https.httpsConfigurator = object : HttpsConfigurator(buildSslContext(state.config)) {
            override fun configure(params: com.sun.net.httpserver.HttpsParameters) {
                val engine = sslContext.createSSLEngine()
                params.setNeedClientAuth(state.config.requireClientAuth)
                params.setProtocols(arrayOf("TLSv1.3"))
                params.setCipherSuites(engine.enabledCipherSuites)
                params.setSSLParameters(sslContext.defaultSSLParameters)
            }
        }
        https
    } else {
        HttpServer.create(addr, 0)
    }

    server.createContext("/api/v1/handshake", JsonHandler(state) { ex, body ->
        if (ex.requestMethod != "POST") return@JsonHandler respondJson(ex, 405, mapOf("error" to "METHOD_NOT_ALLOWED"))
        val req = JSON.decodeFromString(HandshakeRequest.serializer(), body)
        val resp = handleHandshake(state, req)
        respondJson(ex, 200, resp)
    })

    server.createContext("/api/v1/user/register", JsonHandler(state) { ex, body ->
        if (ex.requestMethod != "POST") return@JsonHandler respondJson(ex, 405, mapOf("error" to "METHOD_NOT_ALLOWED"))
        val req = JSON.decodeFromString(EncryptedRequest.serializer(), body)
        val resp = handleUserRegister(state, req)
        respondJson(ex, 200, resp)
    })

    server.createContext("/api/v1/user/login", JsonHandler(state) { ex, body ->
        if (ex.requestMethod != "POST") return@JsonHandler respondJson(ex, 405, mapOf("error" to "METHOD_NOT_ALLOWED"))
        val req = JSON.decodeFromString(EncryptedRequest.serializer(), body)
        val resp = handleUserLogin(state, req)
        respondJson(ex, 200, resp)
    })

    server.createContext("/api/v1/token/verify", JsonHandler(state) { ex, body ->
        if (ex.requestMethod != "POST") return@JsonHandler respondJson(ex, 405, mapOf("error" to "METHOD_NOT_ALLOWED"))
        val req = JSON.decodeFromString(EncryptedRequest.serializer(), body)
        val resp = handleTokenVerify(state, req)
        respondJson(ex, 200, resp)
    })

    server.createContext("/healthz", JsonHandler(state) { ex, _ ->
        respondJson(ex, 200, mapOf("ok" to true))
    })

    return server
}

private class JsonHandler(
    private val state: AppState,
    private val block: (HttpExchange, String) -> Unit
) : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        val startMs = System.currentTimeMillis()
        val method = exchange.requestMethod
        val path = exchange.requestURI?.path ?: ""
        val remote = exchange.remoteAddress?.toString() ?: ""
        try {
            val changed = state.store.reloadIfChanged()
            if (!changed) {
                val licPath = state.store.dir.resolve("licenses.json")
                if (Files.exists(licPath) && state.store.listLicenses().isEmpty()) {
                    val raw = try {
                        Files.readString(licPath, StandardCharsets.UTF_8)
                    } catch (_: Exception) {
                        ""
                    }
                    if (raw.isNotBlank()) state.store.reload()
                }
            }
            val contentType = exchange.requestHeaders.getFirst("Content-Type") ?: ""
            val body = exchange.requestBody.use { it.readBytes().toString(StandardCharsets.UTF_8) }
            if (exchange.requestMethod == "POST" && !contentType.lowercase(Locale.ROOT).contains("application/json")) {
                respondJson(exchange, 415, mapOf("error" to "UNSUPPORTED_MEDIA_TYPE"))
                return
            }
            block(exchange, body)
        } catch (e: Exception) {
            System.err.println("API_ERROR $method $path $remote ${e.javaClass.simpleName} ${e.message ?: ""}")
            e.printStackTrace()
            respondJson(exchange, 500, mapOf("error" to "INTERNAL_ERROR"))
        } finally {
            val cost = System.currentTimeMillis() - startMs
            println("API $method $path $remote ${cost}ms")
            exchange.close()
        }
    }
}

private fun normalizeLicenseKey(key: String?): String? {
    val raw = key ?: return null
    var v = raw.replace('\u0000', ' ')
    v = v.replace(Regex("\\s+"), "")
    v = v.replace(Regex("\\p{Pd}+"), "-")
    v = v.uppercase(Locale.ROOT)
    v = v.replace(Regex("[^A-Z0-9-]"), "")
    return v.takeIf { it.isNotBlank() }
}

private fun handleHandshake(state: AppState, req: HandshakeRequest): HandshakeResponse {
    val clientPubX509 = B64D.decode(req.clientEphemeralPub)
    val nonce = B64D.decode(req.nonce)
    if (nonce.size != 32) throw IllegalStateException("BAD_NONCE")

    val clientPub = x25519DecodePublicX509(clientPubX509)
    val serverEphemeral = x25519Generate()
    val shared = x25519Agree(serverEphemeral.private, clientPub)
    val key32 = hkdfSha256(shared, nonce, "lemon-auth-v1".toByteArray(StandardCharsets.UTF_8), 32)

    val sid = UUID.randomUUID().toString()
    state.sessions[sid] = SecureSession(sid, key32, Instant.now().epochSecond)

    val transcript = concat(clientPubX509, serverEphemeral.public.encoded, nonce)
    val sig = ed25519Sign(state.signing.privateKey, transcript)

    return HandshakeResponse(
        sid = sid,
        serverEphemeralPub = B64.encodeToString(serverEphemeral.public.encoded),
        nonce = B64.encodeToString(nonce),
        sig = B64.encodeToString(sig)
    )
}

private fun handleUserRegister(state: AppState, req: EncryptedRequest): EncryptedResponse {
    val session = requireSession(state, req.sid)
    val aad = ("sid=" + req.sid).toByteArray(StandardCharsets.UTF_8)
    val pt = aesGcmDecrypt(session.key32, B64D.decode(req.iv), B64D.decode(req.ct), aad)
    val plain = JSON.decodeFromString(UserRegisterPlainRequest.serializer(), pt.toString(StandardCharsets.UTF_8))

    if (!timestampOk(plain.ts)) {
        return encryptUserAuthResponse(
            session,
            UserAuthPlainResponse(ok = false, error = "BAD_TIMESTAMP").also {
                println("AUTH_REGISTER u=${plain.username} ok=false err=BAD_TIMESTAMP")
            }
        )
    }

    val username = normalizeUsername(plain.username) ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "BAD_REQUEST").also {
            println("AUTH_REGISTER u=${plain.username} ok=false err=BAD_REQUEST")
        }
    )
    val password = plain.password.takeIf { it.isNotBlank() } ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "BAD_REQUEST").also {
            println("AUTH_REGISTER u=$username ok=false err=BAD_REQUEST")
        }
    )
    val licenseKey = normalizeLicenseKey(plain.licenseKey) ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "BAD_REQUEST").also {
            println("AUTH_REGISTER u=$username ok=false err=BAD_REQUEST")
        }
    )
    val deviceId = plain.deviceId.trim().takeIf { it.isNotBlank() } ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "BAD_REQUEST").also {
            println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=BAD_REQUEST")
        }
    )

    val lic0 = state.store.findLicenseByKey(licenseKey) ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "LICENSE_NOT_FOUND").also {
            println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=LICENSE_NOT_FOUND loadedLicenses=" + state.store.listLicenses().size)
        }
    )
    if (lic0.status == "BANNED" || lic0.status == "REVOKED") return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "LICENSE_REVOKED").also {
            println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=LICENSE_REVOKED")
        }
    )
    val existingUser = state.store.findUserByUsername(username)
    val user = if (existingUser != null) {
        if (!state.store.verifyUserPassword(existingUser, password)) {
            return encryptUserAuthResponse(
                session,
                UserAuthPlainResponse(ok = false, error = "BAD_CREDENTIALS").also {
                    println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=BAD_CREDENTIALS")
                }
            )
        }
        existingUser
    } else {
        if (lic0.userId != null) {
            return encryptUserAuthResponse(
                session,
                UserAuthPlainResponse(ok = false, error = "LICENSE_ALREADY_CLAIMED").also {
                    println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=LICENSE_ALREADY_CLAIMED")
                }
            )
        }
        try {
            state.store.createUserAndClaimLicense(username, password, iterations = 200_000, licenseKey = licenseKey)
        } catch (e: Exception) {
            val msg = e.message ?: ""
            val err = when (msg) {
                "USER_EXISTS" -> "USER_EXISTS"
                "LICENSE_ALREADY_CLAIMED" -> "LICENSE_ALREADY_CLAIMED"
                "LICENSE_NOT_FOUND" -> "LICENSE_NOT_FOUND"
                else -> "USER_CREATE_FAILED"
            }
            return encryptUserAuthResponse(
                session,
                UserAuthPlainResponse(ok = false, error = err).also {
                    println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=$err")
                }
            )
        }
    }

    when (val ownerId = (state.store.findLicenseByKey(licenseKey) ?: lic0).userId) {
        null -> {
            val claimed = state.store.claimLicense(licenseKey, user.id)
            if (!claimed) return encryptUserAuthResponse(
                session,
                UserAuthPlainResponse(ok = false, error = "LICENSE_ALREADY_CLAIMED").also {
                    println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=LICENSE_ALREADY_CLAIMED")
                }
            )
        }

        user.id -> Unit

        else -> return encryptUserAuthResponse(
            session,
            UserAuthPlainResponse(ok = false, error = "LICENSE_ALREADY_CLAIMED").also {
                println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=LICENSE_ALREADY_CLAIMED")
            }
        )
    }

    val now = Instant.now().epochSecond
    val lic = state.store.activateIfNeeded(state.store.findLicenseByKey(licenseKey) ?: lic0, now)
    val exp = lic.expiresAt ?: 0L
    if (exp > 0 && now >= exp) return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "LICENSE_EXPIRED", licenseKey = licenseKey, expiresAt = exp).also {
            println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=LICENSE_EXPIRED")
        }
    )

    val normDev = normalizeDeviceId(deviceId)
    val binding = state.store.getBinding(licenseKey)
    when {
        binding == null -> state.store.upsertBinding(licenseKey, normDev, iterations = 200_000)
        !state.store.verifyBinding(binding, normDev) -> return encryptUserAuthResponse(
            session,
            UserAuthPlainResponse(
                ok = false,
                error = "DEVICE_MISMATCH",
                licenseKey = licenseKey,
                expiresAt = exp
            ).also {
                println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=false err=DEVICE_MISMATCH")
            }
        )
    }

    val token = signToken(state.signing.privateKey, licenseKey, exp)
    return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = true, token = token, licenseKey = licenseKey, expiresAt = exp).also {
            println("AUTH_REGISTER u=$username lic=${licenseKey.take(12)} ok=true")
        }
    )
}

private fun handleUserLogin(state: AppState, req: EncryptedRequest): EncryptedResponse {
    val session = requireSession(state, req.sid)
    val aad = ("sid=" + req.sid).toByteArray(StandardCharsets.UTF_8)
    val pt = aesGcmDecrypt(session.key32, B64D.decode(req.iv), B64D.decode(req.ct), aad)
    val plain = JSON.decodeFromString(UserLoginPlainRequest.serializer(), pt.toString(StandardCharsets.UTF_8))

    if (!timestampOk(plain.ts)) return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "BAD_TIMESTAMP").also {
            println("AUTH_LOGIN u=${plain.username} ok=false err=BAD_TIMESTAMP")
        }
    )

    val username = normalizeUsername(plain.username) ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "BAD_REQUEST").also {
            println("AUTH_LOGIN u=${plain.username} ok=false err=BAD_REQUEST")
        }
    )
    val password = plain.password.takeIf { it.isNotBlank() } ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "BAD_REQUEST").also {
            println("AUTH_LOGIN u=$username ok=false err=BAD_REQUEST")
        }
    )
    val deviceId = plain.deviceId.trim().takeIf { it.isNotBlank() } ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "BAD_REQUEST").also {
            println("AUTH_LOGIN u=$username ok=false err=BAD_REQUEST")
        }
    )

    val user = state.store.findUserByUsername(username) ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "USER_NOT_FOUND").also {
            println("AUTH_LOGIN u=$username ok=false err=USER_NOT_FOUND")
        }
    )
    if (!state.store.verifyUserPassword(user, password)) return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "BAD_CREDENTIALS").also {
            println("AUTH_LOGIN u=$username ok=false err=BAD_CREDENTIALS")
        }
    )

    val lic0 = state.store.findFirstLicenseForUser(user.id) ?: return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "NO_LICENSE_BOUND").also {
            println("AUTH_LOGIN u=$username ok=false err=NO_LICENSE_BOUND")
        }
    )
    if (lic0.status == "BANNED" || lic0.status == "REVOKED") return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "LICENSE_REVOKED").also {
            println("AUTH_LOGIN u=$username lic=${lic0.key.take(12)} ok=false err=LICENSE_REVOKED")
        }
    )

    val now = Instant.now().epochSecond
    val lic = state.store.activateIfNeeded(lic0, now)
    val exp = lic.expiresAt ?: 0L
    if (exp > 0 && now >= exp) return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = false, error = "LICENSE_EXPIRED", licenseKey = lic.key, expiresAt = exp).also {
            println("AUTH_LOGIN u=$username lic=${lic.key.take(12)} ok=false err=LICENSE_EXPIRED")
        }
    )

    val normDev = normalizeDeviceId(deviceId)
    val binding = state.store.getBinding(lic.key)
    when {
        binding == null -> state.store.upsertBinding(lic.key, normDev, iterations = 200_000)
        !state.store.verifyBinding(binding, normDev) -> return encryptUserAuthResponse(
            session,
            UserAuthPlainResponse(ok = false, error = "DEVICE_MISMATCH", licenseKey = lic.key, expiresAt = exp).also {
                println("AUTH_LOGIN u=$username lic=${lic.key.take(12)} ok=false err=DEVICE_MISMATCH")
            }
        )
    }

    val token = signToken(state.signing.privateKey, lic.key, exp)
    return encryptUserAuthResponse(
        session,
        UserAuthPlainResponse(ok = true, token = token, licenseKey = lic.key, expiresAt = exp).also {
            println("AUTH_LOGIN u=$username lic=${lic.key.take(12)} ok=true")
        }
    )
}

private fun handleTokenVerify(state: AppState, req: EncryptedRequest): EncryptedResponse {
    val session = requireSession(state, req.sid)
    val aad = ("sid=" + req.sid).toByteArray(StandardCharsets.UTF_8)
    val pt = aesGcmDecrypt(session.key32, B64D.decode(req.iv), B64D.decode(req.ct), aad)
    val plain = JSON.decodeFromString(TokenVerifyPlainRequest.serializer(), pt.toString(StandardCharsets.UTF_8))

    if (!timestampOk(plain.ts)) return encryptTokenVerifyResponse(
        session,
        TokenVerifyPlainResponse(ok = false, error = "BAD_TIMESTAMP")
    )

    val token = plain.token.trim()
    val (payloadObj, tokenErr) = verifyTokenPayload(state.signing.publicKey, token)
    if (payloadObj == null) return encryptTokenVerifyResponse(
        session,
        TokenVerifyPlainResponse(ok = false, error = tokenErr ?: "INVALID_TOKEN")
    )

    val now = Instant.now().epochSecond
    if (payloadObj.expiresAt > 0 && now >= payloadObj.expiresAt) {
        return encryptTokenVerifyResponse(
            session,
            TokenVerifyPlainResponse(
                ok = false,
                error = "TOKEN_EXPIRED",
                licenseKey = payloadObj.licenseKey,
                expiresAt = payloadObj.expiresAt
            )
        )
    }

    val deviceId = plain.deviceId?.trim()?.takeIf { it.isNotBlank() }
    if (deviceId != null) {
        val lic0 = state.store.findLicenseByKey(payloadObj.licenseKey) ?: return encryptTokenVerifyResponse(
            session,
            TokenVerifyPlainResponse(
                ok = false,
                error = "LICENSE_NOT_FOUND",
                licenseKey = payloadObj.licenseKey,
                expiresAt = payloadObj.expiresAt
            )
        )
        if (lic0.status == "BANNED" || lic0.status == "REVOKED") return encryptTokenVerifyResponse(
            session,
            TokenVerifyPlainResponse(
                ok = false,
                error = "LICENSE_REVOKED",
                licenseKey = payloadObj.licenseKey,
                expiresAt = payloadObj.expiresAt
            )
        )
        val exp = lic0.expiresAt ?: payloadObj.expiresAt
        if (exp > 0 && now >= exp) return encryptTokenVerifyResponse(
            session,
            TokenVerifyPlainResponse(
                ok = false,
                error = "LICENSE_EXPIRED",
                licenseKey = payloadObj.licenseKey,
                expiresAt = exp
            )
        )
        val binding = state.store.getBinding(payloadObj.licenseKey) ?: return encryptTokenVerifyResponse(
            session,
            TokenVerifyPlainResponse(
                ok = false,
                error = "DEVICE_NOT_BOUND",
                licenseKey = payloadObj.licenseKey,
                expiresAt = exp
            )
        )
        if (!state.store.verifyBinding(binding, normalizeDeviceId(deviceId))) return encryptTokenVerifyResponse(
            session,
            TokenVerifyPlainResponse(
                ok = false,
                error = "DEVICE_MISMATCH",
                licenseKey = payloadObj.licenseKey,
                expiresAt = exp
            )
        )
        return encryptTokenVerifyResponse(
            session,
            TokenVerifyPlainResponse(ok = true, licenseKey = payloadObj.licenseKey, expiresAt = exp)
        )
    }

    return encryptTokenVerifyResponse(
        session,
        TokenVerifyPlainResponse(ok = true, licenseKey = payloadObj.licenseKey, expiresAt = payloadObj.expiresAt)
    )
}

private fun timestampOk(tsMs: Long): Boolean = kotlin.math.abs(tsMs - System.currentTimeMillis()) <= 120_000L

private fun requireSession(state: AppState, sid: String): SecureSession {
    val session = state.sessions[sid] ?: throw IllegalStateException("NO_SESSION")
    val now = Instant.now().epochSecond
    if (now - session.createdAt > 60) {
        state.sessions.remove(sid)
        throw IllegalStateException("SESSION_EXPIRED")
    }
    return session
}

private fun encryptUserAuthResponse(session: SecureSession, resp: UserAuthPlainResponse): EncryptedResponse {
    val aad = ("sid=" + session.sid).toByteArray(StandardCharsets.UTF_8)
    val pt = JSON.encodeToString(resp).toByteArray(StandardCharsets.UTF_8)
    val (iv, ct) = aesGcmEncrypt(session.key32, pt, aad)
    return EncryptedResponse(iv = B64.encodeToString(iv), ct = B64.encodeToString(ct))
}

private fun encryptTokenVerifyResponse(session: SecureSession, resp: TokenVerifyPlainResponse): EncryptedResponse {
    val aad = ("sid=" + session.sid).toByteArray(StandardCharsets.UTF_8)
    val pt = JSON.encodeToString(resp).toByteArray(StandardCharsets.UTF_8)
    val (iv, ct) = aesGcmEncrypt(session.key32, pt, aad)
    return EncryptedResponse(iv = B64.encodeToString(iv), ct = B64.encodeToString(ct))
}

private inline fun <reified T> respondJson(ex: HttpExchange, code: Int, value: T) {
    val payload = when (value) {
        is String -> value
        else -> JSON.encodeToString(value)
    }.toByteArray(StandardCharsets.UTF_8)
    val headers: Headers = ex.responseHeaders
    headers.set("Content-Type", "application/json; charset=utf-8")
    headers.set("Cache-Control", "no-store")
    ex.sendResponseHeaders(code, payload.size.toLong())
    ex.responseBody.use { it.write(payload) }
}

private fun buildSslContext(config: ServerConfig): SSLContext {
    val keyStore = java.security.KeyStore.getInstance("PKCS12").apply {
        java.io.FileInputStream(config.keystorePath!!).use { load(it, config.keystorePassword!!.toCharArray()) }
    }
    val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()).apply {
        init(keyStore, config.keystorePassword!!.toCharArray())
    }

    val trustStore = java.security.KeyStore.getInstance("PKCS12").apply {
        java.io.FileInputStream(config.truststorePath!!).use { load(it, config.truststorePassword!!.toCharArray()) }
    }
    val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
        init(trustStore)
    }

    val ctx = SSLContext.getInstance("TLSv1.3")
    ctx.init(kmf.keyManagers, tmf.trustManagers, java.security.SecureRandom())
    return ctx
}
