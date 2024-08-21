/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import main.validation.ValidId;

/**
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(title = "UserShoppingSessionDTO", description = "Parameters required to create/update a user shopping session")
public record UserShoppingSessionDTO(
        @ValidId(message="Id must be not null, must by positive")
        Integer userId,
        @NotEmpty
        Set<Integer> cartItemIds
        ) {

}
