package com.food2you.foodserver.restaurant

import com.food2you.foodserver.menus.Menu
import com.food2you.foodserver.menus.MenuRepository
import com.food2you.foodserver.menus.requests.NewMenu
import com.food2you.foodserver.orders.Order
import com.food2you.foodserver.orders.OrderRepository
import com.food2you.foodserver.product.Product
import com.food2you.foodserver.product.ProductRepository
import com.food2you.foodserver.restaurant.Stubs.costumerStubs
import com.food2you.foodserver.restaurant.Stubs.restaurantLoginRequestStubs
import com.food2you.foodserver.restaurant.Stubs.menuStubs
import com.food2you.foodserver.restaurant.Stubs.orderStubs
import com.food2you.foodserver.restaurant.Stubs.productStubs
import com.food2you.foodserver.restaurant.Stubs.restaurantStubs
import com.food2you.foodserver.security.JWT
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.webjars.NotFoundException

internal class RestaurantServiceTest {
    private val restaurantRepositoryMock = mockk<RestaurantRepository>()
    private val orderRepositoryMock = mockk<OrderRepository>()
    private val menuRepositoryMock = mockk<MenuRepository>()
    private val productRepositoryMock = mockk<ProductRepository>()
    private val jwt = mockk<JWT>()

    private val service =
        RestaurantService(restaurantRepositoryMock, orderRepositoryMock, menuRepositoryMock, productRepositoryMock, jwt)

    @Test
    fun `Restaurant login - Success`() {
        val credentials = restaurantLoginRequestStubs()
        val restaurant = restaurantStubs()

        every { restaurantRepositoryMock.findRestaurantByEmail(credentials.email) } returns restaurant
        every { jwt.createToken(restaurant) } returns "token"

        val loginResponse = service.restaurantLogin(credentials)

        loginResponse shouldNotBe null
        loginResponse?.token shouldBe "token"
        loginResponse?.restaurant shouldBe restaurant
    }

    @Test
    fun `Restaurant login - Restaurant not found`() {
        val credentials = restaurantLoginRequestStubs()

        every { restaurantRepositoryMock.findRestaurantByEmail(credentials.email) } returns null

        val exception = org.junit.jupiter.api.assertThrows<ResponseStatusException> {
            service.restaurantLogin(credentials)
        }
        exception.statusCode shouldBe HttpStatus.NOT_FOUND
        exception.message shouldBe "404 NOT_FOUND \"Restaurant not found with email: ${credentials.email}\""
        verify { restaurantRepositoryMock.findRestaurantByEmail(credentials.email) }
    }


    @Test
    fun `Restaurant login with invalid credentials should throw exception`() {
        val credentials = restaurantLoginRequestStubs()

        val restaurant = restaurantStubs()
        every { restaurantRepositoryMock.findRestaurantByEmail(credentials.email) } returns restaurant

        shouldThrow<IllegalArgumentException> {
            service.restaurantLogin(credentials)
        }
    }


    @Test
    fun `Get all restaurants - Success`() {
        val restaurants = listOf(
            restaurantStubs(),
            restaurantStubs()
        )
        every { restaurantRepositoryMock.findAll() } returns restaurants.toMutableList()

        val result = service.findAllRestaurants()

        result shouldBe restaurants
    }

    @Test
    fun `Get all orders for a restaurant - Success`() {

        val order1 = orderStubs(restaurantId = 1)
        val order2 = orderStubs(restaurantId = 1)
        val orders = mutableListOf(order1, order2)
        every { orderRepositoryMock.findAllByRestaurant(1) } returns orders

        val result = service.getAllOrders(1)

        result shouldContainExactly orders
        verify(exactly = 1) { orderRepositoryMock.findAllByRestaurant(1) }
    }


    @Test
    fun `Create a new menu - Success`() {
        val newMenu = NewMenu(name = "Test Menu")
        val restaurantId = 1L
        val menu = Menu(
            id = 1,
            name = newMenu.name,
            restaurant = restaurantId,
            products = mutableSetOf<Product>()
        )
        every { menuRepositoryMock.save(any()) } returns menu

        val result = service.createMenu(newMenu, restaurantId)

        result shouldBe menu
    }


    @Test
    fun `Get all products from restaurant - Success`() {

        val product1 = productStubs(restaurantId = 1)
        val product2 = productStubs(restaurantId = 1)
        val products = mutableListOf(product1, product2)
        every { productRepositoryMock.findAllByRestaurant(1) } returns products

        val result = service.getAllProducts(1)

        result shouldContainExactly products
    }

    @Test
    fun `Get all products from order - Success`() {

        val product1 = productStubs()
        val product2 = productStubs()
        val products = mutableListOf(product1, product2)
        every { orderRepositoryMock.findById(1).get().products } returns products

        val result = service.getAllProductsFromOrder(1)

        result shouldContainExactly products
    }


    @Test
    fun `delete Product by id - Success`() {
        val product = productStubs()
        every { productRepositoryMock.findByIdOrNull(1) } returns product
        justRun { productRepositoryMock.deleteById(1)}
        service.deleteProduct(1) shouldBe product
    }

    @Test
    fun `delete Product by id - Product Not Found`() {
        every { productRepositoryMock.findByIdOrNull(any()) } returns null

        shouldThrow<NotFoundException> {
            service.deleteProduct(1)
        } shouldHaveMessage "Product with id: 1 Not Found"
    }

    @Test
    fun `Get products by Id - Success`() {
        val product = productStubs()
        every { productRepositoryMock.findByIdOrNull(1) } returns product

        val result = service.getProductById(1)

        result shouldBe product
    }

    @Test
    fun `Get products by Id - Product not exists`() {
        every { productRepositoryMock.findByIdOrNull(1) } returns null

        shouldThrow<NotFoundException> {
            service.deleteProduct(1)
        } shouldHaveMessage "Product with id: 1 Not Found"
    }

    @Test
    fun `Update products by Id - Success`() {
        val product = productStubs()
        every { productRepositoryMock.findByIdOrNull(1)} returns product
        every { restaurantRepositoryMock.findByIdOrNull(product.restaurant)} returns restaurantStubs()
        every { productRepositoryMock.save(product)} returns product
        justRun { service.deleteProduct(1)}
        justRun { service.addProduct(product, product.restaurant)}

        service.updateProduct(1, product) shouldBe product
    }

    @Test
    fun `Update products by Id - Product Not Found`() {
        val product = productStubs()
        every { service.getProductById(1)} returns null

        shouldThrow<NotFoundException> {
            service.deleteProduct(1)
        } shouldHaveMessage "Product with id: 1 Not Found"

    }
}