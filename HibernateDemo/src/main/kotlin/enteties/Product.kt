package enteties

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Product(
    @Id
    @GeneratedValue
    var id: Long = 0,

    var price: Int,
    var number: Int
)