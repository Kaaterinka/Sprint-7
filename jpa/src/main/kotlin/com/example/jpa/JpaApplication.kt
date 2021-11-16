package com.example.jpa

import com.example.jpa.enteties.Buyer
import com.example.jpa.enteties.Order
import com.example.jpa.enteties.Product
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.example.jpa.repository.OrderRepository
import org.hibernate.internal.CriteriaImpl

@SpringBootApplication
class JpaApplication(private val orderRepository: OrderRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {

        val order1 = Order(
            totalSum = 5000,
            buyer = Buyer(surname = "Ivanov", name = "Ivan", phone = "555")
        )
        order1.addProduct(Product(price = 100, number = 3))
        order1.addProduct(Product(price = 700, number = 2))

        val order2 = Order(
            totalSum = 8500,
            buyer = Buyer(surname = "Ivanov", name = "Ivan", phone = "777777"),
        )
        order2.addProduct(Product(price = 450, number = 8))

        orderRepository.saveAll(listOf(order1, order2))

        val orders = orderRepository.findAll()
        println("Все заказы + $orders")

        val sumOrders = orderRepository.findByTotalSum(5000)
        println("Заказы стоимостью 5000 + $sumOrders")

        val afterDeleteOrders = orderRepository.delete(order1)
        println("Оставшиеся заказы + $afterDeleteOrders")
    }
}

fun main(args: Array<String>) {
    runApplication<JpaApplication>(*args)
}
