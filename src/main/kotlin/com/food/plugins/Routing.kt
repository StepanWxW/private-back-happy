package com.food.plugins


import com.food.database.Products.getProductsInShopSection
import com.food.database.Sections.getShopSectionsAndAllProductsFirstSection
import com.food.database.Shops.getAllShops
import io.ktor.http.HttpStatusCode
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
        get("/tomsk/shops/shop_section_first/") {
            val shopId = call.parameters["shop_id"]?.toIntOrNull()
                ?: throw IllegalArgumentException("shop_id parameter is missing or invalid")
            val sections = getShopSectionsAndAllProductsFirstSection(shopId)
            call.respond(HttpStatusCode.OK, sections)
        }
    }

    routing {
        get("/tomsk/shops/section/") {
            val sectionId = call.parameters["section_id"]?.toIntOrNull()
                ?: throw IllegalArgumentException("section_id parameter is missing or invalid")
            val sections = getProductsInShopSection(sectionId)
            call.respond(HttpStatusCode.OK, sections)
        }
    }
}
