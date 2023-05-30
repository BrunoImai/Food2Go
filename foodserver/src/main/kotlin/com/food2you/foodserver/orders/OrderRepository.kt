package com.food2you.foodserver.orders;

import jakarta.validation.Valid
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderRepository : JpaRepository<Order, Long> {


    fun findAllByCostumer (id: Long) : MutableList<Order>

    fun findAllByRestaurant (id: Long) : MutableList<Order>


}