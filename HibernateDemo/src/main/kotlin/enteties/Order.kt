package enteties

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Order(
    @Id
    @GeneratedValue
    var id: Long = 0,

    var totalSum: Int,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn
    var product: MutableList<Product> = mutableListOf(),

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn
    var buyer: Buyer,

    @CreationTimestamp
    var createdTime: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedTime: LocalDateTime? = null
) {
    override fun toString(): String {
        return "Order(id=$id, totalSum='$totalSum')"
    }

    fun addProduct(newProduct: Product) {
        product.add(newProduct)
    }
}