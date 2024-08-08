/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;


import java.util.List;

/**
 *
 * @author hp
 */
public record OrderResponseDTO(
        Integer id,
        Integer userId,
        Double total,
        Integer paymentDetailId,
        List<Integer> orderItemIds
        ) 
         {
    
}
