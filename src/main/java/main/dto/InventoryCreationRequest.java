/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "InventoryCreationRequest", description = "Parameters required to create an inventory")
public record InventoryCreationRequest(
        @ValidId
        @Schema(title="productId",nullable=false)
        Integer productId,
        @Positive(message="Product quantity should be positive")
        @NotNull
        @Schema(title="quantity",description="product quantity",nullable=false,minimum="1")
        Integer quantity
        ) {

}
