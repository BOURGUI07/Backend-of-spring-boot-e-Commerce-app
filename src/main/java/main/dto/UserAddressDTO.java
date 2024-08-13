/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author hp
 */
@Schema(title = "UserAddressDTO", description = "Parameters required to create/update a user address")
public record UserAddressDTO(
        Integer id,
        @NotNull
        Integer userId,
        @NotBlank(message="addressLine1 shouldn't be blank")
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
