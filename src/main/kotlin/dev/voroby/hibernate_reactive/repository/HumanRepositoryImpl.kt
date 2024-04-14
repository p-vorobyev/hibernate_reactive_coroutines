package dev.voroby.hibernate_reactive.repository

import dev.voroby.hibernate_reactive.entity.Human
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.stereotype.Component

@Component
class HumanRepositoryImpl(private val sessionFactory: SessionFactory): HumanRepository {

    override suspend fun findAll(): List<Human> =
        sessionFactory.withTransaction { session, tx ->
            session.createQuery("select h from Human h join fetch h.pets", Human::class.java).resultList
        }.awaitSuspending()

}