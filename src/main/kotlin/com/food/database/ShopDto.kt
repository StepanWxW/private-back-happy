package com.food.database

class ShopDto(
    val id: Int,
    val name: String,
    val address: String,
    val description: String?,
    val imgUrl: String,
    val cityId: Int,
    val isActive: Boolean
        )