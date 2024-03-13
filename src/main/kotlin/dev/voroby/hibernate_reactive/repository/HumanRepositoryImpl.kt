package dev.voroby.hibernate_reactive.repository

import dev.voroby.hibernate_reactive.entity.Human
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class HumanRepositoryImpl(private val sessionFactory: SessionFactory): HumanRepository {

    override fun findAll(): Mono<List<Human>> {
        val uni = sessionFactory.withTransaction { session, tx ->
            session.createQuery("select h from Human h join fetch h.pets", Human::class.java).resultList
        }
        return Mono.fromCompletionStage { uni.subscribeAsCompletionStage() }
    }

}