/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import main.util.WishListVisibility;

/**
 *
 * @author hp
 */
@Schema(title = "WishListResponse", description = "Parameters of wishlist creation/update response")
public record WishListResponse(
        Integer id,
        String name,
        String desc,
        WishListVisibility visibility,
        Integer userId,
        String userName,
        List<Integer> productIds
        ) {

}
