package com.food2you.foodserver.security.tokens

data class RestaurantToken(
    val id: Long,
    val email: String,
    val nome: String,
    val roles: Set<String>
) {
    constructor() : this(0, "", "", setOf())
}
