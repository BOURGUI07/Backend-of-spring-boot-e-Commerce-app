/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import main.dto.OrderItemCreationRequest;
import main.dto.OrderItemResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author hp
 */
@Service
public class OrderItemApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/orderitems";
    private final RestClient client;

    public OrderItemApiClient(RestClient client) {
        this.client = RestClient.create(BASE_URL);
    }
    
    public OrderItemResponse createOrderItem(OrderItemCreationRequest x){
        return client
                .post()
                .accept(MediaType.APPLICATION_JSON)
                .body(x)
                .retrieve()
                .toEntity(OrderItemResponse.class)
                .getBody();
    }
}
