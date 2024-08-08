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
        @NotNull
        Integer userId,
        @NotNull
        Integer paymentDetailId,
        @NotNull(message="The Order Item List is Required")
        @NotEmpty(message="The Order Item List should Contain at least one element")
        List<Integer> orderItemIds
        ) {

}
