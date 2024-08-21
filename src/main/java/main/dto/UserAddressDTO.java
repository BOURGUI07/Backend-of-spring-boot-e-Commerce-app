/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "UserAddressDTO", description = "Parameters required to create/update a user address")
public record UserAddressDTO(
        Integer id,
        @ValidId(message="Id must be not null, must by positive")
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
