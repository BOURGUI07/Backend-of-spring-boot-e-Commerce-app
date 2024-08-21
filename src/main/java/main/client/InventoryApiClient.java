/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import main.dto.InventoryCreationRequest;
import main.dto.InventoryResponse;
import main.dto.InventoryUpdateRequest;
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
public class InventoryApiClient {
    @Value("${inventory.api.url}")
    private  String BASE_URL;
    private final RestClient client;

    public InventoryApiClient(RestClient client) {
        this.client = RestClient.create(BASE_URL);
    }
    
    public InventoryResponse updateInventory(InventoryUpdateRequest x){
        return client
                .put()
                .body(x)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                     throw new InvalidBodyRequestException("The client entered an invalid request body");
                 })
                .onStatus(HttpStatusCode::is5xxServerError, (request,response) -> {
                     throw new CustomServerException("Server is down");
                 })
                .toEntity(InventoryResponse.class)
                .getBody();
    }

    public InventoryResponse findInventoryForProductid(Integer productId) {
        return client
                .get()
                .uri("/product{id}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                     throw new IllegalArgumentException("Either The Client Entered an Id that's below 1 or "
                             + "no inventory was found for product id");
                 })
                .onStatus(HttpStatusCode::is5xxServerError, (request,response) -> {
                     throw new CustomServerException("Server is down");
                 })
                .toEntity(InventoryResponse.class)
                .getBody();
    }
    
    public InventoryResponse createInventory(InventoryCreationRequest x){
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
                .toEntity(InventoryResponse.class)
                .getBody();
    }
}
