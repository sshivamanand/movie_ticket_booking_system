package com.cinema.pattern.factory;

import com.cinema.model.User;
import org.springframework.stereotype.Component;

/**
 * Factory Pattern (Creational): Creates User instances with proper initialization.
 * Design Principle: Single Responsibility - encapsulates user creation logic.
 */
@Component
public class UserFactory {
    public User createUser(String name, String email, String phone, String password) {
        User user = new User(name, email, phone, password);
        user.setRole("USER");
        return user;
    }

    public User createAdmin(String name, String email, String phone, String password) {
        User user = new User(name, email, phone, password);
        user.setRole("ADMIN");
        return user;
    }
}
