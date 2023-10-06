
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
//    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
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
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-jackson-jvm")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.slf4j:slf4j-api:$1.7.36")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

//    implementation("io.ktor:ktor-serialization-kotlin-json:$ktor_version")
    implementation ("io.ktor:ktor-server-netty:1.3.2-1.4-M2")
    implementation ("org.jetbrains.exposed:exposed-core:0.44.0")
    implementation ("org.jetbrains.exposed:exposed-dao:0.44.0")
    implementation ("org.jetbrains.exposed:exposed-jdbc:0.44.0")
    implementation ("org.postgresql:postgresql:42.6.0")
}
