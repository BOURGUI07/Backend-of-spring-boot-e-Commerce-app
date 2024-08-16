/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import main.models.WishList;
import main.validation.EntityIdExists;

/**
 *
 * @author hp
 */
@Schema(title = "WishListMergeRequest", description = "Parameters of wishlist merge request")
public record WishListMergeRequest(
        @EntityIdExists(entityClass =WishList.class,message="Id must be not null, must by positive, and must exists")
        Integer targetWishId,
        @NotEmpty(message="wish list Id list should contain at least one element")
        Set<Integer> wishListIds
        ) {

}
