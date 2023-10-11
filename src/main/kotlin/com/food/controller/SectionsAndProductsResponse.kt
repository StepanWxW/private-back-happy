package com.food.controller

import com.food.controller.Product
import com.food.controller.Section

data class SectionsAndProductsResponse(
    val sections: List<Section>,
    val products: List<Product>
)