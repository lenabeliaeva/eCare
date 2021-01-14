package com.example.demo.services;

public interface SecurityService {
    String findLoggedInEmail();
    void autologin(String username, String password);
}
