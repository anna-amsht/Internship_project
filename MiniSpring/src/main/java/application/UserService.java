package application;

import springframework.annotation.Component;

@Component
public class UserService {
    public void printUser() {
        System.out.println("Пользователь найден");
    }
}
