package com.food2you.foodserver.restaurant

import com.food2you.foodserver.combo.Combo
import com.food2you.foodserver.costumer.Costumer
import com.food2you.foodserver.costumer.response.LoginResponse
import com.food2you.foodserver.menus.Menu
import com.food2you.foodserver.menus.MenuRepository
import com.food2you.foodserver.menus.requests.NewMenu
import com.food2you.foodserver.orders.Order
import com.food2you.foodserver.orders.OrderRepository
import com.food2you.foodserver.product.Product
import com.food2you.foodserver.product.ProductRepository
import com.food2you.foodserver.restaurant.requests.NewRestaurant
import com.food2you.foodserver.restaurant.requests.RestaurantLoginRequest
import com.food2you.foodserver.restaurant.response.RestaurantLoginResponse
import com.food2you.foodserver.security.JWT
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
data class RestaurantService (
    val restaurantRepository: RestaurantRepository,
    val orderRepository: OrderRepository,
    val menuRepository: MenuRepository,
    val productRepository: ProductRepository,
    val jwt : JWT
) {
    private val logger = LoggerFactory.getLogger(Costumer::class.java)

    fun createRestaurant(restaurant: NewRestaurant) {
        val restaurant = Restaurant(
            id = null,
            name = restaurant.name,
            email = restaurant.email,
            password = restaurant.password,
            status = "Open",
            orders = mutableListOf<Order>(),
            menus = mutableListOf<Menu>(),
            products = mutableListOf<Product>(),
            combos = mutableListOf<Combo>(),
            roles = mutableSetOf<String>()
        )

        restaurantRepository.save(restaurant)
    }

    fun restaurantLogin(credentials: RestaurantLoginRequest) : RestaurantLoginResponse? {
        val restaurant = restaurantRepository.findRestaurantByEmail(credentials.email) ?: return null
        if (restaurant.password != credentials.password) return null
        val loginResponse = RestaurantLoginResponse(
            token = jwt.createToken(restaurant),
            restaurant = restaurant
        )

        logger.info(loginResponse.toString())
        return loginResponse
    }


    fun findAllRestaurants() = restaurantRepository.findAll()

    fun getAllOrders(restaurantId: Long) : MutableList<Order> = orderRepository.findAllByRestaurant(restaurantId)

    fun createMenu(newMenu: NewMenu, restaurantId: Long) : Menu {
        val menu = Menu (
            id = null,
            name = newMenu.name,
            restaurant = restaurantId,
            products = mutableSetOf<Product>()
        )

        menu.restaurant = restaurantId
        return menuRepository.save(menu)
    }

    fun createMenu(menu: Menu, restaurantId: Long) : Menu {
        menu.restaurant = restaurantId
        return menuRepository.save(menu)
    }

    fun getAllMenus(restaurantId: Long) : MutableList<Menu> = menuRepository.findAllByRestaurant(restaurantId)

    fun addProductToMenu(productId : Long, menuId: Long ) {
        val menu = menuRepository.findByIdOrNull(menuId)
        val product = productRepository.findByIdOrNull(productId)
        menu?.products?.add(product!!)
        menuRepository.deleteById(menuId)
        createMenu(menu!!, menuId)
    }

    fun addProduct(product : Product, restaurantId: Long) : Product {
        product.restaurant = restaurantId
        return productRepository.save(product)
    }

    fun getAllProducts(restaurantId: Long) : MutableList<Product> = productRepository.findAllByRestaurant(restaurantId)

    fun getAllProductsFromOrder(orderId : Long) : MutableList<Product> = orderRepository.findById(orderId).get().products

    fun deleteProduct(productId: Long) = productRepository.deleteById(productId)

    fun getProductById(productId: Long) : Product? = productRepository.findByIdOrNull(productId)

    fun updateProduct(productId: Long, product: Product) {
        var deletedProduct = getProductById(productId)
        deleteProduct(productId)
        if (deletedProduct != null) {
            addProduct(product, deletedProduct.restaurant)
        } else {
            logger.info("PRODUCT NOT FOUND")
        }
    }
}
