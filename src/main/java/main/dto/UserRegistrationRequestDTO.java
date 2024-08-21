/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Optional;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "UserRegistrationRequestDTO", description = "Parameters required to register a user")
public record UserRegistrationRequestDTO (
      
    @NotBlank
            @Schema(title="username",nullable=false)
     String username,

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
             message = "password must be at least 8 characters,"
                     + " one lowercase letter,"
                     + " one uppercase letter,"
                     + " one number,"
                     + " and one special character")
            @Schema(title="password",nullable=false)
     String password,

    @NotBlank
            @Schema(title="firstname",nullable=false)
     String firstname,

    @NotBlank
            @Schema(title="lastname",nullable=false)
     String lastname,

    @Email(message = "email should be valid")
    @NotNull
            @Schema(title="email",nullable=false)
     String email,
    
    @Schema(title="phone",nullable=false)
            @NotNull
     String phone,
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
    
        ){

}
