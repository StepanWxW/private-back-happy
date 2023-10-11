package com.food.controller

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val sectionId: Int,
    val costs: List<Cost>,
    val additions: List<Addition>,
    val productPictures: List<ProductPicture>
)
