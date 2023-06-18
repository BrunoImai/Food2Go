package com.food2you.foodserver.security.tokens

data class CostumerToken(
    val id: Long,
    val email: String,
    val nome: String,
    val roles: MutableSet<String>

)
