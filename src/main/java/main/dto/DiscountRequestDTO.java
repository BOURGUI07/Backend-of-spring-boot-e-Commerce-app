/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.Optional;
import main.validation.ValidOptionalString;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "DiscountRequestDTO", description = "Parameters required to create/update a discount")
public record DiscountRequestDTO(
        @NotBlank(message="discount name is required")
        @Schema(title="name",description="Discount Name",nullable=false)
        String name,
        @ValidOptionalString(max=500, message="Product desc must be at max 500 characters")
        @Schema(title="desc",description="Discount Description",nullable=true)
        Optional<String> desc,
        @Positive
        @Schema(title="percent",description="dicount percent",nullable=false,maximum="1")
        Double percent,
        @Schema(title="active",description="discount status",nullable=true)
        Optional<Boolean> active
        ) {

}
