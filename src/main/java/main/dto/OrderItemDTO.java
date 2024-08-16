/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import main.models.Order;
import main.models.Product;
import main.validation.EntityIdExists;
import main.validation.ValidQuantity;

/**
 *
 * @author hp
 */
@ValidQuantity(productIdField = "productid", quantityField = "quantity",
    message = "Quantity is required, Quantity must be positive, Requested quantity exceeds available inventory")
@Schema(title = "OrderItemDTO", description = "Parameters required to create/update an order")
public record OrderItemDTO(
        Integer id,
        @EntityIdExists(entityClass =Order.class,message="Id must be not null, must by positive, and must exists")
        Integer orderId,
        @EntityIdExists(entityClass =Product.class,message="Id must be not null, must by positive, and must exists")
        Integer productid,
        Integer quantity
        ) {

}
