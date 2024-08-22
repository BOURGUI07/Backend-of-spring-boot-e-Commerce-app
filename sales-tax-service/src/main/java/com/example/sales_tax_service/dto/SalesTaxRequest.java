/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package com.example.sales_tax_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "SalesTaxRequest", description = "Parameters required to create/update a sales tax")
public record SalesTaxRequest(
        @NotBlank(message="country is required")
        String country,
        @NotNull(message="taxRate is required")
            @Min(value=0, message="tax rate must be at least 0")
            @Max(value=100, message="tax rate must be at most 100")
        Double taxRate
        ) {

}
