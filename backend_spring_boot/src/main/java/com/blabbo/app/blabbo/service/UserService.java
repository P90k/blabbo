package com.blabbo.app.blabbo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blabbo.app.blabbo.model.User;
import com.blabbo.app.blabbo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String name, String email, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(name, email, encodedPassword);
        return userRepository.save(user);
    }

}
