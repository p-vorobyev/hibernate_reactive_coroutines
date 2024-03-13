package dev.voroby.hibernate_reactive.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "pets")
class Pet() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "human_seq", sequenceName = "human_seq", allocationSize = 1)
    var id: Long? = null

    @Enumerated(EnumType.STRING)
    var type: PetType = PetType.UNKNOWN

    @Column(nullable = false)
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "human_id")
    @JsonIgnore
    var human: Human? = null

    constructor(type: PetType = PetType.UNKNOWN, name: String?) : this() {
        this.name = name
        this.type = type
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pet

        return id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }


}

enum class PetType {
    CAT,
    DOG,
    FISH,
    SNAKE,
    UNKNOWN
}