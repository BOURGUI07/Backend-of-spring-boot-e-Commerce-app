/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.InventoryCreationRequest;
import main.dto.InventoryResponse;
import main.models.Inventory;
import main.repo.ProductRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class InventoryMapper {
    ProductRepo repo;
    
    public Inventory toEntity(InventoryCreationRequest x){
        var i = new Inventory().setQuantity(x.quantity());
        repo.findById(x.productId()).ifPresent(i::setProduct);
        return i;
    }
    
    public InventoryResponse toDTO(Inventory i){
        return new InventoryResponse(i.getId(),i.getProduct().getId(), i.getQuantity(),i.getVersion());
    }
}
