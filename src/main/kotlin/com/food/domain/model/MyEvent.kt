package com.food.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
@Serializable
data class MyEvent(
    var id: Int? = null,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("patronymic")
    val patronymic: String,
    @SerializedName("telephone")
    val telephone: Long,
    var year: Long,
    var month: Long,
    var day: Long,
    var hour: Long,
    val timeZone: Int
)