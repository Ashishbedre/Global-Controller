package com.example.demo.service.serviceimpl;

import ch.qos.logback.core.util.FixedDelay;
import com.example.demo.service.PlaceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
public class PlaceServiceImp implements PlaceService {
    private final WebClient webClient;
    private String accessToken;

    public PlaceServiceImp() {
        this.webClient = WebClient.create("https://www.universal-tutorial.com/api");
    }

    public Mono<String> getAccessToken() {
        return webClient.get()
                .uri("/getaccesstoken")
                .header("Accept", "application/json")
                .header("api-token", "KnJxKBjgnq30xh2vPnR3XeBqoXfxuteD8kMTzsuM07mvYtetaZUdoQcw9onjekCeFz8")
                .header("user-email", "anurag@niralnetworks.com")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    int startIndex = response.indexOf(':') + 2;
                    int endIndex = response.lastIndexOf('"');
                    return response.substring(startIndex, endIndex);
                });
    }
//    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    @Scheduled(fixedRate = 300000)
    public void  generateToken(){
        accessToken = getAccessToken().block();
        System.out.println(accessToken);
    }


    public Object[] getCountries() {
        return webClient.get()
                .uri("https://www.universal-tutorial.com/api/countries")
                .header("Authorization", "Bearer "+accessToken)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(Object[].class)
                .block();
    }

    public Object[] getStates(String states) {
        return webClient.get()
                .uri("https://www.universal-tutorial.com/api/states/"+states)
                .header("Authorization", "Bearer "+accessToken)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(Object[].class)
                .block();
    }

    public Object[] getCities(String cities) {
        return webClient.get()
                .uri("https://www.universal-tutorial.com/api/cities/"+cities)
                .header("Authorization", "Bearer "+accessToken)
                .header("Accept", "application/json")
                .retrieve()
                .bodyToMono(Object[].class)
                .block();
    }


}
