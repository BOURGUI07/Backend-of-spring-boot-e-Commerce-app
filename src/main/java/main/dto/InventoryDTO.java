/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;

/**
 *
 * @author hp
 */
@Schema(title = "InventoryDTO", description = "Parameters required to create/update an inventory")
public record InventoryDTO(
        Integer id,
        @PositiveOrZero(message="Product quantity should be positive")
        Integer quantity,
        Integer version
        ) {

}
