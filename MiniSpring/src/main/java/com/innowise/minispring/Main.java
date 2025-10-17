package com.innowise.minispring;

import com.innowise.minispring.application.OrderService;
import com.innowise.minispring.springframework.context.MiniApplicationContext;

public class Main {
    public static void main(String[] args) {
        MiniApplicationContext context = new MiniApplicationContext("com.innowise.minispring.application");

        OrderService orderService = context.getBean(OrderService.class);
        orderService.createOrder();

        context.close();
    }
}
