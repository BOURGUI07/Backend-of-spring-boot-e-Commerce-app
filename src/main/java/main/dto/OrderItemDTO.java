/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 *
 * @author hp
 */
public record OrderItemDTO(
        Integer id,
        @NotNull(message="order Id is required")
        Integer orderId,
        @NotNull(message="product Id is required")
        Integer productid,
        @Positive(message="ordered quantity should be positive")
        Integer quantity
        ) {

}
