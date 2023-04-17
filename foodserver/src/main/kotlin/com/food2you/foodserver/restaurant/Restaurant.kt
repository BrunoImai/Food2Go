package com.food2you.foodserver.restaurant

import com.food2you.foodserver.menus.Menu
import com.food2you.foodserver.orders.Order
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "restaurant")
data class Restaurant (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    @NotBlank
    open val name: String,

    @NotBlank
    open val status: String,

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orders : MutableList<Order>,

    @OneToMany(mappedBy = "restaurantMenu", cascade = [CascadeType.ALL], orphanRemoval = true)
    val menus : MutableList<Menu>

)