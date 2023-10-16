package com.food.database

import com.food.controller.ShopDto
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Shops : IntIdTable("public.shops") {
    private val name = varchar("name", 100)
    private val address = varchar("address", 50)
    private val description = text("description")
    private val imgUrl = text("img_url")
    private val cityId = reference("city_id", Cities)
    private val isActive = bool("is_active")
    private val isWorkNow = bool("is_work_now")
    private val delivery = bool("delivery")
    private val timeWork = varchar("time_work",20)

    fun getAllShops(): List<ShopDto> {
        return transaction {
            Shops.selectAll().map {
                ShopDto(
                    id = it[Shops.id].value,
                    name = it[name],
                    address = it[address],
                    description = it[description],
                    imgUrl = it[imgUrl],
                    cityId = it[cityId].value,
                    isActive = it[isActive],
                    isWorkNow = it[isWorkNow],
                    delivery = it[delivery],
                    timeWork = it[timeWork]
                )
            }
        }
    }
}