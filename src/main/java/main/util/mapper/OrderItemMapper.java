/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.OrderItemCreationRequest;
import main.dto.OrderItemResponse;
import main.models.OrderItem;
import main.repo.OrderRepo;
import main.repo.ProductRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class OrderItemMapper {
      ProductRepo prepo;
      OrderRepo orepo;
    
    public OrderItem toEntity(OrderItemCreationRequest x){
        var o = new OrderItem().setQuantity(x.quantity());
        prepo.findById(x.productid()).ifPresent(o::setProduct);
        orepo.findById(x.orderId()).ifPresent(o::setOrder);
        return o;
    }
    
    public OrderItemResponse toDTO(OrderItem i){
        var p = i.getProduct();
        var o = i.getOrder();
        return (o!=null && p!=null) ? new OrderItemResponse(i.getId(),o.getId(),p.getId(),i.getQuantity()):null;
    }
    
}
