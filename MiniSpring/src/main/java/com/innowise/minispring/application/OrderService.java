package com.innowise.minispring.application;

import com.innowise.minispring.springframework.annotation.Autowired;
import com.innowise.minispring.springframework.annotation.Component;
import com.innowise.minispring.springframework.beanFactory.InitializingBean;

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
