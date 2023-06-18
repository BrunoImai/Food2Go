package com.food2you.foodserver.restaurant.response

import com.food2you.foodserver.costumer.Costumer
import com.food2you.foodserver.restaurant.Restaurant
import org.springframework.lang.NonNull

data class RestaurantLoginResponse(
    @NonNull
    val token: String,

    @NonNull
    val restaurant: Restaurant

)
