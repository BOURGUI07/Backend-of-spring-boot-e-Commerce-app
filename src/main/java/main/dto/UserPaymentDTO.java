/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import main.util.PaymentProvider;
import main.util.PaymentType;

/**
 *
 * @author hp
 */
public record UserPaymentDTO(
        Integer id,
        @NotNull
        Integer userId,
        @NotNull
        PaymentType type,
        @NotNull
        PaymentProvider provider,
        @NotNull
        Integer accountNo,
        @NotNull
        LocalDate expiryDate
        ) {

}
