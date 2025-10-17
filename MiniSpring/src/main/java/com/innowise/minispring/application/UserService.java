package com.innowise.minispring.application;

import com.innowise.minispring.springframework.annotation.Component;

@Component
public class UserService {
    public void printUser() {
        System.out.println("Пользователь найден");
    }
}
