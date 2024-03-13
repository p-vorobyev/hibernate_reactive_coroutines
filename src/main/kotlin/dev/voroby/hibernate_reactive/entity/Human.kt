package dev.voroby.hibernate_reactive.entity

import jakarta.persistence.*

@Entity
@Table(name = "humans")
class Human {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "human_seq", sequenceName = "human_seq", allocationSize = 1)
    var id: Long? = null

    var name: String? = null

    @OneToMany(
        mappedBy = "human",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE],
        orphanRemoval = true
    )
    var pets: MutableSet<Pet> = mutableSetOf()


    override fun hashCode(): Int {
        return this::class.java.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Human

        return id == other.id
    }
}