package com.food.database

import org.jetbrains.exposed.dao.id.IntIdTable

object Costs : IntIdTable() {
    val name = varchar("name", 50)
    val cost = double("cost")
    val productId = integer("product_id").references(Products.id)
}
