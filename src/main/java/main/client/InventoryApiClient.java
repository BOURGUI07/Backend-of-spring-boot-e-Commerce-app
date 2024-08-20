/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import main.dto.InventoryDTO;
import main.dto.InventoryUpdateRequest;
import main.dto.PaymentDetailDTO;
import main.dto.PaymentDetailResponseDTO;
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
    
    public InventoryDTO updateInventory(InventoryUpdateRequest x){
        return client
                .put()
                .body(x)
                .retrieve()
                .toEntity(InventoryDTO.class)
                .getBody();
    }

    public InventoryDTO findInventoryForProductid(Integer productId) {
        return client
                .get()
                .uri("/product{id}", productId)
                .retrieve()
                .toEntity(InventoryDTO.class)
                .getBody();
    }
}
