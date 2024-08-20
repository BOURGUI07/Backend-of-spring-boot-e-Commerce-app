/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.ProductResponseDTO;
import main.dto.UserResponse;
import main.exception.CustomServerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author hp
 */
@Service
@FieldDefaults(makeFinal=true,level=AccessLevel.PRIVATE)
public class ProductApiClient {


     static  String BASE_URL = "http://localhost:8080/api/products";
      RestTemplate restTemplate = new RestTemplate();
      RestClient client=RestClient.create(BASE_URL);
    
    
    public List<ProductResponseDTO> findAllByIds(Set<Integer> productIds){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Set<Integer>> requestEntity = new HttpEntity<>(productIds, headers);
        var responseEntity =
                restTemplate.exchange(BASE_URL+"/product_ids", HttpMethod.GET, requestEntity, ProductResponseDTO[].class);

        return List.of(responseEntity.getBody());  
    }
    
    public ProductResponseDTO findProductById(Integer id){
        return client
                .get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                     throw new IllegalArgumentException("Either The Client Entered an Id that's below 1 or "
                             + "no product was found for id");
                 })
                .onStatus(HttpStatusCode::is5xxServerError, (request,response) -> {
                     throw new CustomServerException("Server is down");
                 })
                .toEntity(ProductResponseDTO.class)
                .getBody();
    }
}
