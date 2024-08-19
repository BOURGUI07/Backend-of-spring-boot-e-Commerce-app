/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author hp
 */
@Service
public class SalesTaxApiClient {
    private static final String BASE_URL = "http://localhost:9090/api/sales_taxes";

    public SalesTaxApiClient(RestClient client) {
        this.client = RestClient.create(BASE_URL);
    }
    private final RestClient client;

    public Double getTaxRateForCountry(String country) {
        return client
                .get()
                .uri("/country/{country}",country)
                .retrieve()
                .toEntity(Double.class)
                .getBody();
    }
    
    
}
