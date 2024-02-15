package com.food.database.model

import kotlinx.serialization.Serializable

@Serializable
data class Question (
    val question: String,
    val answer: String,
    val number: Int,
        )
