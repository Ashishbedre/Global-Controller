package com.example.demo.service;


public interface TokenService {
    void retrieveInitialTokens();
    void refreshAccessToken();
    String getAccessToken();
    String getRefreshToken();
}