package dev.voroby.hibernate_reactive.repository

import dev.voroby.hibernate_reactive.config.Config
import dev.voroby.hibernate_reactive.entity.PetType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [Config::class, HumanRepositoryImpl::class])
class HumanRepositoryImplKotest(
    private var humanRepository: HumanRepository
) : StringSpec({

    "humanRepository findAll()" {
        humanRepository.findAll().apply {
            size shouldBe 1
            elementAt(0).pets.elementAt(0).type shouldBe PetType.CAT
        }
    }

})