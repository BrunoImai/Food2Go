package com.food2you.foodserver.costumer

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
data class Costumer(
    @Id @GeneratedValue
    val id: Long?,

    @NotBlank
    val name: String,

    @NotNull
    val mobilePhone: String = "",

    @Email
    val email: String,

    val password: String
)
