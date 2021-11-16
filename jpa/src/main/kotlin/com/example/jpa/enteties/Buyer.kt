package com.example.jpa.enteties

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Buyer(
    @Id
    @GeneratedValue
    var buyer_id: Long = 0,

    var surname: String,
    var name: String,
    var phone: String
)
