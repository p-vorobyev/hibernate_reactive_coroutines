package dev.voroby.hibernate_reactive.repository

import dev.voroby.hibernate_reactive.entity.Human
import reactor.core.publisher.Mono

interface HumanRepository {

    fun findAll(): Mono<List<Human>>

}