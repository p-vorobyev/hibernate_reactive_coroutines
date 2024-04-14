package dev.voroby.hibernate_reactive.repository

import dev.voroby.hibernate_reactive.AbstractBootTest
import dev.voroby.hibernate_reactive.entity.PetType
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class HumanRepositoryImplTest: AbstractBootTest() {

    @Autowired
    private lateinit var humanRepository: HumanRepository

    @Test
    fun findAll() {
        runBlocking {
            humanRepository.findAll().apply {
                assertTrue { size == 1 }
                val pet = get(0).pets.iterator().next()
                assertEquals(PetType.CAT, pet.type)
            }
        }
    }

}