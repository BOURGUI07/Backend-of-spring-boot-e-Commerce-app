/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 *
 * @author hp
 */
@Schema(title = "UserRegistrationRequestDTO", description = "Parameters required to register a user")
public record UserRegistrationRequestDTO (
      
    @NotBlank
     String username,

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
             message = "password must be at least 8 characters,"
                     + " one lowercase letter,"
                     + " one uppercase letter,"
                     + " one number,"
                     + " and one special character")
     String password,

    @NotBlank
     String firstname,

    @NotBlank
     String lastname,

    @Email(message = "email should be valid")
    @NotNull
     String email,
    
     String phone
    
        ){

}
