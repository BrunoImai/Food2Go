package com.food2you.foodserver.orders

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.food2you.foodserver.costumer.Costumer
import com.food2you.foodserver.restaurant.Restaurant
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank



@Entity
@Table(name="Orders")

data class  Order   (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,

    @NotBlank
    val name: String,

    var costumer : Long,

    var restaurant : Long

)

