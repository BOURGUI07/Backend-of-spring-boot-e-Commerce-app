/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import java.util.List;
import java.util.Set;
import main.dto.ProductResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author hp
 */
@Service
public class ProductApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/products/product-ids";
    private final RestTemplate restTemplate = new RestTemplate();
    
    public List<ProductResponseDTO> findAllByIds(Set<Integer> productIds){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Set<Integer>> requestEntity = new HttpEntity<>(productIds, headers);
        var responseEntity =
                restTemplate.exchange(BASE_URL, HttpMethod.GET, requestEntity, ProductResponseDTO[].class);

        return List.of(responseEntity.getBody());  
    }
}
