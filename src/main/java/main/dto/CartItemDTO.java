/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import main.models.Product;
import main.models.UserShoppingSession;
import main.validation.EntityIdExists;

/**
 *
 * @author hp
 */
@Schema(title = "CartItemDTO", description = "Parameters required to create/update a cart item")
public record CartItemDTO(
        Integer id,
        @EntityIdExists(entityClass =UserShoppingSession.class,message="Id must be not null, must by positive, and must exists")
        Integer sessionId,
        @EntityIdExists(entityClass =Product.class,message="Id must be not null, must by positive, and must exists")
        Integer productId,
        @NotNull(message="Product Quantity is required")
        @Positive(message="Product Quantity Must be positive")
        Integer quantity
        ) {
}
