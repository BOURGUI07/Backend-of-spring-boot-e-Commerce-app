/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.RequiredArgsConstructor;
import main.dto.OrderItemDTO;
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
public class OrderItemMapper {
    private final ProductRepo prepo;
    private final OrderRepo orepo;
    
    public OrderItem toEntity(OrderItemDTO x){
        var o = new OrderItem().setQuantity(x.quantity());
        prepo.findById(x.productid()).ifPresent(o::setProduct);
        orepo.findById(x.orderId()).ifPresent(o::setOrder);
        return o;
    }
    
    public OrderItemDTO toDTO(OrderItem i){
        var p = i.getProduct();
        var o = i.getOrder();
        return (o!=null && p!=null) ? new OrderItemDTO(i.getId(),o.getId(),p.getId(),i.getQuantity()):null;
    }
    
}
