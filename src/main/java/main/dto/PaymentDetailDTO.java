/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import main.models.Order;
import main.util.PaymentProvider;
import main.util.PaymentStatus;
import main.validation.EntityIdExists;

/**
 *
 * @author hp
 */
@Schema(title = "PaymentDetailDTO", description = "Parameters required to create/update a payment detail")
public record PaymentDetailDTO(
        @EntityIdExists(entityClass =Order.class,message="Id must be not null, must by positive, and must exists")
        Integer orderId,
        @NotNull(message="payment provider is required")
        PaymentProvider provider,
        PaymentStatus status
        ) {

}
