/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.exception;

import lombok.AllArgsConstructor;

/**
 *
 * @author hp
 */
@AllArgsConstructor
public class ResourceError {
    private int status;
    private String message;
    private long timestamp;
}
