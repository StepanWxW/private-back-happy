package com.food.database

import com.food.domain.model.MyEvent
import com.food.plugins.TIMEZONE
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object Events : IntIdTable("happy.events") {
    var uid = text("uid")
    var firstName = text("first_name")
    var lastName = text("last_name")
    var patronymic = text("patronymic")
    var telephone = long("telephone")
    var year = long("year")
    var month = long("month")
    var day = long("day")
    var hour = long("hour")

    fun insertEvent(event: MyEvent) {
        transaction {
            Events.insert {
                it[uid] = event.uid
                it[firstName] =  event.firstName
                it[lastName] = event.lastName
                it[patronymic] = event.patronymic
                it[telephone] = event.telephone
                it[year] = event.year
                it[month] = event.month
                it[day] = event.day
                it[hour] = event.hour
            }
        }
    }

    fun getAllOfUid(uidSearch: String): List<MyEvent> {
        return transaction {
            Events.select { (uid eq uidSearch) }
                .map { row ->
                    MyEvent(
                        id = row[Events.id].value,
                        uid = row[uid],
                        firstName = row[firstName],
                        lastName = row[lastName],
                        patronymic = row[patronymic],
                        telephone = row[telephone],
                        year = row[year],
                        month = row[month],
                        day = row[day],
                        hour = row[hour],
                        timeZone = TIMEZONE
                    )
                }
                .toList()
        }
    }

    fun getAllOfDate(monthSearch: Long, daySearch: Long, hourSearch: Long): List<MyEvent> {
        return transaction {
            Events.select {
                (month eq monthSearch) and
                (day eq daySearch) and
                (hour eq hourSearch)
            }
                .map { row ->
                    MyEvent(
                        id = row[Events.id].value,
                        uid = row[uid],
                        firstName = row[firstName],
                        lastName = row[lastName],
                        patronymic = row[patronymic],
                        telephone = row[telephone],
                        year = row[year],
                        month = row[month],
                        day = row[day],
                        hour = row[hour],
                        timeZone = TIMEZONE
                    )
                }
                .toList()
        }
    }

    fun updateEvent(event: MyEvent) {
        transaction {
            Events.update({ Events.id eq event.id }) {
                it[firstName] =  event.firstName
                it[lastName] = event.lastName
                it[patronymic] = event.patronymic
                it[telephone] = event.telephone
                it[year] = event.year
                it[month] = event.month
                it[day] = event.day
                it[hour] = event.hour
            }
        }
    }

    fun deleteEvent(uid: String, id: Int) {
        transaction {
            Events.deleteWhere { Events.id eq id }
        }
    }
//    fun updateVideoZakaz(id: Int) {
//        transaction {
//            Vladelecs.update({ Vladelecs.id eq id }) {
//                it[videoZakaz] = true
//            }
//        }
//    }
//    fun updateVideoShowZakaz(id: Int) {
//        val x = id
//        transaction {
//            Vladelecs.update({ Vladelecs.id eq id }) {
//                it[videoShowZakaz] = true
//            }
//        }
//    }
//    fun updateVideoEnd(id: Int) {
//        transaction {
//            Vladelecs.update({ Vladelecs.id eq id }) {
//                it[videoEnd] = true
//            }
//        }
//    }
//    fun updateWork(id: Int, workIs: String) {
//        transaction {
//            Vladelecs.update({ Vladelecs.id eq id }) {
//                it[work] = workIs
//            }
//        }
//    }
//
//    fun updateContact(id: Int, contact: Contact) {
//        val phone = contact.phoneNumber.toLong()
//
//        val nameF = contact.firstName
//        transaction {
//            Vladelecs.update({ Vladelecs.id eq id }) {
//                it[telephone] = phone
//                it[name] = nameF
//            }
//        }
//    }
//    fun updateTimeBonus(id: Int, time: String, idBonusMsg: Long) {
//        transaction {
//            Vladelecs.update({ Vladelecs.id eq id }) {
//                it[timeBonus] = time
//                it[messageIdBonus] = idBonusMsg
//            }
//        }
//    }
//    fun updatePrice(id: Int) {
//        transaction {
//            Vladelecs.update({ Vladelecs.id eq id }) {
//                it[price] = true
//            }
//        }
//    }
//
//    fun searchVladelecByChatId(chatIdSearch: Long): Vladelec? {
//        return transaction {
//            val vladelec = Vladelecs.select { chatId eq chatIdSearch }.firstOrNull() ?: return@transaction null
//            Vladelec(
//                telephone = vladelec[telephone],
//                name = vladelec[name],
//                chatId = vladelec[chatId],
//                timeCreate = vladelec[timeCreate],
//                videoHi = vladelec[videoHi],
//                videoAdditional = vladelec[videoAdditional],
//                videoZakaz = vladelec[videoZakaz],
//                videoShowZakaz = vladelec[videoShowZakaz],
//                videoEnd = vladelec[videoEnd],
//                price = vladelec[price],
//                id = vladelec[Vladelecs.id].value,
//                work = vladelec[work],
//                timeBonus = vladelec[timeBonus],
//                messageIdBonus = vladelec[messageIdBonus]
//            )
//        }
//    }
//    fun getAllVladelecs(): List<Vladelec> {
//        return transaction {
//            Vladelecs.selectAll().map {
//                Vladelec(
//                    telephone = it[telephone],
//                    name = it[name],
//                    chatId = it[chatId],
//                    timeCreate = it[timeCreate],
//                    videoHi = it[videoHi],
//                    videoAdditional = it[videoAdditional],
//                    videoZakaz = it[videoZakaz],
//                    videoShowZakaz = it[videoShowZakaz],
//                    videoEnd = it[videoEnd],
//                    price = it[price],
//                    id = it[Vladelecs.id].value,
//                    work = it[work],
//                    timeBonus = it[timeBonus],
//                    messageIdBonus = it[messageIdBonus]
//                )
//            }
//        }
//    }
}