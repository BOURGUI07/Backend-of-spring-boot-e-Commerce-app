/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@Schema(title = "CartItemDTO", description = "Parameters required to create/update a cart item")
@JsonIgnoreProperties(ignoreUnknown = true)
public record CartItemDTO(
        Integer id,
        @ValidId(message="Id must be not null, must by positive")
        Integer sessionId,
        @ValidId(message="Id must be not null, must by positive")
        Integer productId,
        @NotNull
        @Positive
        Integer quantity
        ) {
}
