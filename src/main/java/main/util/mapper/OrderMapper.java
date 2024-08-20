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
    
    public Order toEntity(OrderDTO x){
        var o = new Order();
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

