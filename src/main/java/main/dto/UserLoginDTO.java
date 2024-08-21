/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 *
 * @author hp
 */
@Schema(title="UserLoginDTO",description="The body request of login")
public record UserLoginDTO(
        @Schema(title="username",nullable=false)
        @NotBlank
        String username,
        @NotBlank
             @Schema(title="password",nullable=false)   
        String password
        ) {
    
}
