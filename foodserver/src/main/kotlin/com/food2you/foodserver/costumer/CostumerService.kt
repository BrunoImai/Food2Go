/*
 * Copyright Epic Games, Inc. All Rights Reserved.
 */
package com.food2you.foodserver.costumer

import com.food2you.foodserver.costumer.requests.CostumerRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CostumerService(
    val repository: CostumerRepository
) {
    private val logger = LoggerFactory.getLogger(Costumer::class.java)
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
}