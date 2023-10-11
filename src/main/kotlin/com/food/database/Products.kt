package com.food.database

import com.food.controller.Addition
import com.food.controller.Cost
import com.food.controller.Product
import com.food.controller.ProductPicture
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Products : IntIdTable() {
    val name = varchar("name", 100)
    val description = text("description")
    val sectionId = integer("section_id").references(Sections.id)
    val place = integer("place")

    fun getProductsInShopSection(sectionId: Int): List<Product> {
        val products = transaction {
            Products
                .select { Products.sectionId eq sectionId }
                .map { it ->
                    val productId = it[Products.id].value
                    val productCosts = Costs.select { Costs.productId eq productId }
                        .map {
                            Cost(
                                it[Costs.id].value,
                                it[Costs.name],
                                it[Costs.cost]
                            )
                        }

                    val productAdditions = transaction {
                        ProductAdditions.select { ProductAdditions.productId eq productId }
                            .map { paRow ->
                                val additionId = paRow[ProductAdditions.additionId]
                                val addition = Additions.select { Additions.id eq additionId }
                                    .map {
                                        Addition(
                                            it[Additions.id].value,
                                            it[Additions.name],
                                            it[Additions.cost],
                                            it[Additions.imgUrl]
                                        )
                                    }
                                addition.firstOrNull()
                            }.filterNotNull()
                    }

                    val productPictures =
                        ProductPictures.select { ProductPictures.productId eq productId }
                            .map {
                                ProductPicture(
                                    it[ProductPictures.id].value,
                                    it[ProductPictures.imgUrl]
                                )
                            }

                    Product(
                        productId,
                        it[name],
                        it[description],
                        it[Products.sectionId],
                        productCosts,
                        productAdditions,
                        productPictures
                    )
                }
        }

        return products
    }
}