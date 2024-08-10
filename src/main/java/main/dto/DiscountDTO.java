/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.util.List;

/**
 *
 * @author hp
 */
public record DiscountDTO(
        Integer id,
        @NotBlank(message="discount name is required")
        String name,
        String desc,
        @Positive
        double percent,
        Boolean active,
        @NotEmpty(message="discount product list should contain at least one product")
        List<Integer> productIds,
        Integer version
        ) {

}
