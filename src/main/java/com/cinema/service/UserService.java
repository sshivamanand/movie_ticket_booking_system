package com.cinema.service;

import com.cinema.model.User;
import com.cinema.pattern.factory.UserFactory;
import com.cinema.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;

    public UserService(UserRepository userRepository, UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    public User register(String name, String email, String phone, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = userFactory.createUser(name, email, phone, password);
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
            .filter(u -> password.equals(u.getPassword()));
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
