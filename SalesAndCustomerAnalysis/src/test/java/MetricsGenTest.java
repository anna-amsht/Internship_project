import com.innowise.salesanalysis.*;
import com.innowise.salesanalysis.data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetricsGenTest {
    private MetricsGen metricsGenerator;
    private static List<Order> orders;
    Customer customer1, customer2, customer3, customer4;

    @BeforeEach
    void setUp() {
        metricsGenerator = new MetricsGen();
        customer1 = new Customer("cus1","Алексей Колосов", "dinamo@mail.com",23, "Молодечно", "2022-09-11");
        customer2 = new Customer("cus2","Вадим Мороз", "dinamo@mail.com",21, "Минск", "2024-08-21");
        customer3 = new Customer("cus3","Сэм Энас", "dinamo@mail.com",32, "Минск", "2025-01-05");
        customer4 = new Customer("cus4","Андрей Стась", "dinamo@mail.com",36, "Гродно", "2025-02-17");

        orders = List.of(
                new Order(  customer1, "ord1",
                        List.of(new OrderItem("Laptop", 1, 1000, Category.ELECTRONICS),
                                new OrderItem("Mouse", 2, 25, Category.ELECTRONICS)),
                        OrderStatus.DELIVERED, "2022-10-12"
                ),

                new Order(  customer1, "ord2",
                        List.of(new OrderItem("Book", 1, 15, Category.BOOKS)),
                        OrderStatus.DELIVERED, "2022-11-24"
                ),

                new Order(  customer1, "ord3",
                        List.of(new OrderItem("Book", 1, 15, Category.BOOKS)),
                        OrderStatus.DELIVERED, "2023-02-20"
                ),

                new Order(customer3, "ord4",
                        List.of(new OrderItem("Book", 3, 25, Category.BOOKS)),
                        OrderStatus.CANCELLED, "2025-04-01")

        );

    }

    @Test
    void testListOfUniqueCities() {
        List<String> cities = metricsGenerator.listOfUniqueCities(orders);
        assertEquals(List.of("Молодечно", "Минск"), cities);
    }

    @Test
    void testTotalPrice() {
        double totalPrice = metricsGenerator.totalPrice(orders);
        double expectedValue = 1000*1 + 25*2 + 15 + 15 + 0*25; // the last one is cancelled
        assertEquals(expectedValue, totalPrice);
    }

    @Test
    void testMostPopularProduct() {
        assertEquals("Mouse", metricsGenerator.mostPopularProduct(orders));
    }

    @Test
    void testAverageCheck() {
        double expectedAverage = ((1000 + 2*25) + 15 + 15) / 3;
        assertEquals(expectedAverage, metricsGenerator.averageCheck(orders), 0.001);
    }

    @Test
    void testCustomerWithMoreThenFiveOrders() {
        List<Order> testOrders = List.of(
                new Order(  customer1, "ord1", List.of(), OrderStatus.DELIVERED, "2022-10-12"),
                new Order(  customer1, "ord2", List.of(), OrderStatus.DELIVERED, "2022-10-16"),
                new Order(  customer1, "ord3", List.of(), OrderStatus.DELIVERED, "2022-10-20"),
                new Order(  customer1, "ord4", List.of(), OrderStatus.DELIVERED, "2022-11-24"),
                new Order(  customer1, "ord5", List.of(), OrderStatus.DELIVERED, "2022-11-28"),
                new Order(  customer1, "ord6", List.of(), OrderStatus.DELIVERED, "2022-12-04")
        );

        List<Customer> resultList = metricsGenerator.customerWithMoreThenFiveOrders(testOrders);
        assertEquals(1, resultList.size());
        assertEquals(customer1, resultList.get(0));

    }
}