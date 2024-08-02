/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import lombok.Data;

/**
 *
 * @author hp
 */
@Data
public class UserLoginResponseDTO {
    private String token;
    private long expiresIn;
}
