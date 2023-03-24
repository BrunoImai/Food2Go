package com.food2you.foodserver.costumer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CostumerRepository : JpaRepository<Costumer, Long> {
    override fun deleteById(id: Long) {}

    @Query("select c from Costumer c")
    override fun findAll() : MutableList<Costumer>

}