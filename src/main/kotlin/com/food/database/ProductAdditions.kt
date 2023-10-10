package com.food.database

import org.jetbrains.exposed.dao.id.IntIdTable

object ProductAdditions : IntIdTable("public.product_addition") {
    val productId = integer("product_id").references(Products.id)
    val additionId = integer("addition_id").references(Additions.id)
}