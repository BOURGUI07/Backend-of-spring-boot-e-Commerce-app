/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author hp
 */
public record SalesTaxRequest(
        @NotBlank(message="country is required")
        String country,
        @NotNull(message="taxRate is required")
            @Min(value=0, message="Rating must be at least 0")
            @Max(value=100, message="Rating must be at most 100")
        Double taxRate
        ) {

}
