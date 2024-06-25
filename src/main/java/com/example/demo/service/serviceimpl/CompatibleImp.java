package com.example.demo.service.serviceimpl;

import com.example.demo.dto.BackendPackage.ProductListResponcedto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ProvisionSiteSSDto;
import com.example.demo.dto.VersionSetProductDto;
import com.example.demo.service.Compatible;
import com.example.demo.service.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompatibleImp implements Compatible {

//    private final TokenService tokenService;
//    public String accessToken;
        @Autowired
        TokenService tokenService;
//    public CompatibleImp(TokenService tokenService) {
//        this.tokenService = tokenService;
//        this.accessToken=tokenService.getAccessToken();
//    }

    public JsonNode checkCompatible(ProvisionSiteSSDto provisionDto){
        return checkCompatibleUrl(provisionDto.getVersionControl());
//        WebClient webClient = WebClient.create();
//        List<VersionSetProductDto> versionControl = provisionDto.getVersionControl();
//        return webClient.post()
//                .uri("http://localhost:8080/v1/globalSDN/SiteManagement/checkBatchCompatibility")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(versionControl))
//                .retrieve()
//                .bodyToMono(JsonNode.class)
//                .block();
    }

    public JsonNode checkCompatibleUrl(List<VersionSetProductDto> versionControl){
        WebClient webClient = WebClient.create();
        String accessToken = tokenService.getAccessToken();
//        List<VersionSetProductDto> versionControl = provisionDto.getVersionControl();
        return webClient.post()
                .uri("http://localhost:8080/v1/globalSDN/SiteManagement/checkBatchCompatibility")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .body(BodyInserters.fromValue(versionControl))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }

    // Method to retrieve and process data from Mono<CompatibilityCheckResultdto>
    public List<ProductListResponcedto> processCompatibilityData(JsonNode jsonString) {
        List<ProductListResponcedto> productList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        // Access the "compatibility" array
        JsonNode compatibilityArray = jsonString.get("compatibility");
        if (compatibilityArray != null && compatibilityArray.isArray()) {
            for (JsonNode compatibilityNode : compatibilityArray) {
                // Retrieve the "compatible" field value
                boolean compatible = compatibilityNode.path("compatible").asBoolean();
                System.out.println("Compatible: " + compatible);

                // Retrieve the "productsWithGreaterId" array
                JsonNode productsArray = compatibilityNode.path("productsWithGreaterId");
                List<ProductDto> productDtoList = new ArrayList<>();

                if (productsArray.isArray()) {
                    for (JsonNode productNode : productsArray) {
                        // Retrieve fields from each product object
                        String productName = productNode.path("product").asText();
                        String productVersion = productNode.path("version").asText();

                        // Create ProductDto object and add to list
                        ProductDto productDto = new ProductDto(productName, productVersion);
                        productDtoList.add(productDto);
                    }
                }

                // Create ProductListResponseDto object and set fields
                ProductListResponcedto responseDto = new ProductListResponcedto();
                responseDto.setCompatible(compatible);
                responseDto.setProductsWithGreaterId(productDtoList);

                productList.add(responseDto);
            }
        }

        return productList;

    }

}
