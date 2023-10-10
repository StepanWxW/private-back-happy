package com.food.database

data class SectionsAndProductsResponse(
    val sections: List<Section>,
    val products: List<Product>
)