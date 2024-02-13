package com.food.plugins


import com.food.database.Events
import com.food.database.model.MyEvent
import com.food.database.model.MyStatus
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Здорово!")
        }
    }
    routing {
        post("/event/") {
            try {
                val event = call.receive<MyEvent>()
                Events.insertEvent(event)
                call.respond(HttpStatusCode.OK, MyStatus(true))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format ${e.message}")
            }
        }
    }

    routing {
        get("/getAll/") {
            try {
                val uid = call.parameters["uid"]
                if (uid != null) {
                    val events = Events.getAllOfUid(uid)
                    call.respond(HttpStatusCode.OK, events)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Parameter 'uid' is missing")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format ${e.message}")
            }
        }
    }

    routing {
        patch("/update/") {
            try {
                val event = call.receive<MyEvent>()
                Events.updateEvent(event)
                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format ${e.message}")
            }
        }
    }

    routing {
        delete ("/delete/") {
            try {
                val uid = call.parameters["uid"]
                val id = call.parameters["id"]
                if (uid != null && id != null) {
                    Events.deleteEvent(uid, id.toInt())
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Parameter 'uid' or 'id' is missing")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format ${e.message}")
            }
        }
    }

    routing {
        get("/e/") {

            val events = Events.getAllOfDate(8,10,15)
            if(events.isEmpty()){
                println("пустой")
            } else {
                for(event in events) {
                    println(event)
                }
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
