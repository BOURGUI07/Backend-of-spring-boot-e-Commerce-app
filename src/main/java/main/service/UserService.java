/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import lombok.RequiredArgsConstructor;
import main.dto.UserLoginRequestDTO;
import main.dto.UserLoginResponseDTO;
import main.dto.UserRegistrationRequestDTO;
import main.dto.UserRegistrationResponseDTO;
import main.repo.UserRepo;
import main.security.service.JwtService;
import main.util.mapper.AdminUserMapper;
import main.util.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo repo;
    private final UserMapper mapper;
    private final AdminUserMapper adminMapper;
    private final AuthenticationManager manager;
    private final JwtService jwtService;
    
    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO x){
        var user = mapper.toEntity(x);
        var saved = repo.save(user);
        return mapper.toDTO(saved);
    }
    
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO x){
        try {
            // Authenticate the user
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(x.username(), x.password())
            );

            // Generate JWT token
            String token = jwtService.generateToken((UserDetails)authentication.getPrincipal());

            // Return the login response with the token and expiration time
            UserLoginResponseDTO response = new UserLoginResponseDTO();
            response.setToken(token);
            response.setExpiresIn(jwtService.getJwtExpiration());

            return response;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
    
    public UserRegistrationResponseDTO registerAdmin(UserRegistrationRequestDTO x){
        var user = adminMapper.toEntity(x);
        var saved = repo.save(user);
        return adminMapper.toDTO(saved);
    }
}
