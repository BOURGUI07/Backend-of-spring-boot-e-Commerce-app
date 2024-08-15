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
@Schema(title = "WishListMergeRequest", description = "Parameters of wishlist merge request")
public record WishListMergeRequest(
        @NotNull(message="wish list id is required")
        Integer targetWishId,
        @NotEmpty(message="wish list Id list should contain at least one element")
        List<Integer> wishListIds
        ) {

}