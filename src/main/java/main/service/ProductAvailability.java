/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.OrderDTO;
import main.exception.EntityNotFoundException;
import main.models.OrderItem;
import main.models.Product;
import main.repo.OrderItemRepo;
import main.repo.ProductRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
@Service
public class ProductAvailability {
    ProductRepo repo;
    OrderItemRepo detailRepo;
    
    public boolean checkAvailability(OrderDTO x){
        var orderItems  = detailRepo.findAllById(x.orderItemIds());
        var map = orderItems.stream().collect(Collectors.toMap(OrderItem::getProduct, OrderItem::getQuantity));
        var desiredProductsIds = map.keySet().stream().map(Product::getId).toList();
        var availableProducts = repo.findAllById(desiredProductsIds);
        if(availableProducts.size()==map.keySet().size()){
            var isQtyValid = availableProducts.stream().allMatch(p-> p.getInventory().getQuantity()>=map.get(p));
            return isQtyValid;
        }else{
            throw new EntityNotFoundException("At least one products isn't found");
        }
    }
}
