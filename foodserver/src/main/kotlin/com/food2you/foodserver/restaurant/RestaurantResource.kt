package com.food2you.foodserver.restaurant

import com.food2you.foodserver.costumer.CostumerRepository
import com.food2you.foodserver.menus.Menu
import com.food2you.foodserver.orders.OrderService
import jakarta.annotation.security.RolesAllowed
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurant")
data class RestaurantResource(
    private val restaurantService: RestaurantService,
    private val orderService: OrderService
) {
    @PostMapping
    fun createRestaurant(@RequestBody @Valid restaurant : Restaurant) = status(HttpStatus.CREATED).body(restaurantService.createRestaurant(restaurant))

    @PostMapping("/{restaurantId}/menu")
    fun createMenu(@RequestBody @Valid menu: Menu, @PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.createMenu(menu,restaurantId))

    @GetMapping("/{restaurantId}/orders")
    fun getAllOrders(@PathVariable @Valid restaurantId: Long) = status(HttpStatus.OK).body(restaurantService.findAllOrders(restaurantId))

    @GetMapping
    fun getAllRestaurants() = status(HttpStatus.OK).body(restaurantService.findAllRestaurants())
}
