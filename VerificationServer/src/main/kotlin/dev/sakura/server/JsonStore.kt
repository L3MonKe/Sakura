package dev.sakura.server

import kotlinx.serialization.builtins.ListSerializer
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.time.Instant
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

internal class JsonStore(dataDir: Path) {
    internal val dir: Path = dataDir.toAbsolutePath().normalize().also { Files.createDirectories(it) }
    private val usersPath: Path = dir.resolve("users.json")
    private val licensesPath: Path = dir.resolve("licenses.json")
    private val bindingsPath: Path = dir.resolve("bindings.json")

    private val lock = ReentrantReadWriteLock()

    private var users: MutableList<UserRecord> = mutableListOf()
    private var licenses: MutableList<LicenseRecord> = mutableListOf()
    private var bindings: MutableList<BindingRecord> = mutableListOf()

    private var usersMtimeMs: Long = 0L
    private var licensesMtimeMs: Long = 0L
    private var bindingsMtimeMs: Long = 0L

    init {
        reload()
    }

    fun reload() {
        lock.write {
            users = readList(usersPath, ListSerializer(UserRecord.serializer())).toMutableList()
            licenses = readList(licensesPath, ListSerializer(LicenseRecord.serializer()))
                .map { it.copy(key = normalizeLicenseKeyForStore(it.key)) }
                .toMutableList()
            bindings = readList(bindingsPath, ListSerializer(BindingRecord.serializer())).toMutableList()
            usersMtimeMs = mtimeMs(usersPath)
            licensesMtimeMs = mtimeMs(licensesPath)
            bindingsMtimeMs = mtimeMs(bindingsPath)
        }
    }

    fun reloadIfChanged(): Boolean {
        val u = mtimeMs(usersPath)
        val l = mtimeMs(licensesPath)
        val b = mtimeMs(bindingsPath)
        if (u == usersMtimeMs && l == licensesMtimeMs && b == bindingsMtimeMs) return false
        return try {
            reload()
            true
        } catch (_: Exception) {
            false
        }
    }

    fun listUsers(): List<UserRecord> = lock.read { users.toList() }

    fun listLicenses(): List<LicenseRecord> = lock.read { licenses.toList() }

    fun findUserByUsername(username: String): UserRecord? = lock.read {
        users.firstOrNull { it.username == username }
    }

    fun createUser(username: String, password: String, iterations: Int): UserRecord {
        val norm = normalizeUsername(username) ?: throw IllegalStateException("BAD_REQUEST")
        lock.write {
            if (users.any { it.username == norm }) throw IllegalStateException("USER_EXISTS")
            val salt = randomBytes(16)
            val hash = pbkdf2(password, salt, iterations, 32)
            val now = Instant.now().epochSecond
            val nextId = (users.maxOfOrNull { it.id } ?: 0L) + 1L
            val u = UserRecord(
                id = nextId,
                username = norm,
                saltB64 = B64.encodeToString(salt),
                hashB64 = B64.encodeToString(hash),
                iterations = iterations,
                createdAt = now
            )
            users.add(u)
            persistUsersLocked()
            return u
        }
    }

    fun createUserAndClaimLicense(username: String, password: String, iterations: Int, licenseKey: String): UserRecord {
        val norm = normalizeUsername(username) ?: throw IllegalStateException("BAD_REQUEST")
        lock.write {
            if (users.any { it.username == norm }) throw IllegalStateException("USER_EXISTS")
            val licIdx = licenses.indexOfFirst { it.key == licenseKey }
            if (licIdx < 0) throw IllegalStateException("LICENSE_NOT_FOUND")
            val lic = licenses[licIdx]
            if (lic.userId != null) throw IllegalStateException("LICENSE_ALREADY_CLAIMED")

            val salt = randomBytes(16)
            val hash = pbkdf2(password, salt, iterations, 32)
            val now = Instant.now().epochSecond
            val nextId = (users.maxOfOrNull { it.id } ?: 0L) + 1L
            val u = UserRecord(
                id = nextId,
                username = norm,
                saltB64 = B64.encodeToString(salt),
                hashB64 = B64.encodeToString(hash),
                iterations = iterations,
                createdAt = now
            )

            users.add(u)
            licenses[licIdx] = lic.copy(userId = u.id)
            persistUsersLocked()
            persistLicensesLocked()
            return u
        }
    }

    fun verifyUserPassword(user: UserRecord, password: String): Boolean {
        val salt = B64D.decode(user.saltB64)
        val expected = B64D.decode(user.hashB64)
        val candidate = pbkdf2(password, salt, user.iterations, expected.size)
        return constantTimeEq(candidate, expected)
    }

    fun seedLicenses(keys: List<String>, validDays: Int) {
        if (keys.isEmpty()) return
        val now = Instant.now().epochSecond
        lock.write {
            val existing = licenses.map { it.key }.toHashSet()
            val toAdd =
                keys.map { normalizeLicenseKeyForStore(it) }.filter { it.isNotBlank() && !existing.contains(it) }
            if (toAdd.isEmpty()) return
            for (k in toAdd) {
                licenses.add(
                    LicenseRecord(
                        key = k,
                        status = "NEW",
                        userId = null,
                        validDays = validDays,
                        createdAt = now,
                        activatedAt = null,
                        expiresAt = null
                    )
                )
            }
            persistLicensesLocked()
        }
    }

    fun generateLicenses(count: Int, validDays: Int): List<String> {
        val now = Instant.now().epochSecond
        val out = mutableListOf<String>()
        lock.write {
            val existing = licenses.map { it.key }.toHashSet()
            while (out.size < count) {
                val k = normalizeLicenseKeyForStore("LEMON-" + java.util.UUID.randomUUID().toString().replace("-", ""))
                if (existing.contains(k)) continue
                existing.add(k)
                out.add(k)
                licenses.add(
                    LicenseRecord(
                        key = k,
                        status = "NEW",
                        userId = null,
                        validDays = validDays,
                        createdAt = now,
                        activatedAt = null,
                        expiresAt = null
                    )
                )
            }
            persistLicensesLocked()
        }
        return out
    }

    fun updateLicenseStatus(key: String, status: String): Boolean {
        lock.write {
            val idx = licenses.indexOfFirst { it.key == key }
            if (idx < 0) return false
            licenses[idx] = licenses[idx].copy(status = status)
            persistLicensesLocked()
            return true
        }
    }

    fun findLicenseByKey(key: String): LicenseRecord? = lock.read {
        licenses.firstOrNull { it.key == key }
    }

    fun findFirstLicenseForUser(userId: Long): LicenseRecord? = lock.read {
        licenses.firstOrNull { it.userId == userId && it.status != "BANNED" && it.status != "REVOKED" }
    }

    fun claimLicense(licenseKey: String, userId: Long): Boolean {
        lock.write {
            val idx = licenses.indexOfFirst { it.key == licenseKey }
            if (idx < 0) return false
            val lic = licenses[idx]
            if (lic.userId != null) return false
            licenses[idx] = lic.copy(userId = userId)
            persistLicensesLocked()
            return true
        }
    }

    fun activateIfNeeded(lic: LicenseRecord, now: Long): LicenseRecord {
        if (lic.activatedAt != null && lic.expiresAt != null && lic.status == "ACTIVE") return lic
        val activatedAt = lic.activatedAt ?: now
        val expiresAt = lic.expiresAt ?: (activatedAt + lic.validDays.toLong() * 86400L)
        lock.write {
            val idx = licenses.indexOfFirst { it.key == lic.key }
            if (idx >= 0) {
                licenses[idx] = lic.copy(activatedAt = activatedAt, expiresAt = expiresAt, status = "ACTIVE")
                persistLicensesLocked()
            }
        }
        return lic.copy(activatedAt = activatedAt, expiresAt = expiresAt, status = "ACTIVE")
    }

    fun getBinding(licenseKey: String): BindingRecord? = lock.read {
        bindings.firstOrNull { it.licenseKey == licenseKey }
    }

    fun verifyBinding(binding: BindingRecord, deviceId: String): Boolean {
        val salt = B64D.decode(binding.saltB64)
        val expected = B64D.decode(binding.hashB64)
        val candidate = pbkdf2(deviceId, salt, binding.iterations, expected.size)
        return constantTimeEq(candidate, expected)
    }

    fun upsertBinding(licenseKey: String, deviceId: String, iterations: Int) {
        val salt = randomBytes(16)
        val hash = pbkdf2(deviceId, salt, iterations, 32)
        val now = Instant.now().epochSecond
        lock.write {
            val idx = bindings.indexOfFirst { it.licenseKey == licenseKey }
            val rec = BindingRecord(
                licenseKey = licenseKey,
                saltB64 = B64.encodeToString(salt),
                hashB64 = B64.encodeToString(hash),
                iterations = iterations,
                createdAt = now
            )
            if (idx >= 0) bindings[idx] = rec else bindings.add(rec)
            persistBindingsLocked()
        }
    }

    fun deleteBinding(licenseKey: String): Boolean {
        lock.write {
            val before = bindings.size
            bindings.removeIf { it.licenseKey == licenseKey }
            val changed = bindings.size != before
            if (changed) persistBindingsLocked()
            return changed
        }
    }

    private fun persistUsersLocked() {
        writeList(usersPath, users, ListSerializer(UserRecord.serializer()))
        usersMtimeMs = mtimeMs(usersPath)
    }

    private fun persistLicensesLocked() {
        writeList(licensesPath, licenses, ListSerializer(LicenseRecord.serializer()))
        licensesMtimeMs = mtimeMs(licensesPath)
    }

    private fun persistBindingsLocked() {
        writeList(bindingsPath, bindings, ListSerializer(BindingRecord.serializer()))
        bindingsMtimeMs = mtimeMs(bindingsPath)
    }

    private fun normalizeLicenseKeyForStore(key: String?): String {
        val raw = key ?: return ""
        var v = raw.replace('\u0000', ' ')
        v = v.replace(Regex("\\s+"), "")
        v = v.replace(Regex("\\p{Pd}+"), "-")
        v = v.uppercase(Locale.ROOT)
        v = v.replace(Regex("[^A-Z0-9-]"), "")
        return v
    }

    private fun <T> readList(path: Path, serializer: kotlinx.serialization.KSerializer<List<T>>): List<T> {
        if (!Files.exists(path)) return emptyList()
        val text = Files.readString(path, StandardCharsets.UTF_8)
        if (text.isBlank()) return emptyList()
        return JSON.decodeFromString(serializer, text)
    }

    private fun <T> writeList(path: Path, value: List<T>, serializer: kotlinx.serialization.KSerializer<List<T>>) {
        val text = JSON.encodeToString(serializer, value)
        val tmp = path.resolveSibling(path.fileName.toString() + ".tmp")
        Files.createDirectories(path.parent)
        Files.writeString(tmp, text, StandardCharsets.UTF_8)
        try {
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
        } catch (_: java.nio.file.AtomicMoveNotSupportedException) {
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING)
        }
    }

    private fun mtimeMs(path: Path): Long {
        return try {
            if (!Files.exists(path)) return 0L
            Files.getLastModifiedTime(path).toMillis()
        } catch (_: Exception) {
            0L
        }
    }
}

