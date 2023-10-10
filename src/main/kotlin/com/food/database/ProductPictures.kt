package com.food.database

import org.jetbrains.exposed.dao.id.IntIdTable

object ProductPictures : IntIdTable("public.product_picture") {
    val imgUrl = text("img_url")
    val productId = integer("product_id").references(Products.id)
}