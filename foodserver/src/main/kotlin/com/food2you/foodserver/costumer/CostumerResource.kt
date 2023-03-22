package com.food2you.foodserver.costumer

import com.food2you.foodserver.costumer.requests.CostumerRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/costumers")
class CostumerResource(
    private val service: CostumerService
) {
    @PostMapping
    fun create(@RequestBody @Valid costumer: CostumerRequest) =
        status(HttpStatus.CREATED).body(service.createUser(costumer))

}