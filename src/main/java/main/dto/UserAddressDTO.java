/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author hp
 */
public record UserAddressDTO(
        Integer id,
        @NotNull
        Integer userId,
        @NotBlank
        String addressLine1,
        String addressLine2,
        @NotBlank
        String city,
        @NotBlank
        String postalcode,
        @NotBlank
        String country
        ) {

}
