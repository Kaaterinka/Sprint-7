package ru.sber.rdbms

import java.sql.DriverManager
import java.sql.SQLException

class NotEnoughMoney(message: String) : SQLException(message)

class TransferOptimisticLock {
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
                val select1 = conn.prepareStatement("select * from account1 where id=$accountId1")
                var version = 0
                select1.use { statement ->
                    statement.executeQuery().use {
                        it.next()
                        if (it.getInt("amount") < amount)
                            throw NotEnoughMoney("Недостаточно средств!")
                        version = it.getInt("version")
                    }
                }
                val send =
                    conn.prepareStatement("UPDATE account1 SET amount=amount-$amount, version=version+1 where id=$accountId1 and version=?")
                send.use { statement ->
                    statement.setInt(1, version)
                    val updatedRows = statement.executeUpdate()
                    if (updatedRows == 0)
                        throw SQLException("Concurrent update")
                }
                val select2 = conn.prepareStatement("select * from account1 where id=$accountId2")
                select2.use { st ->
                    st.executeQuery().use {
                        it.next()
                        version = it.getInt("version")
                    }
                }
                val get =
                    conn.prepareStatement("UPDATE account1 SET amount=amount+$amount, version=version+1 where id=$accountId2 and version=?")
                get.use { statement ->
                    statement.setInt(1, version)
                    val updatedRows = statement.executeUpdate()
                    if (updatedRows == 0)
                        throw SQLException("Concurrent update")
                }
                conn.commit()
            } catch (exception: SQLException) {
                println(exception.message)
                exception.printStackTrace()
                conn.rollback()
            } finally {
                conn.autoCommit = autoCommit
            }
        }
    }
}
