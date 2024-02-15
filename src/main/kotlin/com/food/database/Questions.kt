package com.food.database


import com.food.database.model.Question
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Questions : IntIdTable("happy.questions") {
    var question = text("question")
    var answer = text("answer")
    var number = integer("number")

    fun getAll(): List<Question> {
        return transaction {
            Questions.selectAll().map {
                Question(
                   question = it[question],
                   answer = it[answer],
                   number = it[number]
                )
            }
        }
    }
}
