/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import main.dto.PaymentDetailDTO;
import main.dto.PaymentDetailResponseDTO;
import main.exception.CustomServerException;
import main.exception.InvalidBodyRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author hp
 */
@Service
public class PaymentDetailApiClient {
    @Value("${paymentdetail.api.url}")
    private String BASE_URL;
    private final RestClient client;

    public PaymentDetailApiClient(RestClient client) {
        this.client = RestClient.create(BASE_URL);
    }
    
    public PaymentDetailResponseDTO createPayment(PaymentDetailDTO detailRequest){
        return client
                .post()
                .accept(APPLICATION_JSON)
                .body(detailRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                     throw new InvalidBodyRequestException("The client entered an invalid request body");
                 })
                .onStatus(HttpStatusCode::is5xxServerError, (request,response) -> {
                     throw new CustomServerException("Server is down");
                 })
                .toEntity(PaymentDetailResponseDTO.class)
                .getBody();
    }
}
