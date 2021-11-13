package ru.sber.rdbms

fun main() {
    val tr = TransferConstraint()
    tr.transfer(1, 2, 500)

    val trOpt = TransferOptimisticLock()
    trOpt.transfer(1, 2, 500)

    val trPes = TransferPessimisticLock()
    trPes.transfer(1, 2, 500)
}
