/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

/**
 *
 * @author hp
 */
public record DiscountDTO(
        @NotNull
        Integer id,
        @NotBlank
        String name,
        String desc,
        @Positive
        double percent,
        boolean active,
        List<Integer> productIds
        ) {

}
