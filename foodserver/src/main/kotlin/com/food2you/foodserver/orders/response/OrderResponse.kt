package com.food2you.foodserver.orders.response

import com.food2you.foodserver.product.Product

data class OrderResponse(

    var id: Long,

    var status: String,

    var costumerName : String,

    var products : MutableList<Product>,

    val customerMobilePhone: String
)
