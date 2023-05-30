package com.food2you.foodserver.restaurant

import com.food2you.foodserver.menus.requests.NewMenu
import com.food2you.foodserver.orders.OrderService
import com.food2you.foodserver.product.Product
import com.food2you.foodserver.restaurant.requests.NewRestaurant
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurant")
data class RestaurantResource(
    private val restaurantService: RestaurantService,
    private val orderService: OrderService
) {
    @PostMapping
    fun createRestaurant(@RequestBody @Valid restaurant : NewRestaurant) = status(HttpStatus.CREATED).body(restaurantService.createRestaurant(restaurant))

    @PostMapping("/{restaurantId}/menu")
    fun createMenu(@RequestBody @Valid menu: NewMenu, @PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.createMenu(menu,restaurantId))

    @PostMapping("/{restaurantId}/product")
    fun addProductToRestaurant(@RequestBody @Valid product: Product, @PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.addProduct(product, restaurantId))

    @PostMapping("/{restaurantId}/{menuId}/{productId}")
    fun addProductToMenu(@PathVariable productId: Long, @PathVariable menuId: Long) = status(HttpStatus.CREATED).body(restaurantService.addProductToMenu (productId, menuId))

    @GetMapping("/{restaurantId}/products")
    fun getAllProducts(@PathVariable restaurantId: Long) = status(HttpStatus.CREATED).body(restaurantService.getAllProducts (restaurantId))

    @GetMapping("/{restaurantId}/orders")
    fun getAllOrders(@PathVariable @Valid restaurantId: Long) = status(HttpStatus.OK).body(restaurantService.getAllOrders(restaurantId))

    @GetMapping("/{restaurantId}/{orderId}/products")
    fun getAllOrdersProducts( @PathVariable orderId: Long) = status(HttpStatus.OK).body(restaurantService.getAllProductsFromOrder(orderId))

    @GetMapping
    fun getAllRestaurants() = status(HttpStatus.OK).body(restaurantService.findAllRestaurants())

    @GetMapping("/{restaurantId}")
    fun getAllMenus(@PathVariable @Valid restaurantId: Long) = status(HttpStatus.OK).body(restaurantService.getAllMenus(restaurantId))
}
