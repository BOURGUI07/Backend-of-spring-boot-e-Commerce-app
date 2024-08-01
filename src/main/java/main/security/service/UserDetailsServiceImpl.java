/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.security.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import main.repo.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepo repo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repo.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(""
                        + "User Details with username: " + username+ " isn't found!"));
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().getName().name()));
        return new User(username, user.getPassword(),authorities);
    }
    
}
