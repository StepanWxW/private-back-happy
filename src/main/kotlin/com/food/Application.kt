package com.food

import com.food.plugins.CreateTable
import com.food.plugins.configureRouting
import com.food.plugins.configureSerialization
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import java.io.FileInputStream

fun main() {
    val localhost = "127.0.0.1"
    val ip = "likefast.ru"
    Database.connect("jdbc:postgresql://${ip}:5432/my", driver = "org.postgresql.Driver",
    "StepanJava", "Ilovedota70")

    CreateTable().create()

    val serviceAccount = FileInputStream("happystepanwxw-firebase-adminsdk-vhvnu-597c529cf4.json")
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl("https://happystepanwxw.firebaseio.com")
        .build()

    FirebaseApp.initializeApp(options)

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
