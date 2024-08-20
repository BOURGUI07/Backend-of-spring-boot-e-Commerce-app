/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author hp
 */
@Schema(title = "InventoryResponse", description = "Parameters required to create/update an inventory")
public record InventoryResponse(
        Integer id,
        Integer productId,
        Integer quantity,
        Integer version
        ) {

}
