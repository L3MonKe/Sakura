import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.task.RemapJarTask
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("fabric-loom") version "1.14-SNAPSHOT"
    id("com.github.gmazzo.buildconfig") version "5.3.5"
    id("com.gradleup.shadow") version "9.0.0-beta4"
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

base {
    archivesName.set(property("archives_base_name").toString())
}

version = property("mod_version")!!
group = property("maven_group")!!

buildConfig {
    buildConfigField("String", "VERSION", "\"${property("mod_version")}\"")
    buildConfigField("String", "BUILD_TIME", "\"${SimpleDateFormat("MM/dd/yyyy HH:mm").format(Date())}\"")

    val pinnedKeyRaw = (findProperty("pinned_server_signing_key_x509_base64") as String?)
        ?: System.getenv("PINNED_SERVER_SIGNING_KEY_X509_BASE64")
        ?: ""
    val pinnedKey = pinnedKeyRaw.replace("\\", "\\\\").replace("\"", "\\\"")
    buildConfigField("String", "PINNED_SERVER_SIGNING_KEY_X509_BASE64", "\"$pinnedKey\"")

    packageName("dev.sakura.client")
    useJavaOutput()
    generateAtSync.set(true)
}

configure<LoomGradleExtensionAPI> {
    accessWidenerPath.set(file("src/main/resources/sakura.accesswidener"))
}

sourceSets["main"].java.srcDirs("build/gen/buildconfig/src/main")

repositories {
    //mavenLocal()
    maven {
        name = "AliyunPublic"
        url = uri("https://maven.aliyun.com/repository/public")
    }
    maven {
        name = "AliyunGoogle"
        url = uri("https://maven.aliyun.com/repository/google")
    }
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven("https://impactdevelopment.github.io/maven/")
    maven("https://maven.fabricmc.net/")
    maven("https://maven.isxander.dev/")
    maven("https://maven.meteordev.org/releases")
    maven("https://maven.meteordev.org/snapshots")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_version")}:v2")

    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

    implementation(include("meteordevelopment:orbit:${property("orbit_version")}")!!)

    // native-obfuscator annotations
    implementation(files("libs/annotations.jar"))

    // NanoVG 运行库
    val lwjglVersion = property("nanovg_version")
    implementation(include("org.lwjgl:lwjgl-nanovg:$lwjglVersion")!!)

    // 跨平台 Natives 支持
    val platforms = listOf(
        "natives-windows",
        "natives-macos",
        "natives-macos-arm64",
        "natives-linux",
        "natives-linux-arm64"
    )

    platforms.forEach { platform ->
        runtimeOnly(include("org.lwjgl:lwjgl:$lwjglVersion:$platform")!!)
        runtimeOnly(include("org.lwjgl:lwjgl-nanovg:$lwjglVersion:$platform")!!)
        runtimeOnly(include("org.lwjgl:lwjgl-opengles:$lwjglVersion:$platform")!!)
    }
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand(project.properties)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xmaxerrs", "9178"))
    dependsOn("generateBuildConfig")
}

tasks.named<ShadowJar>("shadowJar") {
    configurations = listOf(project.configurations["shadow"])
    archiveClassifier.set("dev")
}

tasks.named<RemapJarTask>("remapJar") {
    dependsOn("shadowJar")
    inputFile.set(tasks.named<ShadowJar>("shadowJar").get().archiveFile)
}

val isMyHome = run {
    val userHome = System.getProperty("user.home") ?: ""
    val normalized = userHome.replace('\\', '/')
    userHome == "L3MonKe" || normalized.endsWith("/L3MonKe")
}

val minecraftModsDir = file("C:/Users/L3MonKe/Desktop/MC/.minecraft/versions/Sakura-1.21.11/mods")

val copyJarToMinecraftMods = tasks.register<Copy>("copyJarToMinecraftMods") {
    group = "distribution"

    val remapJar = tasks.named<RemapJarTask>("remapJar")
    dependsOn(remapJar)

    onlyIf { isMyHome }

    from(remapJar.flatMap { it.archiveFile })
    into(minecraftModsDir)
}

tasks.named("build") {
    finalizedBy(copyJarToMinecraftMods)
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.register("buildClientOnly") {
    doFirst {
        tasks.findByName("remapLoader")?.enabled = false
    }
    finalizedBy("build")
}

/*tasks.register<Copy>("extractRuntimeClasspath") {
    group = "distribution"
    description = "Extract runtime classpath to ZKM/libs directory"

    from(configurations.runtimeClasspath)
    into("$projectDir/Deobf/ZKM/libs")

    doFirst {
        file("$projectDir/Deobf/ZKM/libs").mkdirs()
    }
}*/
