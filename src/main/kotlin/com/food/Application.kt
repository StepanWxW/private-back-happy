package com.food

import com.food.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database

fun main() {
    val localhost = "0.0.0.0"
    val ip = "94.228.125.138"
    Database.connect("jdbc:postgresql://${ip}:5432/my", driver = "org.postgresql.Driver",
    "stepan", "stepan")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
