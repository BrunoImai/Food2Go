package com.food2you.foodserver.orders

import com.food2you.foodserver.costumer.Costumer
import com.food2you.foodserver.product.Product
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank



@Entity
@Table(name="Orders")
class  Order   (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    @NotBlank
    var name: String,

    @ManyToOne
    var costumer : Costumer?,

    var restaurant : Long,

    @OneToMany
    var products : MutableList<Product>

)

