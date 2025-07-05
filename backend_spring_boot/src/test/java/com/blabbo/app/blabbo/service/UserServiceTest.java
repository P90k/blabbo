package com.blabbo.app.blabbo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.blabbo.app.blabbo.model.User;
import com.blabbo.app.blabbo.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void testRegisterUser_encodesPasswordAndSavesUser() {
        String rawPassword = "plainPassword";
        String encodedPassword = "encodedPassword123";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String name = "Test User";
        String email = "test@example.com";

        User registeredUser = userService.registerUser(name, email, rawPassword);

        verify(passwordEncoder).encode(rawPassword);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals(name, savedUser.getName());
        assertEquals(email, savedUser.getEmail());
        assertEquals(encodedPassword, savedUser.getPassword());

        assertEquals(savedUser, registeredUser);
    }
}
