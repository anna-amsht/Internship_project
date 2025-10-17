package com.innowise.salesanalysis;

import com.innowise.salesanalysis.data.Customer;
import com.innowise.salesanalysis.data.Order;
import com.innowise.salesanalysis.data.OrderItem;
import com.innowise.salesanalysis.data.OrderStatus;

import java.util.*;
import java.util.stream.Collectors;

public class MetricsGen {

    public List<String> listOfUniqueCities(List<Order> orders) {
        return orders.stream()
                .map(order -> order.getCustomer().getCity())
                .distinct()
                .collect(Collectors.toList());
    }

    public double totalPrice(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.DELIVERED))
                .flatMap(order -> order.getItems().stream())
                .map(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                .reduce(0.0, Double::sum);
    }

    public String mostPopularProduct(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.DELIVERED))
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getProductName,
                        Collectors.summingInt(OrderItem::getQuantity)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No product found");
    }

    public double averageCheck(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.DELIVERED))
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                        .sum())
                .average()
                .orElse(0.0);
    }
    public List<Customer> customerWithMoreThenFiveOrders(List<Order> orders) {
        Map<Customer, Long> ordersCount = orders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer(), Collectors.counting()));

        return ordersCount.entrySet().stream()
                .filter(count -> count.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

}

