/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import main.models.Category;
import main.validation.EntityIdExists;

/**
 *
 * @author hp
 */
@Schema(title = "AddProductsToCategoryRequest", description = "Parameters of adding products to category request")
public record AddProductsToCategoryRequest(
        @EntityIdExists(entityClass =Category.class,message="Id must be not null, must by positive, and must exists")
        Integer categoryId,
        @NotEmpty(message="list of product Ids must contains at least one element")
        Set<Integer> productIds
        ) {

}
