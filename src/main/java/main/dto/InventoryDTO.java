/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.PositiveOrZero;

/**
 *
 * @author hp
 */
public record InventoryDTO(
        Integer id,
        @PositiveOrZero(message="Product quantity should be positive")
        Integer quantity,
        Integer version
        ) {

}
