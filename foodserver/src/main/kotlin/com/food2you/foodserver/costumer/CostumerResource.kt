package com.food2you.foodserver.costumer

import com.food2you.foodserver.costumer.requests.LoginRequest
import com.food2you.foodserver.orders.Order
import com.food2you.foodserver.orders.OrderService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/costumers")
class CostumerResource(
    private val costumerService: CostumerService,
    private val orderService: OrderService
) {
//    @PostMapping("/login")
//    fun login(@RequestBody loginRequest: LoginRequest) {
//        val login = costumerService.login(loginRequest)
//        if(login == null) status(HttpStatus.UNAUTHORIZED).body(loginRequest) else status(HttpStatus.OK)
//    }

    @PostMapping
    fun create(@RequestBody @Valid costumer: Costumer) =
        status(HttpStatus.CREATED).body(costumerService.createUser(costumer))

    @PostMapping("/order")
    fun create(@RequestBody @Valid order: Order, @Valid costumerId : Long, @Valid restaurantId : Long) {
        status(HttpStatus.CREATED).body(costumerService.createOrder(costumerId, order, restaurantId))
    }

    @DeleteMapping("/{costumerId}")
    fun delete(@Valid @PathVariable costumerId: Long) = status(HttpStatus.OK).body( costumerService.deleteCostumerById(costumerId))

    @GetMapping
    fun getAllCostumers() = status(HttpStatus.OK).body( costumerService.findAllCostumers())

    @GetMapping("{costumerId}/orders")
    fun getOrders(@Valid @PathVariable costumerId: Long) = status(HttpStatus.OK).body( costumerService.findAllOrders(costumerId))

    @GetMapping("/{costumerId}")
    fun getCostumerById(@Valid @PathVariable costumerId: Long) = status(HttpStatus.OK).body( costumerService.getCustomerById(costumerId))

    @PutMapping("/{costumerId}")
    fun updateCostumer(@Valid @PathVariable costumerId: Long, @RequestBody @Valid costumer: Costumer) {
        status(HttpStatus.CREATED).body(costumerService.createUserWithId(costumer, costumerId))
    }
}