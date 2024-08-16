/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import java.util.List;

/**
 *
 * @author hp
 */
public record DiscountResponseDTO(
        Integer id,
        String name,
        String desc,
        Double percent,
        Boolean active,
        List<Integer> productIds,
        Integer version
        ) {

}
