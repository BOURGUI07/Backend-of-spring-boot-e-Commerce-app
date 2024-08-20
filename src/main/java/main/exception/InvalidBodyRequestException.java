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
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBodyRequestException extends RuntimeException {

    public InvalidBodyRequestException(String message) {
        super(message);
    }
    
}
