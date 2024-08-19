/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service.event_listeners;

import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.client.InventoryApiClient;
import main.dto.InventoryDTO;
import main.event.OrderCreationEvent;
import main.models.OrderItem;
import main.repo.OrderItemRepo;
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
    OrderItemRepo detailRepo;
    
    @EventListener
    @Order(2)
    public void updateInventory(OrderCreationEvent event){
        var order = event.getOrderRequest();
        var orderItems = detailRepo.findAllById(order.orderItemIds());
        var map = orderItems.stream().collect(Collectors.toMap(OrderItem::getProduct, OrderItem::getQuantity));
        map.keySet()
                .forEach(p->client.updateInventory(p.getInventory().getId(),
                new InventoryDTO(p.getInventory().getId(),p.getInventory().getQuantity()-map.get(p),p.getInventory().getVersion())));
    }
}
