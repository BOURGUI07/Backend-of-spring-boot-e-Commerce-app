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
public record CartItemDTO(
        Integer id,
        @NotNull
        Integer sessionId,
        @NotNull
        Integer productId,
        @Positive
        Integer quantity
        ) {
}
