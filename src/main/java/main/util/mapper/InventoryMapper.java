/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.RequiredArgsConstructor;
import main.dto.InventoryDTO;
import main.models.Inventory;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class InventoryMapper {
    
    public Inventory toEntity(InventoryDTO x){
        var i = new Inventory().setQuantity(x.quantity());
        return i;
    }
    
    public InventoryDTO toDTO(Inventory i){
        return new InventoryDTO(i.getId(),i.getQuantity());
    }
}
