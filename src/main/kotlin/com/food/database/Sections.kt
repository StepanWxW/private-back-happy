package com.food.database


import com.food.controller.Addition
import com.food.controller.Cost
import com.food.controller.Product
import com.food.controller.ProductPicture
import com.food.controller.Section
import com.food.controller.SectionsAndProductsResponse
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Sections : IntIdTable() {
    private val name = varchar("name", 100)
    private val place = integer("place")
    private val shopId = integer("shop_id").references(Shops.id)

    fun getShopSectionsAndAllProductsFirstSection(shopId: Int): SectionsAndProductsResponse {
        val sections =  transaction {
            Sections.select { this@Sections.shopId eq shopId }
                .map {
                    Section(
                        it[Sections.id].value,
                        it[name],
                        it[place],
                        it[this@Sections.shopId]
                    )
                }
        }
        val firstIdSection = sections[0].id
        val products = transaction {
            Products.innerJoin(Sections)
                .select {
                    (this@Sections.shopId eq shopId) and
                            (Sections.id eq firstIdSection)
                }.map { it ->
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
                        it[Products.name],
                        it[Products.description],
                        it[Products.sectionId],
                        productCosts,
                        productAdditions,
                        productPictures
                    )
                }
        }

        return SectionsAndProductsResponse(
            sections = sections,
            products = products
        )
    }
}