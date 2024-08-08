/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

/**
 *
 * @author hp
 */
public record ReviewsResponseDTO(
        Integer id,
        String username,
        String productName,
        Integer rating,
        String title,
        String content
        ) {
    
}
