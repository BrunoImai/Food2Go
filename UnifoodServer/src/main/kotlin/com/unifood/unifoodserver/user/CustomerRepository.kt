package com.unifood.unifoodserver.user
import org.springframework.data.jpa.repository.JpaRepository



interface CustomerRepository  : JpaRepository<Customer, Int> {

}