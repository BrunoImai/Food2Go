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
import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.webjars.NotFoundException


@Service
data class RestaurantService (
    val restaurantRepository: RestaurantRepository,
    val orderRepository: OrderRepository,
    val menuRepository: MenuRepository,
    val productRepository: ProductRepository,
    val jwt : JWT
) {
    private val logger = LoggerFactory.getLogger(Costumer::class.java)

    fun createRestaurant(newRestaurant: NewRestaurant): Restaurant {
        val restaurant = Restaurant(
            id = null,
            name = newRestaurant.name,
            email = newRestaurant.email,
            password = newRestaurant.password,
            status = "Open",
            orders = mutableListOf<Order>(),
            menus = mutableListOf<Menu>(),
            products = mutableListOf<Product>(),
            combos = mutableListOf<Combo>(),
            roles = mutableSetOf<String>()
        )


        return restaurantRepository.save(restaurant)
    }

    fun restaurantLogin(credentials: RestaurantLoginRequest) : RestaurantLoginResponse? {
        val restaurant = restaurantRepository.findRestaurantByEmail(credentials.email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found with email: ${credentials.email}")
        if (restaurant.password != credentials.password) throw IllegalArgumentException("Invalid password")
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
        restaurantRepository.findByIdOrNull(restaurantId) ?: throw NotFoundException("Restaurant with id: $restaurantId Not Found")
        menu.restaurant = restaurantId
        return menuRepository.save(menu)
    }

    fun getAllMenus(restaurantId: Long) : MutableList<Menu> = menuRepository.findAllByRestaurant(restaurantId)

    fun addProductToMenu(productId : Long, menuId: Long ) {
        val menu = menuRepository.findByIdOrNull(menuId)
        val product = productRepository.findByIdOrNull(productId)

        menu ?: throw NotFoundException("Menu with id: $menuId Not Found")
        product ?: throw NotFoundException("Product with id: $productId Not Found")

        menu.products?.add(product)
        menuRepository.deleteById(menuId)
        createMenu(menu, menuId)
    }

    fun addProduct(product : Product, restaurantId: Long) : Product {
        val restaurant = restaurantRepository.findByIdOrNull(restaurantId)
        restaurant ?: throw NotFoundException("Restaurant with id: $restaurantId Not Found")
        product.restaurant = restaurantId
        return productRepository.save(product)
    }

    fun getAllProducts(restaurantId: Long) : MutableList<Product> = productRepository.findAllByRestaurant(restaurantId)

    fun getAllProductsFromOrder(orderId : Long) : MutableList<Product> = orderRepository.findById(orderId).get().products

    fun deleteProduct(productId: Long) : Product {
        val deletedProduct = productRepository.findByIdOrNull(productId)
            ?: throw NotFoundException("Product with id: $productId Not Found")
        productRepository.deleteById(productId)
        return deletedProduct
    }

    fun getProductById(productId: Long) : Product? {
        val product = productRepository.findByIdOrNull(productId)
        return product ?: throw NotFoundException("Product with id: $productId Not Found")

    }

    fun updateProduct(productId: Long, product: Product) : Product{
        val deletedProduct = getProductById(productId) ?: throw NotFoundException("Product with id: $productId Not Found")
        addProduct(product, deletedProduct.restaurant)
        return deletedProduct
    }
}
