package com.blabbo.app.blabbo.controller;

import com.blabbo.app.blabbo.DTO.UserRegisterDTO;
import com.blabbo.app.blabbo.model.User;
import com.blabbo.app.blabbo.model.UserSummary;
import com.blabbo.app.blabbo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserSummary>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User retrievedUser = userService.getUserById(id);
        return ResponseEntity.ok().body(retrievedUser);
    }


    @PutMapping("/update/username/{id}")
    public ResponseEntity<Void> updateUsername(@PathVariable Long id,
                                               @RequestBody
                                               String newUserName) {
        userService.updateUserName(newUserName, id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/users/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }


    @PutMapping("/update/email/{id}")
    public ResponseEntity<String> updateUserEmail(@PathVariable Long id,
                                                  @RequestBody
                                                  String newEmail) {
        User user = userService.getUserById(id);
        user.setEmail(newEmail);
        return ResponseEntity.ok().body("Email updated successfully");
    }


    @PostMapping("/create")
    public ResponseEntity<String> createUser(
            @Valid @RequestBody UserRegisterDTO userDTO) {
        userService.registerUser(userDTO.getName(), userDTO.getEmail(),
                                 userDTO.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body("User registered " + "successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(204).build();
    }
}
