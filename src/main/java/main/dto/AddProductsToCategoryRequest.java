/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 *
 * @author hp
 */
@Schema(title = "AddProductsToCategoryRequest", description = "Parameters of adding products to category request")
public record AddProductsToCategoryRequest(
        @NotNull(message="discout id is required")
        Integer categoryId,
        @NotEmpty(message="list of product Ids must contains at least one element")
        List<Integer> productIds
        ) {

}
