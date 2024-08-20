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
import java.util.Optional;
import main.validation.ValidOptionalString;
import main.validation.ValidId;


/**
 *
 * @author hp
 */
@Schema(title = "ProductRequestDTO", description = "Parameters required to create/update a product")
public record ProductRequestDTO(
        @NotBlank
        @Size(min=3,max=100,message="Product name must be between 3 and 100 characters")
        String name,
        @ValidOptionalString(max=500, message="Product desc must be at max 500 characters")
        Optional<String> desc,
        @NotBlank
        @Size(max=16)
        String sku,
        @Positive(message="Product price must be positive")
        Double price,
        Optional<Integer> categoryId,
        @NotNull
        @Positive
        Integer quantity,
        Optional<Integer> discountId
        ) {
}
