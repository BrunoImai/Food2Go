package com.food2you.foodserver.restaurant.requests

import jakarta.validation.constraints.NotBlank

data class RestaurantLoginRequest (
    @NotBlank
    val email: String,

    @NotBlank
    val password: String

)
