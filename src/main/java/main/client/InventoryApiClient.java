/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import main.dto.InventoryCreationRequest;
import main.dto.InventoryResponse;
import main.dto.InventoryUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author hp
 */
@Service
public class InventoryApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/inventories";
    private final RestClient client;

    public InventoryApiClient(RestClient client) {
        this.client = RestClient.create(BASE_URL);
    }
    
    public InventoryResponse updateInventory(InventoryUpdateRequest x){
        return client
                .put()
                .body(x)
                .retrieve()
                .toEntity(InventoryResponse.class)
                .getBody();
    }

    public InventoryResponse findInventoryForProductid(Integer productId) {
        return client
                .get()
                .uri("/product{id}", productId)
                .retrieve()
                .toEntity(InventoryResponse.class)
                .getBody();
    }
    
    public InventoryResponse createInventory(InventoryCreationRequest x){
        return client
                .post()
                .body(x)
                .retrieve()
                .toEntity(InventoryResponse.class)
                .getBody();
    }
}
