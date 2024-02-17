package com.food.domain

import com.food.database.Events
import com.food.domain.model.MyEvent
import com.food.plugins.TIMEZONE
import java.time.LocalDateTime

class EventUseCase {
    fun saveEvent(event: MyEvent) {
        val diffTime = event.timeZone.minus(TIMEZONE)
        workWithTimeZone(event, diffTime)
        Events.insertEvent(event)
    }
    fun update(event: MyEvent) {
        val diffTime = event.timeZone.minus(TIMEZONE)
        workWithTimeZone(event, diffTime)
        Events.updateEvent(event)
    }

    private fun workWithTimeZone(event: MyEvent, diffTime: Int) {
        val customDateTime = LocalDateTime.of(
            event.year.toInt(),
            event.month.toInt(),
            event.day.toInt(),
            event.hour.toInt(),
            0
        )
        val laterDateTime = customDateTime.minusHours(diffTime.toLong())
        event.year = laterDateTime.year.toLong()
        event.month = laterDateTime.month.value.toLong()
        event.day = laterDateTime.dayOfMonth.toLong()
        event.hour = laterDateTime.hour.toLong()
    }
}