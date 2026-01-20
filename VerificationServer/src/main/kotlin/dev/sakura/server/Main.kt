package dev.sakura.server

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentHashMap

fun main(args: Array<String>) {
    val selfTest = args.any { it == "--selftest" }
    val noUi = args.any { it == "--no-ui" }

    val baseConfig = loadConfig()
    val dataDir =
        if (selfTest) Files.createTempDirectory("VerificationServer-selftest") else resolveDataDir(baseConfig.dataDir)
    val config =
        if (selfTest) baseConfig.copy(host = "127.0.0.1", port = 0, dataDir = dataDir.toString()) else baseConfig
    val store = JsonStore(dataDir)
    println("DATA_DIR=${store.dir}")
    println("LICENSES_LOADED=" + store.listLicenses().size)

    val seedKeys =
        (System.getenv("AUTH_SEED_LICENSE_KEYS") ?: "").split(",").map { it.trim() }.filter { it.isNotBlank() }
    val seedDays = (System.getenv("AUTH_SEED_LICENSE_VALID_DAYS") ?: "30").toIntOrNull() ?: 30
    store.seedLicenses(seedKeys, seedDays)
    if (seedKeys.isNotEmpty()) {
        store.reload()
        println("LICENSES_LOADED_AFTER_SEED=" + store.listLicenses().size)
    }

    val signing = loadSigningKeys(config.signingKeyPkcs8B64, config.signingPublicKeyX509B64)
    val signingPubB64 = B64.encodeToString(signing.publicKey.encoded)
    println("AUTH_SERVER_SIGNING_KEY_X509_BASE64=$signingPubB64")

    val state = AppState(
        config = config,
        store = store,
        signing = signing,
        sessions = ConcurrentHashMap()
    )

    val server = startApiServer(state)
    val listeningPort = server.address.port
    println("Listening: ${config.host}:$listeningPort")

    if (selfTest) {
        val baseUrl = "http://127.0.0.1:$listeningPort"
        runSelfTest(baseUrl, store)
        server.stop(0)
        return
    }

    if (!noUi && !java.awt.GraphicsEnvironment.isHeadless()) {
        showAdminUi(store)
    }
}

private fun runSelfTest(baseUrl: String, store: JsonStore) {
    val keys = store.generateLicenses(1, 30)
    val licenseKeyRaw = keys.first()
    val licenseKey = licenseKeyRaw.replaceFirst("-", "\u2014")

    val client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build()

    val clientEphemeral = x25519Generate()
    val nonce = randomBytes(32)
    val hsReq = HandshakeRequest(
        v = "v1",
        clientEphemeralPub = B64.encodeToString(clientEphemeral.public.encoded),
        nonce = B64.encodeToString(nonce)
    )
    val hsRespJson =
        postJson(client, "$baseUrl/api/v1/handshake", JSON.encodeToString(HandshakeRequest.serializer(), hsReq))
    val hsResp = JSON.decodeFromString(HandshakeResponse.serializer(), hsRespJson)

    val sid = hsResp.sid
    val serverPubX509 = B64D.decode(hsResp.serverEphemeralPub)
    val nonceEcho = B64D.decode(hsResp.nonce)
    if (!nonce.contentEquals(nonceEcho)) throw IllegalStateException("NONCE_MISMATCH")

    val serverPub = x25519DecodePublicX509(serverPubX509)
    val shared = x25519Agree(clientEphemeral.private, serverPub)
    val key32 = hkdfSha256(shared, nonce, "lemon-auth-v1".toByteArray(StandardCharsets.UTF_8), 32)
    val aad = ("sid=" + sid).toByteArray(StandardCharsets.UTF_8)

    val user = "selftest_" + UUID.randomUUID().toString().replace("-", "").take(8)
    val deviceId = "selftest-device-" + UUID.randomUUID().toString().replace("-", "")
    val regPlain = UserRegisterPlainRequest(
        username = user,
        password = "selftest",
        licenseKey = licenseKey,
        deviceId = deviceId,
        clientVersion = "selftest",
        ts = System.currentTimeMillis()
    )
    val regPlainJson = JSON.encodeToString(UserRegisterPlainRequest.serializer(), regPlain)
    val (iv, ct) = aesGcmEncrypt(key32, regPlainJson.toByteArray(StandardCharsets.UTF_8), aad)
    val regReq = EncryptedRequest(
        sid = sid,
        iv = B64.encodeToString(iv),
        ct = B64.encodeToString(ct)
    )
    val regRespJson =
        postJson(client, "$baseUrl/api/v1/user/register", JSON.encodeToString(EncryptedRequest.serializer(), regReq))
    val regResp = JSON.decodeFromString(EncryptedResponse.serializer(), regRespJson)

    val pt = aesGcmDecrypt(key32, B64D.decode(regResp.iv), B64D.decode(regResp.ct), aad)
    val auth = JSON.decodeFromString(UserAuthPlainResponse.serializer(), pt.toString(StandardCharsets.UTF_8))
    if (!auth.ok) throw IllegalStateException(auth.error ?: "REGISTER_FAILED")

    val user2 = "selftest_" + UUID.randomUUID().toString().replace("-", "").take(8)
    val regPlain2 = UserRegisterPlainRequest(
        username = user2,
        password = "selftest",
        licenseKey = licenseKey,
        deviceId = "selftest-device-" + UUID.randomUUID().toString().replace("-", ""),
        clientVersion = "selftest",
        ts = System.currentTimeMillis()
    )
    val regPlainJson2 = JSON.encodeToString(UserRegisterPlainRequest.serializer(), regPlain2)
    val (iv2, ct2) = aesGcmEncrypt(key32, regPlainJson2.toByteArray(StandardCharsets.UTF_8), aad)
    val regReq2 = EncryptedRequest(
        sid = sid,
        iv = B64.encodeToString(iv2),
        ct = B64.encodeToString(ct2)
    )
    val regRespJson2 =
        postJson(client, "$baseUrl/api/v1/user/register", JSON.encodeToString(EncryptedRequest.serializer(), regReq2))
    val regResp2 = JSON.decodeFromString(EncryptedResponse.serializer(), regRespJson2)
    val pt2 = aesGcmDecrypt(key32, B64D.decode(regResp2.iv), B64D.decode(regResp2.ct), aad)
    val auth2 = JSON.decodeFromString(UserAuthPlainResponse.serializer(), pt2.toString(StandardCharsets.UTF_8))
    if (auth2.ok) throw IllegalStateException("EXPECTED_LICENSE_ALREADY_CLAIMED")
    if (auth2.error != "LICENSE_ALREADY_CLAIMED") throw IllegalStateException(auth2.error ?: "EXPECTED_LICENSE_ALREADY_CLAIMED")
    if (store.findUserByUsername(user2) != null) throw IllegalStateException("ORPHAN_USER_CREATED")

    val usersPath = store.dir.resolve("users.json")
    val bindingsPath = store.dir.resolve("bindings.json")
    if (!Files.exists(usersPath) || !Files.exists(bindingsPath)) throw IllegalStateException("PERSIST_FAILED")
}

private fun postJson(client: HttpClient, url: String, bodyJson: String): String {
    val req = HttpRequest.newBuilder(URI.create(url))
        .timeout(Duration.ofSeconds(8))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(bodyJson, StandardCharsets.UTF_8))
        .build()
    val resp = client.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
    if (resp.statusCode() < 200 || resp.statusCode() >= 300) throw IllegalStateException("HTTP_" + resp.statusCode())
    return resp.body()
}

private fun resolveDataDir(raw: String): Path {
    val p = Paths.get(raw)
    if (p.isAbsolute) return p
    val cwd = Paths.get("").toAbsolutePath().normalize()
    val direct = cwd.resolve(p).normalize()
    val alt = cwd.resolve("VerificationServer").resolve(p).normalize()

    fun hasAnyData(dir: Path): Boolean {
        return Files.exists(dir.resolve("licenses.json")) ||
                Files.exists(dir.resolve("users.json")) ||
                Files.exists(dir.resolve("bindings.json"))
    }

    return when {
        hasAnyData(direct) -> direct
        hasAnyData(alt) -> alt
        else -> direct
    }
}

