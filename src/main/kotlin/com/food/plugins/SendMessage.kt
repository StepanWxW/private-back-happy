package com.food.plugins

import com.food.database.Events
import com.food.database.Users
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class SendMessage {
    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (isStartOfNewHour()) {
                    CoroutineScope(Dispatchers.IO).async { executeHourlyTask() }.await()
                }
                delay(60000)
            }
        }
    }
    private suspend fun executeHourlyTask() {
        val now = LocalDateTime.now()
        val events =
            CoroutineScope(Dispatchers.IO).async {
                Events.getAllOfDate(
                    now.month.toString().toLong(),
                    now.dayOfMonth.toString().toLong(),
                    now.hour.toString().toLong()
                )
            }.await()
        for(event in events) {
            val uid = event.uid
            val user =
                CoroutineScope(Dispatchers.IO).async {
                    Users.getUser(uid)
                }.await()
            if(user != null) {
                val builder = Notification.builder()
                builder.setTitle("${event.lastName} ${event.firstName} ${event.patronymic}")
                builder.setBody("День рождение!!!")
                val notification = builder.build()
                try {
                    val message = Message.builder()
                        .setToken(user.token)
                        .setNotification(notification)
                        .build()
                    FirebaseMessaging.getInstance().send(message)
                } catch (e: Exception) {

                }
            }
        }
    }
    private fun isStartOfNewHour(): Boolean {
        val now = LocalDateTime.now()
        return now.truncatedTo(ChronoUnit.HOURS) != now.minusMinutes(1).truncatedTo(ChronoUnit.HOURS)
    }
}