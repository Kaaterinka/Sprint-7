package com.example.jpa.repository

import com.example.jpa.enteties.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CrudRepository<Order, Long> {
    fun findByTotalSum(totalSum: Int): List<Order>
}