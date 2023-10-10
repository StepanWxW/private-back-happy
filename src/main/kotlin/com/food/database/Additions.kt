package com.food.database

import org.jetbrains.exposed.dao.id.IntIdTable

object Additions : IntIdTable() {
    val name = varchar("name", 50)
    val cost = double("cost")
    val imgUrl = text("img_url")
}