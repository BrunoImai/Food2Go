package com.food2you.foodserver.costumer

import com.food2you.foodserver.orders.Order
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.codehaus.jackson.annotate.JsonIgnore
import org.hibernate.validator.constraints.UniqueElements

@Entity
data class Costumer(
    @Id @GeneratedValue
    val id: Long?,

    @NotBlank
    val name: String,

    @NotNull
    val mobilePhone: String = "",

    @Email
    @Column(unique=true)
    val email: String,

    val password: String,

    @JsonIgnore
    @OneToMany(mappedBy = "costumer", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orders: MutableList<Order>,

    @ElementCollection
    val roles: MutableSet<String>
)
