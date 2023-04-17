package com.food2you.foodserver.orders

import com.food2you.foodserver.costumer.CostumerRepository
import org.springframework.stereotype.Service
import kotlin.NullPointerException

@Service
data class OrderService(
    val repository: OrderRepository,
    val costumerRepository: CostumerRepository
) {
    fun createOrder(order : Order) {
        repository.save(order)

    }
}
