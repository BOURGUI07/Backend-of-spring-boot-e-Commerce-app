/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import main.models.User;
import main.validation.EntityIdExists;

/**
 *
 * @author hp
 */
@Schema(title = "UserAddressDTO", description = "Parameters required to create/update a user address")
public record UserAddressDTO(
        Integer id,
        @EntityIdExists(entityClass =User.class,message="Id must be not null, must by positive, and must exists")
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
