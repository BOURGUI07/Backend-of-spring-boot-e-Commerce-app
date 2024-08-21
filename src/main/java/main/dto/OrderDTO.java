/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.Map;
import main.util.PaymentProvider;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "OrderDTO", description = "Parameters required to create/update an order")
public record OrderDTO(
        Integer id,
        @ValidId(message="Id must be not null, must by positive")
        Integer userId,
        PaymentProvider provider,
        @NotEmpty(message="The ProductIds and Quantity Map should Contain at least one element")
        Map<Integer,Integer> productIdQtyMap        ) {

}
