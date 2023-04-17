package com.food2you.foodserver.costumer.response

import com.food2you.foodserver.costumer.Costumer
import org.springframework.lang.NonNull

data class LoginResponse(
    @NonNull
    val token: String,

    @NonNull
    val costumer : Costumer

)
