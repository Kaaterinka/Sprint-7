import enteties.Order
import org.hibernate.SessionFactory

class OrderDAO(
    private val sessionFactory: SessionFactory
) {
    fun save(order: Order) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(order)
            session.transaction.commit()
        }
    }

    fun find(id: Long): Order? {
        val result: Order?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(Order::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<Order> {
        val result: List<Order>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from Order").list() as List<Order>
            session.transaction.commit()
        }
        return result
    }

    fun delete(id: Long) {
        val order: Order
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            order = session.get(Order::class.java, id)
            session.delete(order)
            session.transaction.commit()
        }
    }
}