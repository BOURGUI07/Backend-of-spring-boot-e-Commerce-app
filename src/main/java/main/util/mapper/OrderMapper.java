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
import main.repo.OrderItemRepo;
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
      UserRepo userRepo;
      OrderItemRepo detailRepo;
     SalesTaxCalculationService taxService;
    
    public Order toEntity(OrderDTO x){
        var o = new Order();
        var user = userRepo.findById(x.userId()).get();
        var list = x.orderItemIds();
            var orderItems = detailRepo.findAllById(list);
            orderItems.forEach(o::addOrderItem);
            taxService.calculateTotalOrderPrice(o);
            user.addOrder(o);
            detailRepo.saveAll(orderItems);
            userRepo.save(user);
        
        return o;
    }
    
    public OrderResponseDTO toDTO(Order o){
        var list = o.getOrderItems().stream().map(x -> x.getId()).collect(Collectors.toList());
        var user = o.getUser();
        return (user != null) ? 
                new OrderResponseDTO(o.getId(), user.getId(), o.getTotal(), list,o.getVersion()) : null;
    }
}

