package dev.voroby.hibernate_reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HibernateReactiveApplication

fun main(args: Array<String>) {
    runApplication<HibernateReactiveApplication>(*args)
}
