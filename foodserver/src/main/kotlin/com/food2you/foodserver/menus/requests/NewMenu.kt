package com.food2you.foodserver.menus.requests

import jakarta.validation.constraints.NotBlank

data class NewMenu (
    @NotBlank
    var name: String,
)
