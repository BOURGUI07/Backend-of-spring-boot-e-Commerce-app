/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package main.dto;

/**
 *
 * @author hp
 */
public record UserResponse(
        String username,
        String firstname,
        String lastname,
        String email
        ) {

}
