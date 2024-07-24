/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 *
 * @author hp
 */
public record CategoryDTO(
        @NotNull
        Integer id,
        @NotBlank
        @Size(min=3,max=100,message="Category name must be"
                + "between 3 and 100 characters")
        String name,
        String desc,
        List<Integer> productIds
        ) {

}
