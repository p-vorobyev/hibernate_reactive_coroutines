package dev.voroby.hibernate_reactive.repository

import dev.voroby.hibernate_reactive.entity.Human

interface HumanRepository {

    suspend fun findAll(): List<Human>

}