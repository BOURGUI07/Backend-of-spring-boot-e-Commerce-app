/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@Schema(title = "OrderDTO", description = "Parameters required to create/update an order")
public record OrderDTO(
        Integer id,
        @ValidId(message="Id must be not null, must by positive")
        Integer userId,
        @ValidId(message="Id must be not null, must by positive")
        Integer paymentDetailId,
        @NotEmpty(message="The Order Item List should Contain at least one element")
        Set<Integer> orderItemIds        ) {

}
