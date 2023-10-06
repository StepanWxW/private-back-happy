package com.food.plugins


import com.food.database.Shops.getAllShops
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    routing {
        get("/tomsk/shops/") {
            val shops = getAllShops()
            call.respond(shops)
        }
    }
    routing {
        get("/tomsk") {
            call.respondText("Text Tomsk")
        }
    }
}
