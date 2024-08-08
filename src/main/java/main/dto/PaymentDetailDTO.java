/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotNull;
import main.util.PaymentProvider;
import main.util.PaymentStatus;

/**
 *
 * @author hp
 */
public record PaymentDetailDTO(
        @NotNull(message="order Id is required")
        Integer orderId,
        @NotNull(message="payment provider is required")
        PaymentProvider provider,
        PaymentStatus status
        ) {

}
