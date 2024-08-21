/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "UserAddressDTO", description = "Parameters required to create/update a user address")
public record UserAddressDTO(
        Integer id,
        @Schema(title="userId",nullable=false)
        @ValidId(message="Id must be not null, must by positive")
        Integer userId,
        @NotBlank(message="addressLine1 shouldn't be blank")
                @Schema(title="addressLine1",nullable=false)
        String addressLine1,
        @Schema(title="addressLine2",nullable=true)
        Optional<String> addressLine2,
        @NotBlank
                @Schema(title="city",nullable=false)
        String city,
        @NotBlank
                @Schema(title="postalcode",nullable=false)
        String postalcode,
        @NotBlank
                @Schema(title="country",nullable=false)
        String country
        ) {

}
