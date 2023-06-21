package com.food2you.foodserver.restaurant.requests

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

data class NewRestaurant (
    @NotBlank
    val name: String,

    @NotBlank
    val email: String,

    @NotBlank
    val password: String,

    @NotBlank
    val roles: MutableSet<String>,

    @NotBlank
    val restaurantImage: String
)
