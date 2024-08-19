/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.client.SalesTaxApiClient;
import main.models.Order;
import main.repo.AddressRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class SalesTaxCalculationService {
      AddressRepo addressRepo;
      SalesTaxApiClient client;
    public void calculateTotalOrderPrice(Order o){
        /*
            to accuratley calculate the total order price
            we have to first check if the user retrieved by the request userId is existent
            we have to find the country of the order
            and since for every country has its own taxtRate, we gonna extract the taxrate based on the country
            then we have to change the taxRate from like 56.2% to like 0.562
            then we gonna multiply the result by the order total non-taxedPrice
            after that we gonna add the result to the order non-taxed total
            the result is the taxed total order price
        */
        if(o.getUser()!=null){
            o.setTotal(o.getTotal() + o.getTotal()*changeTax(client.getTaxRateForCountry(findOrderCountry(o))));
        }
    }
    
    private Double changeTax(Double taxtRate){
        return taxtRate/100;
    }
    
    private String findOrderCountry(Order o){
        return (o.getUser()!=null) ? addressRepo.findByUserId(o.getUser().getId()).get().getCountry():null;
    }
}
