package dev.voroby.hibernate_reactive.web

import dev.voroby.hibernate_reactive.entity.Human
import dev.voroby.hibernate_reactive.repository.HumanRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//https://thorben-janssen.com/hibernate-reactive-getting-started-guide/
@RestController
@RequestMapping("/api")
class Controller(private val humanRepository: HumanRepository) {

    @GetMapping(value = ["/human"], produces = [APPLICATION_JSON_VALUE])
    suspend fun humans(): ResponseEntity<List<Human>?> = humanRepository
        .findAll()
        .awaitFirstOrNull()
        .orEmpty()
        .let { ResponseEntity.ok(it) }

}