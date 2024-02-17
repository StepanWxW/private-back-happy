package com.food.database

import com.food.domain.model.User
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object Users : IntIdTable("happy.users"){
    var uid = Users.text("uid")
    var token = Users.text("token")

    fun getUser(uid: String): User? {
        return transaction {
            val user = Users.select { Users.uid eq uid }.firstOrNull() ?: return@transaction null
            User(
                uid = user[Users.uid],
                token = user[token]
            )
        }
    }
    fun createOrUpdateUser(user: User) {
        transaction {
            val existingUser = Users.select { uid eq user.uid }.singleOrNull()
            if (existingUser != null) {
                Users.update({ uid eq user.uid }) {
                    it[token] = user.token
                }
            } else {
                Users.insert {
                    it[uid] = user.uid
                    it[token] = user.token
                }
            }
        }
    }

    fun deleteToken(uid: String) {
        transaction {
            Users.update({ Users.uid eq uid }) {
                it[token] = ""
            }
        }
    }
}
