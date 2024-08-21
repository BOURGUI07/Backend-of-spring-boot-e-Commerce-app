/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Optional;
import main.validation.ValidOptionalString;


/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "ProductRequestDTO", description = "Parameters required to create/update a product")
public record ProductRequestDTO(
        @NotBlank
        @Size(min=3,max=100,message="Product name must be between 3 and 100 characters")
        @Schema(title="name",description="product Name",nullable=false)
        String name,
        @ValidOptionalString(max=500, message="Product desc must be at max 500 characters")
        @Schema(title="desc",description="product description",nullable=true)
        Optional<String> desc,
        @NotBlank
        @Size(max=16)
        @Schema(title="sku",nullable=false,maximum="16")
        String sku,
        @NotNull
        @Positive(message="Product price must be positive")
        @Schema(title="price",description="product unit price",nullable=false)
        Double price,
        @Schema(title="categoryId",nullable=true)
        Optional<Integer> categoryId,
        @NotNull
        @Positive
        Integer quantity,
        @Schema(title="discountId",nullable=true)
        Optional<Integer> discountId
        ) {
}
