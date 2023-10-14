package com.food

import com.food.plugins.configureRouting
import com.food.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    val localhost = "127.0.0.1"
    val ip = "likefast.ru"
    Database.connect("jdbc:postgresql://${localhost}:5432/my", driver = "org.postgresql.Driver",
    "StepanJava", "Ilovedota70")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
