/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.client;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.UserAddressDTO;
import main.dto.UserAddressRequest;
import main.exception.CustomServerException;
import main.exception.InvalidBodyRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author hp
 */
@Service
@FieldDefaults(makeFinal=true,level=AccessLevel.PRIVATE)
public class UserAddressApiClient {
    @Value("${useraddress.api.url}")
    @NonFinal String url;
    
    RestClient client = RestClient.create(url);
    public UserAddressDTO createUserAddress(UserAddressRequest addressRequest) {
        return client
                .post()
                .body(addressRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,response) -> {
                     throw new InvalidBodyRequestException("The client entered an invalid request body");
                 })
                .onStatus(HttpStatusCode::is5xxServerError, (request,response) -> {
                     throw new CustomServerException("Server is down");
                 })
                .toEntity(UserAddressDTO.class)
                .getBody();
                
    }
    
}
