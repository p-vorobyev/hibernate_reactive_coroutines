package dev.voroby.hibernate_reactive.repository

import dev.voroby.hibernate_reactive.AbstractBootTest
import dev.voroby.hibernate_reactive.entity.PetType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier

class HumanRepositoryImplTest: AbstractBootTest() {

    @Autowired
    private lateinit var humanRepository: HumanRepository

    @Test
    fun findAll() {
        StepVerifier.create(humanRepository.findAll()).assertNext {
            assertTrue { it.size == 1 }
            val pet = it[0].pets.iterator().next()
            assertEquals(PetType.CAT, pet.type)
        }.expectComplete().verify()
    }

}