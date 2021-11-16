package com.example.jpa.enteties

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Product(
    @Id
    @GeneratedValue
    var product_id: Long = 0,

    var price: Int,
    var number: Int
)