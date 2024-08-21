/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.client.InventoryApiClient;
import main.client.ProductApiClient;
import main.dto.OrderDTO;
import main.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
@Service
public class ProductAvailability {
    ProductApiClient client;
    InventoryApiClient invClient;
    
    public boolean checkAvailability(OrderDTO x){
        var map = x.productIdQtyMap();
        var desiredProductsIds = map.keySet();
        var availableProducts = client.findAllByIds(desiredProductsIds);
        if(availableProducts.size()==map.keySet().size()){
            var isQtyValid = desiredProductsIds.stream().allMatch(productId-> invClient.findInventoryForProductid(productId).quantity()>=map.get(productId));
            return isQtyValid;
        }else{
            throw new EntityNotFoundException("At least one products isn't found");
        }
    }
}
