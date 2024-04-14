package dev.voroby.hibernate_reactive.config

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import dev.voroby.hibernate_reactive.Log
import jakarta.annotation.PreDestroy
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import org.testcontainers.utility.DockerImageName
import java.util.concurrent.TimeUnit

@Configuration
class Config {

    companion object: Log()

    private val postgresql = GenericContainer(DockerImageName.parse("postgres:15.1-alpine3.17"))

    @PreDestroy
    fun onShutdown() {
        postgresql.stop()
        log.info("PostgreSQL stopped")
    }

    @Bean
    fun sessionFactory(): SessionFactory {
        initContainer()
        val entityManagerFactory: EntityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit")
        return entityManagerFactory.unwrap(SessionFactory::class.java)
    }

    private fun initContainer() {
        val envs = mapOf(
            "POSTGRES_USER" to "user",
            "POSTGRES_PASSWORD" to "password",
            "POSTGRES_DB" to "pgDatabase"
        )
        postgresql
            .withEnv(envs)
            .withCreateContainerCmdModifier {
                it.withHostConfig(
                    HostConfig().withPortBindings(PortBinding(Ports.Binding.bindPort(5432), ExposedPort(5432)))
                )
            }
            .waitingFor(HostPortWaitStrategy().forPorts(5432))
            .start()
        TimeUnit.SECONDS.sleep(3) // await database
        log.info("PostgreSQL started")
    }

}