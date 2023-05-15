package com.food2you.foodserver.costumer

import com.food2you.foodserver.costumer.requests.LoginRequest
import com.food2you.foodserver.costumer.response.LoginResponse
import com.food2you.foodserver.orders.Order
import com.food2you.foodserver.orders.OrderRepository
import com.food2you.foodserver.restaurant.RestaurantRepository
import com.food2you.foodserver.security.JWT
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CostumerService(
    val jwt: JWT,
    val costumerRepository: CostumerRepository,
    val orderRepository: OrderRepository, private val restaurantRepository: RestaurantRepository
) {
    private val logger = LoggerFactory.getLogger(Costumer::class.java)

    fun login(credentials: LoginRequest) : LoginResponse? {
        val costumer = costumerRepository.findByEmail(credentials.email)
        if (costumer.password == credentials.senha) return null

        val token = jwt.createToken(costumer)
        return LoginResponse(token, costumer)
    }
    fun deleteCostumerById(id : Long) = logger.info("Deleted Costumer:" + costumerRepository.deleteById(id))

    fun createUser(costumer: Costumer): Costumer {
        val costumer = Costumer(
            id = null,
            name = costumer.name,
            mobilePhone = costumer.mobilePhone ?: "",
            email = costumer.email,
            password = costumer.password,
            orders = mutableListOf<Order>(),
            roles = mutableSetOf<String>()
        )

        return costumerRepository.save(costumer)
    }

    fun createUserWithId(costumer: Costumer, id: Long): Costumer {
        val costumer = Costumer(
            id = id,
            name = costumer.name,
            mobilePhone = costumer.mobilePhone ?: "",
            email = costumer.email,
            password = costumer.password,
            orders = mutableListOf(),
            roles = mutableSetOf<String>()
        )

        val saved = costumerRepository.save(costumer)
        logger.info("Saved costumer: $costumer")
        return saved
    }

   fun findAllCostumers() : MutableList<Costumer> {
       val costumerList = costumerRepository.findAll()
       logger.info("Costumer List:")
       logger.info(costumerList.toString())
       return costumerList
   }


    fun getCustomerById(id : Long) : Optional<Costumer> {
        val costumer = costumerRepository.findById(id)
        logger.info("Costumer with ID: $id")
        logger.info(costumer.toString())
        return costumer
    }

    fun createOrder(customerId: Long, order: Order, restaurantId : Long): Order {

        order.costumer = customerId
        order.restaurant = restaurantId
        return orderRepository.save(order)
    }

    fun findAllOrders(costumerId: Long) : MutableList<Order> = orderRepository.findAllByCostumer(costumerId)

}