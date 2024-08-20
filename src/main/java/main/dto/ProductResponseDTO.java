/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import java.util.List;
import java.util.Optional;


/**
 *
 * @author hp
 */
public record ProductResponseDTO(
        Integer id,
        String name,
        String desc,
        String sku,
        Double price,
        Optional<String> categoryName,
        Optional<String> discountName,
        List<Integer> orderItemIds,
        Integer version
        ) {
    
}
