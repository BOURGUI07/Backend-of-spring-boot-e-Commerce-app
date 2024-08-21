/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import main.dto.OrderItemCreationRequest;
import main.dto.OrderItemResponse;
import main.exception.CustomServerException;
import main.exception.InvalidBodyRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author hp
 */
@Service
public class OrderItemApiClient {
    @Value("${orderitem.api.url}")
    private  String BASE_URL;
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
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                     throw new InvalidBodyRequestException("The client entered an invalid request body");
                 })
                .onStatus(HttpStatusCode::is5xxServerError, (request,response) -> {
                     throw new CustomServerException("Server is down");
                 })
                .toEntity(OrderItemResponse.class)
                .getBody();
    }
}
