package com.food2you.foodserver.security

data class CostumerToken(
    val id: Long,
    val email: String,
    val nome: String,
    val roles: MutableList<String>

)
