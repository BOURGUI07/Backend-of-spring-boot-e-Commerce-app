/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;


/**
 *
 * @author hp
 */
@Schema(title = "ProductDTO", description = "Parameters required to create/update a product")
public record ProductDTO(
        Integer Id,
        @NotBlank
        @Size(min=3,max=100,message="Product name must be between 3 and 100 characters")
        String name,
        @Size(max=500,message="Product desc must be at max 500 characters")
        String desc,
        @NotBlank
        @Size(max=16)
        String sku,
        @Positive(message="Product price must be positive")
        Double price,
        Integer categoryId,
        @NotNull(message="inventory id required")
        Integer inventoryId,
        Integer discountId,
        List<Integer> orderItemsIds,
        Integer version
        ) {
}
