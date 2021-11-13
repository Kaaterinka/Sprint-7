package ru.sber.rdbms

import java.sql.DriverManager

class TransferConstraint {
    private val connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres",
        "postgres",
        "123"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        connection.use { conn ->
            val sendMoney =
                conn.prepareStatement("UPDATE account1 SET amount=amount-$amount where id=$accountId1")
            sendMoney.use { sendStatement ->
                sendStatement.executeUpdate()
            }
            val getMoney = conn.prepareStatement("UPDATE account1 SET amount=amount+$amount where id=$accountId2")
            getMoney.use { getStatement ->
                getStatement.executeUpdate()
            }
        }
    }
}
