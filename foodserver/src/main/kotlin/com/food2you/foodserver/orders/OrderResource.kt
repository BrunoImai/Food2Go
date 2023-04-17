package com.food2you.foodserver.orders

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity.status

@RestController
@RequestMapping("/customersOrders")
data class OrderResource(
    private val service: OrderService
) {


}

