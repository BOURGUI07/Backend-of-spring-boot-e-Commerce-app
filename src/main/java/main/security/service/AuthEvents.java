/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author hp
 */
@Component
@Slf4j
public class AuthEvents {
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success){
        log.info("Successful Login For User: {}", success.getAuthentication().getName());
    }
    
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failure){
        log.info("Failed Login For User: {} due to: {}",
                failure.getAuthentication().getName(),
                failure.getException().getMessage());
    }
}
