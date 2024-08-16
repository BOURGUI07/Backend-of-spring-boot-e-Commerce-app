/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import main.models.Order;
import main.models.Product;
import main.validation.EntityIdExists;

/**
 *
 * @author hp
 */
@Schema(title = "OrderItemDTO", description = "Parameters required to create/update an order")
public record OrderItemDTO(
        Integer id,
        @EntityIdExists(entityClass =Order.class,message="Id must be not null, must by positive, and must exists")
        Integer orderId,
        @EntityIdExists(entityClass =Product.class,message="Id must be not null, must by positive, and must exists")
        Integer productid,
        @Positive(message="ordered quantity should be positive")
        Integer quantity
        ) {

}
