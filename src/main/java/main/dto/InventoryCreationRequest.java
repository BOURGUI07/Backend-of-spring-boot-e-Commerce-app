/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@Schema(title = "InventoryCreationRequest", description = "Parameters required to create an inventory")
public record InventoryCreationRequest(
        @ValidId
        Integer productId,
        @Positive(message="Product quantity should be positive")
        @NotNull   
        Integer quantity
        ) {

}
