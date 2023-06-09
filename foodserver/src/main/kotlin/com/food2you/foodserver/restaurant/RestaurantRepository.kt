package com.food2you.foodserver.restaurant;

import com.food2you.foodserver.costumer.Costumer
import jakarta.validation.constraints.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RestaurantRepository : JpaRepository<Restaurant, Long> {

    @Query("select r from Restaurant r")
    override fun findAll() : MutableList<Restaurant>

    fun findRestaurantByEmail(email: String) : Restaurant?
}