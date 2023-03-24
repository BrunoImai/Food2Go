/*
 * Copyright Epic Games, Inc. All Rights Reserved.
 */
package com.food2you.foodserver.costumer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.food2you.foodserver.costumer.requests.CostumerRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CostumerService(
    val repository: CostumerRepository
) {
    private val logger = LoggerFactory.getLogger(Costumer::class.java)
    fun deleteCostumerById(id : Long) = logger.info("Deleted Costumer:" + repository.deleteById(id))

    fun createUser(costumer: CostumerRequest): Costumer {
        val costumer = Costumer(
            id = null,
            name = costumer.name,
            mobilePhone = costumer.mobilePhone ?: "",
            email = costumer.email,
            password = costumer.password
        )

        val saved = repository.save(costumer)
        logger.info("Saved costumer: $costumer")
        return saved
    }

    fun createUserWithId(costumer: Costumer, id: Long): Costumer {
        val costumer = Costumer(
            id = id,
            name = costumer.name,
            mobilePhone = costumer.mobilePhone ?: "",
            email = costumer.email,
            password = costumer.password
        )

        val saved = repository.save(costumer)
        logger.info("Saved costumer: $costumer")
        return saved
    }

   fun findAllCostumers() : MutableList<Costumer> {
       val costumerList = repository.findAll()
       logger.info("Costumer List:")
       logger.info(costumerList.toString())
       return costumerList
   }


    fun getCustomerById(id : Long) : Optional<Costumer> {
        val costumer = repository.findById(id)
        logger.info("Costumer with ID: $id")
        logger.info(costumer.toString())
        return costumer
    }
}