package com.food2you.foodserver.menus

import com.food2you.foodserver.product.Product
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "menu")
class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    @NotBlank
    var name: String,

    @NotBlank
    var restaurant : Long,

    @ManyToMany
    @JoinTable(name = "menu_products", joinColumns = [JoinColumn(name = "menu_id")], inverseJoinColumns = [JoinColumn(name = "product_id")])
    var products: MutableSet<Product>?
)