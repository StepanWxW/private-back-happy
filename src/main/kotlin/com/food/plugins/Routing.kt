package com.food.plugins

import com.food.database.Events
import com.food.database.Questions
import com.food.database.Users
import com.food.domain.EventUseCase
import com.food.domain.model.MyEvent
import com.food.domain.model.MyStatus
import com.food.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

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
                val tokenIs = CoroutineScope(Dispatchers.IO).async { tokenValid(event.uid) }.await()
                if (tokenIs) {
                    EventUseCase().saveEvent(event)
                    call.respond(HttpStatusCode.OK, MyStatus(true))
                }
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

                    val tokenIs = CoroutineScope(Dispatchers.IO).async { tokenValid(uid) }.await()
                    if (tokenIs) {
                        val events = Events.getAllOfUid(uid)
                        call.respond(HttpStatusCode.OK, events)
                    }
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
                val tokenIs = CoroutineScope(Dispatchers.IO).async { tokenValid(event.uid) }.await()
                if (tokenIs) {
                    Events.updateEvent(event)
                    call.respond(HttpStatusCode.OK, MyStatus(true))
                }
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
                    val tokenIs = CoroutineScope(Dispatchers.IO).async { tokenValid(uid) }.await()
                    if (tokenIs) {
                        Events.deleteEvent(uid, id.toInt())
                        call.respond(HttpStatusCode.OK, MyStatus(true))
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Parameter 'uid' or 'id' is missing")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format ${e.message}")
            }
        }
    }
    routing {
        post("/token/") {
            try {
                val uid = call.parameters["uid"]
                val token = call.parameters["token"]
                if (uid != null && token != null) {
                    val tokenIs = CoroutineScope(Dispatchers.IO).async { tokenValid(uid) }.await()
                    if (tokenIs) {
                        Users.createOrUpdateUser(User(uid,token))
                        call.respond(HttpStatusCode.OK, MyStatus(true))
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Parameter 'uid' or 'token' is missing")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format ${e.message}")
            }
        }
    }

    routing {
        delete("/delete_token/") {
            try {
                val uid = call.parameters["uid"]
                if (uid != null) {
                    val tokenIs = CoroutineScope(Dispatchers.IO).async { tokenValid(uid) }.await()
                    if (tokenIs) {
                        Users.deleteToken(uid)
                        call.respond(HttpStatusCode.OK, MyStatus(true))
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Parameter 'uid' or 'id' is missing")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format ${e.message}")
            }
        }
    }

    routing {
        get("/questions/") {
            try {
                val questions = Questions.getAll()
                println(questions)
                val questionSort = questions.sortedBy { it.number }
                println(questions)
                call.respond(HttpStatusCode.OK, questionSort)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format ${e.message}")
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.tokenValid(uid: String): Boolean {
    val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
    if (token != null) {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(token)
        val uidFB = decodedToken.uid
        return uid == uidFB
    }
    return false
}