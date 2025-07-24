package com.blabbo.app.blabbo.service;

public interface AuthService {

    String token();


    String logout(String authHeader);
}
