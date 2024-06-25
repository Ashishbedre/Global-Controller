package com.example.demo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.username}")
    private String username;

    @Value("${app.password}")
    private String password;

    @Value("${app.initial-token-url}")
    private String initialTokenUrl;

    @Value("${app.refresh-token-url}")
    private String refreshTokenUrl;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getInitialTokenUrl() {
        return initialTokenUrl;
    }

    public String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }
}