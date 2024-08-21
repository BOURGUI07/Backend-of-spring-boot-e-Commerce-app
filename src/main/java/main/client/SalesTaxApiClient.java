/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

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
public class SalesTaxApiClient {
    @Value("${salestax.api.url}")
    private String BASE_URL;

    public SalesTaxApiClient(RestClient client) {
        this.client = RestClient.create(BASE_URL);
    }
    private final RestClient client;

    public Double getTaxRateForCountry(String country) {
        return client
                .get()
                .uri("/country/{country}",country)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                     throw new IllegalArgumentException("The client entered a blank country");
                 })
                .onStatus(HttpStatusCode::is5xxServerError, (request,response) -> {
                     throw new CustomServerException("Server is down");
                 })
                .toEntity(Double.class)
                .getBody();
    }
    
    
}
