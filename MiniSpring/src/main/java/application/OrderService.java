package application;

import springframework.annotation.Autowired;
import springframework.annotation.Component;
import springframework.beanFactory.DisposableBean;
import springframework.beanFactory.InitializingBean;

@Component
public class OrderService implements InitializingBean{
    @Autowired
    private UserService userService;

    @Override
    public void afterPropertiesSet() {
        System.out.println("OrderService инициализирован");
    }

    public void createOrder() {
        userService.printUser();
        System.out.println("Заказ создан");
    }
}
