/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import main.dto.PaymentDetailDTO;
import main.dto.PaymentDetailResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author hp
 */
@Service
public class PaymentDetailApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/payment_details";
    private final RestClient client;

    public PaymentDetailApiClient(RestClient client) {
        this.client = RestClient.create(BASE_URL);
    }
    
    public PaymentDetailResponseDTO createPayment(PaymentDetailDTO request){
        return client
                .post()
                .body(request)
                .retrieve()
                .toEntity(PaymentDetailResponseDTO.class)
                .getBody();
    }
}
