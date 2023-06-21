package com.food2you.foodserver.restaurant

import com.fasterxml.jackson.annotation.JsonIgnore
import com.food2you.foodserver.combo.Combo
import com.food2you.foodserver.menus.Menu
import com.food2you.foodserver.orders.Order
import com.food2you.foodserver.product.Product
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


@Entity
@Table(name = "restaurant")
data class Restaurant (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    @NotBlank
    val name: String,

    @NotBlank
    val email: String,

    @NotBlank
    val password: String,

    @NotBlank
    val status: String,

    @NotNull
    var restaurantImage: String,

    @get:JsonIgnore
    @ElementCollection
    val roles: MutableSet<String>,

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orders : MutableList<Order>,

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val menus : MutableList<Menu>,

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val products : MutableList<Product>,

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL], orphanRemoval = true)
    val combos : MutableList<Combo>
)