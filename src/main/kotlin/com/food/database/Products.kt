package com.food.database

import org.jetbrains.exposed.dao.id.IntIdTable

object Products : IntIdTable() {
    val name = varchar("name", 100)
    val description = text("description")
    val sectionId = integer("section_id").references(Sections.id)
    val place = integer("place")
}