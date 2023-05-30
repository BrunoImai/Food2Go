package com.food2you.foodserver.restaurant

import com.food2you.foodserver.combo.Combo
import com.food2you.foodserver.menus.Menu
import com.food2you.foodserver.orders.Order
import com.food2you.foodserver.product.Product
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "restaurant")
data class Restaurant (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    @NotBlank
    val name: String,

    @NotBlank
    val status: String,

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orders : MutableList<Order>,

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val menus : MutableList<Menu>,

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val products : MutableList<Product>,

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val combos : MutableList<Combo>

)