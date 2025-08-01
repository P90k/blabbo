package com.blabbo.app.blabbo.controller;

import com.blabbo.app.blabbo.service.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }




    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("Authorization") String authHeader) {
        String message = authService.logout(authHeader);

        return ResponseEntity.ok(message);
    }
}
