/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

/**
 *
 * @author hp
 */
public record OrderDTO(
        @NotNull
        Integer id,
        @NotNull
        Integer userId,
        @Positive
        Double total,
        @NotNull
        Integer paymentDetailId,
        List<Integer> orderItemIds
        ) {

}
