package com.blabbo.app.blabbo.service.interfaces;

public interface AuthService {

    String token();


    String logout(String authHeader);
}
