package com.food2you.foodserver.product;

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {

    fun findAllByRestaurant(restaurant: Long) : MutableList<Product>
}