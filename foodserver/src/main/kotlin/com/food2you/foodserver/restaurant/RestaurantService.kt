package com.food2you.foodserver.restaurant

import com.food2you.foodserver.costumer.Costumer
import com.food2you.foodserver.menus.Menu
import com.food2you.foodserver.menus.MenuRepository
import com.food2you.foodserver.orders.Order
import com.food2you.foodserver.orders.OrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
data class RestaurantService (
    val restaurantRepository: RestaurantRepository,
    val orderRepository: OrderRepository,
    val menuRepository: MenuRepository
) {
    private val logger = LoggerFactory.getLogger(Costumer::class.java)

    fun createRestaurant(restaurant: Restaurant) = restaurantRepository.save(restaurant)

    fun findAllRestaurants() = restaurantRepository.findAll()

    fun findAllOrders(restaurantId: Long) : MutableList<Order> = orderRepository.findAllByRestaurant(restaurantId)

    fun createMenu(menu: Menu, restaurantId: Long) : Menu {
        menu.restaurantMenu = restaurantId
        return menuRepository.save(menu)
    }

}
