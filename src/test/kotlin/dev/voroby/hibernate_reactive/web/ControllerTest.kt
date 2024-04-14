package dev.voroby.hibernate_reactive.web

import dev.voroby.hibernate_reactive.entity.Human
import dev.voroby.hibernate_reactive.entity.Pet
import dev.voroby.hibernate_reactive.entity.PetType
import dev.voroby.hibernate_reactive.repository.HumanRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [Controller::class])
class ControllerTest {

    @MockBean
    private lateinit var humanRepository: HumanRepository

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun humans() {
        runBlocking {
            val human = Human()
            human.id = 1
            human.name = "Ivan"
            human.pets = mutableSetOf(Pet(name = "Shhhii", type = PetType.SNAKE), Pet(name = "Nemo", type = PetType.FISH))
            Mockito.`when`(humanRepository.findAll()).thenReturn(listOf(human))

            val responseBody: MutableList<Human>? = webTestClient.get()
                .uri("/api/human/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Human::class.java)
                .returnResult().responseBody

            Mockito.verify(humanRepository).findAll()

            assertTrue { responseBody!!.size == 1 }
            assertEquals(human, responseBody!![0])
        }
    }

}