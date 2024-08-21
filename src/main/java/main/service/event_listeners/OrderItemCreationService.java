/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service.event_listeners;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.client.OrderItemApiClient;
import main.dto.OrderItemCreationRequest;
import main.event.OrderProcessedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true,level=AccessLevel.PRIVATE)
@Service
public class OrderItemCreationService {
    OrderItemApiClient client;
    
    @EventListener
    @Order(1)
    public void createOrderItem(OrderProcessedEvent event){
        var order = event.getProcessedOrder();
        var map = order.productIdQtyMap();
        map.
                keySet().
                forEach(productId ->
                        client.createOrderItem(new OrderItemCreationRequest
                        (order.id(),productId,map.get(productId))
                    )
                );
    }
}
