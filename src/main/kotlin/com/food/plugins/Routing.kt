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

    routing {
//        post("/insertData") {
//            try {
//                val orderData = call.receive<OrderRequest>()
//                transaction {
//                    // Вставляем данные в таблицу order_products_1
//                    val orderProductsId = tomsk.order_products_1.insertAndGetId {
//                        it[id_product] = orderData.idProduct
//                        it[count] = orderData.count
//                    }
//
//                    // Вставляем данные в таблицу order_additions_1
//                    tomsk.order_additions_1.insert {
//                        it[id_addition] = orderData.idAddition
//                        it[count] = 1
//                    }
//
//                    call.respond(HttpStatusCode.OK, Response("Data inserted successfully"))
//                }
//            } catch (e: IOException) {
//                call.respond(HttpStatusCode.BadRequest, Response("Invalid data format"))
//            }
//        }
    }
}
