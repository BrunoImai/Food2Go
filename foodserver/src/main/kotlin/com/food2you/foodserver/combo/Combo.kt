package com.food2you.foodserver.combo

import com.food2you.foodserver.product.Product
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name="combo")
open class Combo (
    @Id @GeneratedValue
    var id: Long?,

    @NotNull
    var price: Double,

    @NotNull
    var restaurant: Long,

    @ManyToMany
    @JoinTable(name = "combo_products", joinColumns = [JoinColumn(name = "combo_id")], inverseJoinColumns = [JoinColumn(name = "product_id")])
    var products: MutableSet<Product>?
)