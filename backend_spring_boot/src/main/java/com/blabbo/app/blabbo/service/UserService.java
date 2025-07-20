package com.blabbo.app.blabbo.service;

import com.blabbo.app.blabbo.exceptions.ResourceNotFound;
import com.blabbo.app.blabbo.exceptions.UserAlreadyExists;
import com.blabbo.app.blabbo.model.User;
import com.blabbo.app.blabbo.model.UserSummary;
import com.blabbo.app.blabbo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = Logger.getLogger(UserService.class.getName());


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerUser(String name, String email, String rawPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExists();
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(name, email, encodedPassword);
        userRepository.save(user);
    }


    public List<UserSummary> getAllUsers() {
        return userRepository.findAllProjectedBy();
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(ResourceNotFound::new);
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                ResourceNotFound::new);
    }


    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    public void updateUserName(String newUserName, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ResourceNotFound::new);
        user.setName(newUserName);
        userRepository.save(user);
    }


    public void updateEmail(String newEmail, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ResourceNotFound::new);
        user.setEmail(newEmail);
        userRepository.save(user);
    }
}
