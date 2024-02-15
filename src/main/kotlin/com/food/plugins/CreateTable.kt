package com.food.plugins

import com.food.database.Events
import com.food.database.Questions
import com.food.database.Users
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.transactions.transaction

class CreateTable {
    fun create(){
        transaction {
            val eventsExists = Events.exists()
            if (!eventsExists) {
                SchemaUtils.create(Events)
            }
            val usersExists = Users.exists()
            if (!usersExists) {
                SchemaUtils.create(Users)
            }
            val questions = Questions.exists()
            if (!questions) {
                SchemaUtils.create(Questions)
            }
        }
    }
}

/*
CREATE TABLE happy.events (
    id SERIAL PRIMARY KEY,
    uid TEXT,
    time TIMESTAMP WITH TIME ZONE,
    first_name TEXT,
    last_name TEXT,
    patronymic TEXT,
    telephone BIGINT
);
 */