package com.unifood.unifoodserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UnifoodServerApplication

fun main(args: Array<String>) {
	runApplication<UnifoodServerApplication>(*args)
}
