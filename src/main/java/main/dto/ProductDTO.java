/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;


/**
 *
 * @author hp
 */
public record ProductDTO(
        @NotBlank
        Integer Id,
        @NotBlank
        @Size(min=3,max=100,message="Product name must be between 3 and 100 characters")
        String name,
        String desc,
        @NotBlank
        @Size(max=16)
        String sku,
        @Positive(message="Product price must be positive")
        Double price,
        @NotNull
        Integer categoryId,
        @NotNull
        Integer inventoryId,
        @NotNull
        Integer discountId,
        List<Integer> orderItemsIds
        ) {
    
}
