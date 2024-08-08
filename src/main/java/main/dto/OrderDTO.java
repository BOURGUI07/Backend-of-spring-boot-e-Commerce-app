/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @author hp
 */
public record OrderDTO(
        Integer id,
        @NotNull(message="user Id is required")
        Integer userId,
        @NotNull(message="payment detail Id is required")
        Integer paymentDetailId,
        @NotEmpty(message="The Order Item List should Contain at least one element")
        List<Integer> orderItemIds
        ) {

}
