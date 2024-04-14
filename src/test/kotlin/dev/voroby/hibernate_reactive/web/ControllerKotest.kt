package dev.voroby.hibernate_reactive.web

import dev.voroby.hibernate_reactive.entity.Human
import dev.voroby.hibernate_reactive.entity.Pet
import dev.voroby.hibernate_reactive.entity.PetType
import dev.voroby.hibernate_reactive.repository.HumanRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [Controller::class])
class ControllerKotest(
    @MockBean
    private val humanRepository: HumanRepository,
    private val webTestClient: WebTestClient
) : StringSpec({


    "call GET /api/human/all" {
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

        responseBody!!.size shouldBe 1
        human shouldBeEqual responseBody.elementAt(0)
    }
})