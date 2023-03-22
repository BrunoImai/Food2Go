/*
 * Copyright Epic Games, Inc. All Rights Reserved.
 */
package com.food2you.foodserver.costumer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CostumerRepository : JpaRepository<Costumer, Long>