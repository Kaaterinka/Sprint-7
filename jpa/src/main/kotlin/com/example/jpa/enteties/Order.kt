package com.example.jpa.enteties

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Order(
    @Id
    @GeneratedValue
    var order_id: Long = 0,

    var totalSum: Int,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id")
    var product: MutableList<Product> = mutableListOf(),

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "buyer_id")
    var buyer: Buyer,

    @CreationTimestamp
    var createdTime: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedTime: LocalDateTime? = null
) {
    override fun toString(): String {
        return "Order(id=$order_id, totalSum='$totalSum')"
    }

    fun addProduct(newProduct: Product) {
        product.add(newProduct)
    }
}