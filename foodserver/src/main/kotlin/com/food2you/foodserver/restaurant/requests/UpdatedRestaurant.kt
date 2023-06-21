package com.food2you.foodserver.restaurant.requests

import jakarta.validation.constraints.NotBlank

data class UpdatedRestaurant (
    @NotBlank
    val name: String,

    @NotBlank
    val email: String,

    @NotBlank
    val roles: MutableSet<String>,

    @NotBlank
    val restaurantImage: String
)