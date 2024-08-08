/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author hp
 */
public record ReviewsRequestDTO(
            @NotNull(message="userId is required")
            Integer userId,
            @NotNull(message="productId is required")
            Integer productId,
            @NotNull(message="rating is required")
            @Min(1)
            @Max(5)
            Integer rating,
            String title,
            String content
        
        ) {

}
