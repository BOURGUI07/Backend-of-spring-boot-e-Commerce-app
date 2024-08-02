/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author hp
 */
@Component
@Profile("!production")
public class UserUsernamePasswordAuthProvider implements AuthenticationProvider {
    @Autowired
    public UserUsernamePasswordAuthProvider(UserDetailsService service,@Lazy PasswordEncoder encoder) {
        this.service = service;
        this.encoder = encoder;
    }
    private final UserDetailsService service;
    private final PasswordEncoder encoder;
    
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        var username = auth.getName();
        var password = auth.getCredentials().toString();
        var user = service.loadUserByUsername(username);
        if(encoder.matches(password, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        }else{
            throw new BadCredentialsException("Invalid Password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
    
}
