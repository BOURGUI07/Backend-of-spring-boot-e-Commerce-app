/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import main.util.WishListVisibility;

/**
 *
 * @author hp
 */
@Schema(title = "WishListCreationRequest", description = "Parameters required to create a wishList")
public record WishListCreationRequest(
        @Size(min=3,max=100,message="wish list name must be between 3 and 100 characters")
        @NotBlank(message="wish list name is required")
        String name,
        @Size(max=500,message="wishlist desc must be at max 500 characters")
        String desc,
        @NotNull(message="wishlist visibility is required")
        WishListVisibility visibility,
        @NotNull(message="userid is required")
        @Positive(message="user id can't be less than 1")
        Integer userId
        ) {
}
