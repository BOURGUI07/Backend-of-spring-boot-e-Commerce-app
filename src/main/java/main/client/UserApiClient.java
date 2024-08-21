/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import main.dto.UserResponse;
import main.exception.CustomServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author hp
 */
@Service
public class UserApiClient {
    @Value("${user.api.url}")
    private String BASE_URL = "http://localhost:9090/api/users";

    public UserApiClient(RestClient client) {
        this.client = RestClient.create(BASE_URL);
    }
    private final RestClient client;
    
    public UserResponse findUserById(Integer id){
        return client
                .get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                     throw new IllegalArgumentException("Either The Client Entered an Id that's below 1 or "
                             + "no user was found for id");
                 })
                .onStatus(HttpStatusCode::is5xxServerError, (request,response) -> {
                     throw new CustomServerException("Server is down");
                 })
                .toEntity(UserResponse.class)
                .getBody();
    }
}
