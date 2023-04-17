package com.food2you.foodserver.costumer.requests

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.codehaus.jackson.annotate.JsonIgnore

data class CostumerRequest(
    @NotBlank
    val name: String,
    
    val mobilePhone: String?,

    @Email
    val email: String,

    @NotNull
    @JsonIgnore
    val password: String
)
