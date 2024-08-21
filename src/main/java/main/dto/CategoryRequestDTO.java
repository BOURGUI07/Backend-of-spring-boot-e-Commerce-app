/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Optional;
import main.validation.ValidOptionalString;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "CategoryRequestDTO", description = "Parameters required to create/update a category")
public record CategoryRequestDTO(
        @NotBlank
        @Size(min=3,max=100,message="Category name must be"
                + "between 3 and 100 characters")
        @Schema(title="name",description="Catgeory Name",nullable=false)
        String name,
        @ValidOptionalString(max=500, message="Product desc must be at max 500 characters")
        @Schema(title="desc",description="Catgeory Description",nullable=true)
        Optional<String> desc
        ) {

}
