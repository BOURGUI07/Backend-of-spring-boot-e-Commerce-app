/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import main.models.Product;
import main.models.User;
import main.validation.EntityIdExists;

/**
 *
 * @author hp
 */
@Schema(title = "ReviewsResponseDTO", description = "Parameters required to create a review")
public record ReviewsRequestDTO(
            @EntityIdExists(entityClass =User.class,message="Id must be not null, must by positive, and must exists")
            Integer userId,
            @EntityIdExists(entityClass =Product.class,message="Id must be not null, must by positive, and must exists")
            Integer productId,
            @NotNull(message="rating is required")
            @Min(value=1, message="Rating must be at least 1")
            @Max(value=5, message="Rating must be at most 5")
            Integer rating,
            Optional<String> title,
            Optional<String> content
        
        ) {

}
