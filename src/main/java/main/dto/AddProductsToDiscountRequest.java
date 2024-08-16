/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import main.models.Discount;
import main.validation.EntityIdExists;

/**
 *
 * @author hp
 */
@Schema(title = "AddProductsToDiscountRequest", description = "Parameters of adding products to discount request")
public record AddProductsToDiscountRequest(
        @EntityIdExists(entityClass =Discount.class,message="Id must be not null, must by positive, and must exists")
        Integer discountId,
        @NotEmpty(message="list of product Ids must contains at least one element")
        List<Integer> productIds
        ) {

}
