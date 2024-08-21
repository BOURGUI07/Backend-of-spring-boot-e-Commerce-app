/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service.event_listeners;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.client.InventoryApiClient;
import main.dto.InventoryUpdateRequest;
import main.event.OrderCreationEvent;
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
public class UpdateInventoryService {
    InventoryApiClient client;
    
    @EventListener
    @Order(2)
    public void updateInventory(OrderProcessedEvent event){
        var order = event.getProcessedOrder();
        var map = order.productIdQtyMap();
        map.keySet()
                .forEach(id->client.updateInventory(new InventoryUpdateRequest(id,map.get(id))));
    }
}
