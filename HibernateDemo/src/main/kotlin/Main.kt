import enteties.Product
import enteties.Buyer
import enteties.Order
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

fun main() {
    val sessionFactory = Configuration().configure()
        .addAnnotatedClass(Order::class.java)
        .addAnnotatedClass(Buyer::class.java)
        .addAnnotatedClass(Product::class.java)
        .buildSessionFactory()

    sessionFactory.use { sessionFactory ->
        val dao = OrderDAO(sessionFactory)

        val order1 = Order(
            totalSum = 1000,
            buyer = Buyer(surname = "Testov", name = "Test", phone = "555555"),
        )
        order1.addProduct(Product(price = 100, number = 3))
        order1.addProduct(Product(price = 700, number = 2))

        val order2 = Order(
            totalSum = 8500,
            buyer = Buyer(surname = "Ivanov", name = "Ivan", phone = "777777"),
        )
        order2.addProduct(Product(price = 450, number = 8))

        dao.save(order1)
        dao.save(order2)

        val found = dao.find(order1.id)
        println("Найден заказ: $found \n")

        val allOrders = dao.findAll()
        println("Все заказы: $allOrders")

    }
}