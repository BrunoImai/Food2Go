package com.food2you.foodserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FoodserverApplication

fun main(args: Array<String>) {
	runApplication<FoodserverApplication>(*args)
}
