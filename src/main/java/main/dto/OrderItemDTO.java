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
@Schema(title = "OrderItemDTO", description = "Parameters required to create/update an order")
public record OrderItemDTO(
        Integer id,
        @ValidId(message="Id must be not null, must by positive")
        Integer orderId,
        @ValidId(message="Id must be not null, must by positive")
        Integer productid,
        @NotNull
        @Positive
        Integer quantity
        ) {

}
