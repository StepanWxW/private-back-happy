
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

group = "com.food"
version = "0.0.1"

application {
    mainClass.set("com.food.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven("https://repo.perfectdreams.net/")
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
//    implementation("io.ktor:ktor-serialization-jackson-jvm")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$kotlin_version")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.slf4j:slf4j-api:$1.7.36")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation ("io.ktor:ktor-server-netty:1.3.2-1.4-M2")

    implementation ("org.jetbrains.exposed:exposed-core:0.44.0")
    implementation ("org.jetbrains.exposed:exposed-dao:0.44.0")
    implementation ("org.jetbrains.exposed:exposed-jdbc:0.44.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.30.1")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation ("org.postgresql:postgresql:42.6.0")
    implementation("net.perfectdreams.exposedpowerutils:postgres-java-time:1.1.0")

    implementation ("com.google.firebase:firebase-admin:9.2.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation ("io.grpc:grpc-core:1.40.0")
    implementation ("io.ktor:ktor-network-tls-certificates")

}
