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
public record SessionResponseDTO(
        Integer id,
        Integer userId,
        Double total,
        List<Integer> cartItemIds
        ) {

}
