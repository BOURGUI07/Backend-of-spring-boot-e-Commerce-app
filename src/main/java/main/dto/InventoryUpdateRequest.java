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
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "InventoryUpdateRequest", description = "Parameters required to update a product inventory")
public record InventoryUpdateRequest(
        @ValidId
        Integer productId,
        @Positive
        @NotNull
        Integer quantityToBeSubstracted
        ) {

}
