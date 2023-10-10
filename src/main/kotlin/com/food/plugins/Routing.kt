package com.food.plugins


import com.food.database.Sections.getShopSectionsAndAllProductFirstSection
import com.food.database.Shops.getAllShops
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receiveParameters
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
        get("/tomsk/shops/sectionsAndProducts") {
            val shopId = call.parameters["shop_id"]?.toIntOrNull()
                ?: throw IllegalArgumentException("shop_id parameter is missing or invalid")
            val sections = getShopSectionsAndAllProductFirstSection(shopId)
            call.respond(HttpStatusCode.OK, sections)
        }
    }
}
