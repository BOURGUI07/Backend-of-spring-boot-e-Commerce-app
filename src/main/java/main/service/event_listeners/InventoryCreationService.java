/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service.event_listeners;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.client.InventoryApiClient;
import main.event.ProductCreationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true,level=AccessLevel.PRIVATE)
@Service
public class InventoryCreationService {
    InventoryApiClient client;
    
    
    @EventListener
    @Async
    public void createInventory(ProductCreationEvent event){
        var inventoryCreationRequest = event.getInventoryCreationRequest();
        client.createInventory(inventoryCreationRequest);
    }
}
