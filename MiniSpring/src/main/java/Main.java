import application.OrderService;
import springframework.context.MiniApplicationContext;

public class Main {
    public static void main(String[] args) {
        MiniApplicationContext context = new MiniApplicationContext("application");

        OrderService orderService = context.getBean(OrderService.class);
        orderService.createOrder();

        context.close();
    }
}
