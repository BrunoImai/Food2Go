package com.food2you.foodserver.costumer

import com.food2you.foodserver.costumer.requests.CostumerRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*
import kotlin.math.cos

@RestController
@RequestMapping("/costumers")
class CostumerResource(
    private val service: CostumerService
) {
    @PostMapping
    fun create(@RequestBody @Valid costumer: CostumerRequest) =
        status(HttpStatus.CREATED).body(service.createUser(costumer))

    @DeleteMapping("/{costumerId}")
    fun delete(@Valid @PathVariable costumerId: Long) = status(HttpStatus.OK).body( service.deleteCostumerById(costumerId))

    @GetMapping
    fun getAllCostumers() = status(HttpStatus.OK).body( service.findAllCostumers())

    @GetMapping("/{costumerId}")
    fun getCostumerById(@Valid @PathVariable costumerId: Long) = status(HttpStatus.OK).body( service.getCustomerById(costumerId))

    @PutMapping("/{costumerId}")
    fun updateCostumer(@Valid @PathVariable costumerId: Long, @RequestBody @Valid costumer: Costumer) {
        service.deleteCostumerById(costumerId)
        status(HttpStatus.CREATED).body(service.createUserWithId(costumer, costumerId))
    }

}