/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
import main.models.Order;
import main.repo.ProductRepo;
import main.repo.UserRepo;
import main.service.SalesTaxCalculationService;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class OrderMapper {
     SalesTaxCalculationService taxService;
     UserRepo repo;
     ProductRepo productRepo;
    
    public Order toEntity(OrderDTO x){
        var o = new Order();
        repo.findById(x.userId()).ifPresent(o::setUser);
        repo.save(o.getUser());
        var map = x.productIdQtyMap();
        var products = productRepo.findAllById(map.keySet());
        var orderAmount = products
                .stream()
                .mapToDouble(p -> p.discountedPrice()*map.get(p.getId()))
                .sum();
        o.setTotal(orderAmount);
        taxService.calculateTotalOrderPrice(o);
        
        return o;
    }
    
    public OrderResponseDTO toDTO(Order o){
        var list = o.getOrderItems().stream().map(x -> x.getId()).collect(Collectors.toList());
        var user = o.getUser();
        return (user != null) ? 
                new OrderResponseDTO(o.getId(), user.getId(), o.getTotal(), list,o.getVersion()) : null;
    }
}

