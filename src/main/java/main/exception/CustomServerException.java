/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author hp
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomServerException extends RuntimeException{

    public CustomServerException(String message) {
        super(message);
    }
    
}
