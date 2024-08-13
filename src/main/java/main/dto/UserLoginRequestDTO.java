/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author hp
 */
@Schema(title = "UserLoginRequestDTO", description = "Parameters required to a login request")
public record UserLoginRequestDTO(
        @NotNull
        String username,
        @NotNull
        String password
        ) {

}
