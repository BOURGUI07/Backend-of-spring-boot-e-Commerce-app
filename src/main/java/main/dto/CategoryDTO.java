/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 *
 * @author hp
 */
@Schema(title = "CategoryDTO", description = "Parameters required to create/update a category")
public record CategoryDTO(
        Integer id,
        @NotBlank
        @Size(min=3,max=100,message="Category name must be"
                + "between 3 and 100 characters")
        String name,
        @Size(max=500,message="Category desc must be"
                + " at max 500 character length")
        String desc,
        List<Integer> productIds,
        Integer version
        ) {

}
