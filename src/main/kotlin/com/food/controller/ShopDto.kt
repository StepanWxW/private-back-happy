package com.food.controller

import com.food.database.Shops

class ShopDto(
    val id: Int,
    val name: String,
    val address: String,
    val description: String?,
    val imgUrl: String,
    val cityId: Int,
    val isActive: Boolean,
    val isWorkNow: Boolean,
    val delivery: Boolean,
    val timeWork: String
        )