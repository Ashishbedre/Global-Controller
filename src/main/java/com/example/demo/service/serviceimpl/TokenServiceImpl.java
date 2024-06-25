package com.example.demo.service.serviceimpl;

import com.example.demo.config.AppConfig;
import com.example.demo.dto.TokenResponse;
import com.example.demo.service.TokenService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TokenServiceImpl implements TokenService {

    private final AppConfig appConfig;
    private String accessToken;
    private String refreshToken;

    @Autowired
    public TokenServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @PostConstruct
    public void init() {
        retrieveInitialTokens();
    }

    public void retrieveInitialTokens() {
        WebClient webClient = WebClient.create();

        Mono<TokenResponse> response = webClient.post()
                .uri(appConfig.getInitialTokenUrl())
                .body(BodyInserters.fromFormData("username", appConfig.getUsername())
                        .with("password", appConfig.getPassword()))
                .retrieve()
                .bodyToMono(TokenResponse.class);

        response.subscribe(this::handleTokenResponse);
    }

    @Scheduled(fixedRate = 300000)  // 5 minutes
    public void refreshAccessToken() {
        WebClient webClient = WebClient.create();

        Mono<TokenResponse> response = webClient.post()
                .uri(appConfig.getRefreshTokenUrl())
                .body(BodyInserters.fromFormData("refreshToken", refreshToken))
                .retrieve()
                .bodyToMono(TokenResponse.class);

        response.subscribe(this::handleTokenResponse);
    }

    public void handleTokenResponse(TokenResponse tokenResponse) {
        this.accessToken = tokenResponse.getAccessToken();
        this.refreshToken = tokenResponse.getRefreshToken();
        // Log or store the tokens as needed
        System.out.println("Access Token: " + accessToken);
        System.out.println("Refresh Token: " + refreshToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
