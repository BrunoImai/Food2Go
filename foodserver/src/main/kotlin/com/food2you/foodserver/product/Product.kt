package com.food2you.foodserver.product


import com.food2you.foodserver.menus.Menu
import com.food2you.foodserver.orders.Order
import com.food2you.foodserver.restaurant.Restaurant
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import com.fasterxml.jackson.annotation.JsonIgnore
@Entity
@Table(name="product")
open class Product(
    @Id @GeneratedValue
    var id: Long?,

    @NotBlank
    var name: String,

    @NotNull
    var price: Double,

    @NotNull
    var qtt: Int,

    @NotNull
    var description: String?,

    @NotNull
    var restaurant: Long,

    @NotNull
    var productImage: String,

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    var menusIncluded : MutableSet<Menu>?,

)


