package com.cmpe295.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
