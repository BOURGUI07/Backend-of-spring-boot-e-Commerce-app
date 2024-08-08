/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;


import main.util.PaymentProvider;
import main.util.PaymentStatus;

/**
 *
 * @author hp
 */
public record PaymentDetailResponseDTO(
        Integer id,
        Integer orderId,
        Double amount,
        PaymentProvider provider,
        PaymentStatus status
        ) {

}
