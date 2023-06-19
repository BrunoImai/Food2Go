package com.food2you.foodserver.restaurant


import com.food2you.foodserver.combo.Combo
import com.food2you.foodserver.costumer.Costumer
import com.food2you.foodserver.menus.Menu
import com.food2you.foodserver.orders.Order
import com.food2you.foodserver.product.Product
import com.food2you.foodserver.restaurant.requests.NewRestaurant
import com.food2you.foodserver.restaurant.requests.RestaurantLoginRequest
import kotlin.random.Random

fun randomString(
    length: Int = 10, allowedChars: List<Char> =
        ('A'..'Z') + ('a'..'z') + ('0'..'9')
) = (1..length)
    .map { allowedChars.random() }
    .joinToString()

object Stubs {
    fun restaurantStubs(
        id: Long? = Random.nextLong(1, 1000),
    ): Restaurant {
        val name = "Restaurant-${id ?: "new"}"
        return Restaurant(
            id = id,
            name = name,
            email = "$name@email.com",
            password = randomString(),
            status = "Open",
            orders = mutableListOf<Order>(),
            menus = mutableListOf<Menu>(),
            products = mutableListOf<Product>(),
            combos = mutableListOf<Combo>(),
            roles = mutableSetOf<String>()
        )
    }

    fun newRestaurantStubs(
        id: Long? = Random.nextLong(1, 1000),
    ): NewRestaurant {
        val name = "Restaurant-${id ?: "new"}"
        return NewRestaurant(
            name = name,
            email = "$name@email.com",
            password = randomString(),
        )
    }

    fun restaurantLoginRequestStubs(
        id: Long? = Random.nextLong(1, 1000),
    ): RestaurantLoginRequest {
        val name = "Restaurant-${id ?: "new"}"
        return RestaurantLoginRequest(
            email = "$name@email.com",
            password = randomString(),
        )
    }

    fun costumerStubs(
        id: Long? = Random.nextLong(1, 1000),
    ): Costumer {
        val name = "Restaurant-${id ?: "new"}"
        return Costumer(
            id = id,
            email = "$name@email.com",
            password = randomString(),
            name = "Restaurant-${id ?: "new"}",
            orders = mutableListOf(),
            mobilePhone = "99999999",
            roles = mutableSetOf()
        )
    }

    fun menuStubs(
        id: Long? = Random.nextLong(1, 1000),
        restaurantId: Long = Random.nextLong(1, 1000),
    ): Menu {
        val name = "Restaurant-${id ?: "new"}"
        return Menu(
            id = id,
            restaurant = restaurantId,
            name = "Restaurant-${id ?: "new"}",
            products = mutableSetOf<Product>(),
        )
    }

    fun productStubs(
        id: Long? = Random.nextLong(1, 1000),
        restaurantId: Long = Random.nextLong(1, 1000),
        price: Double = Random.nextDouble(),
        qtt: Int = Random.nextInt(1, 1000),
    ): Product {
        return Product(
            id = id ?: 1,
            restaurant = restaurantId,
            name = "Product-${qtt}",
            price = price * 100,
            description = randomString(),
            qtt = qtt,
            combosIncluded = mutableSetOf(),
            menusIncluded = mutableSetOf()

        )
    }

    fun orderStubs(
        id: Long? = Random.nextLong(1, 1000),
        restaurantId: Long = Random.nextLong(1, 1000),
    ): Order {
        return Order(
            id = id,
            name = "Em andamento",
            costumer = costumerStubs(),
            restaurant = restaurantId,
            products =  mutableListOf()
        )
    }
}