package com.food2you.foodserver.menus

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "menu")
open class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long?,

    @NotBlank
    open var name: String,

    @NotBlank
    open var restaurantMenu : Long
    )