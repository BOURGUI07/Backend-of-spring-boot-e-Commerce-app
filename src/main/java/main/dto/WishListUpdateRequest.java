/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Optional;
import main.util.WishListVisibility;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "WishListUpdateRequest", description = "Parameters required to update a wishList")
public record WishListUpdateRequest(
        @Size(min=3,max=100,message="wish list name must be between 3 and 100 characters")
        @NotBlank(message="wish list name is required")
                @Schema(title="name",description="wishlist name",nullable=false)
        String name,
        @Size(max=500,message="wishlist desc must be at max 500 characters")
                @Schema(title="desc",description="wishlist description",nullable=true)
        Optional<String> desc,
        @NotNull(message="wishlist visibility is required")
                @Schema(title="visibility",nullable=false,description="wishlist visibility",allowableValues = {"PRIVATE", "PUBLIC"})
        WishListVisibility visibility
        ) {

}
