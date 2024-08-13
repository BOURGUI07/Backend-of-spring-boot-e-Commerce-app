/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 *
 * @author hp
 */
@Schema(title = "CartItemDTO", description = "Parameters required to create/update a cart item")
public record CartItemDTO(
        Integer id,
        @NotNull(message="session Id is required")
        Integer sessionId,
        @NotNull(message="Product Id is required")
        Integer productId,
        @NotNull(message="Product Quantity is required")
        @Positive(message="Product Quantity Must be positive")
        Integer quantity
        ) {
}
