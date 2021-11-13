package ru.sber.rdbms

import java.sql.DriverManager
import java.sql.SQLException

class TransferPessimisticLock {
    private val connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres",
        "postgres",
        "123"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        connection.use { conn ->
            val autoCommit = conn.autoCommit
            try {
                conn.autoCommit = false
                val select1 = conn.prepareStatement("select * from account1 where id=$accountId1 for update")
                select1.use { statement ->
                    statement.executeQuery().use {
                        it.next()
                        if (it.getInt("amount") < amount)
                            throw NotEnoughMoney("Недостаточно средств!")
                    }
                }
                val send = conn.prepareStatement("UPDATE account1 SET amount=amount-$amount where id=$accountId1")
                send.use { statement ->
                    statement.executeUpdate()
                }
                val select2 = conn.prepareStatement("select * from account1 where id=$accountId2 for update")
                select2.use { statement ->
                    statement.executeQuery()
                }
                val get = conn.prepareStatement("UPDATE account1 SET amount=amount+$amount where id=$accountId2")
                get.use { statement ->
                    statement.executeUpdate()
                }
                conn.commit()
            } catch (exception: SQLException) {
                println(exception.message)
                conn.rollback()
            } finally {
                conn.autoCommit = autoCommit
            }
        }
    }
}
