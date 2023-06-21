package com.food2you.foodserver.orders.request

import jakarta.validation.constraints.NotBlank

data class OrderRequest(
    var name: String,
    var products : MutableList<Long>
)