package com.food2you.foodserver.restaurant.response
import jakarta.validation.constraints.NotBlank


data class RestaurantUpdateResponse(
    @NotBlank
    val name: String,

    @NotBlank
    val email: String,

    @NotBlank
    val roles: MutableSet<String>,

    @NotBlank
    val restaurantImage: String
)