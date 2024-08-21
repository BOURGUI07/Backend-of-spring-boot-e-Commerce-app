/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.UserAddressDTO;
import main.dto.UserAddressRequest;
import main.dto.UserLoginRequestDTO;
import main.dto.UserLoginResponseDTO;
import main.dto.UserRegistrationRequestDTO;
import main.dto.UserRegistrationResponseDTO;
import main.dto.UserResponse;
import main.event.UserCreationEvent;
import main.exception.EntityNotFoundException;
import main.repo.UserRepo;
import main.security.service.JwtService;
import main.util.mapper.AdminUserMapper;
import main.util.mapper.UserMapper;
import org.springframework.context.ApplicationEventPublisher;
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
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class UserService {
      UserRepo repo;
      UserMapper mapper;
      AdminUserMapper adminMapper;
      AuthenticationManager manager;
      JwtService jwtService;
      ApplicationEventPublisher eventPublisher;
    
    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO x){
        var user = mapper.toEntity(x);
        var saved = repo.save(user);
        var addressRequest = new UserAddressRequest(saved.getId(),x.addressLine1(),x.addressLine2(),x.city(),x.postalcode() ,x.country());
        eventPublisher.publishEvent(new UserCreationEvent(this,addressRequest));
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
    
    
    public UserResponse findById(Integer id){
        if(id<1){
            throw new IllegalArgumentException("");
        }
        return repo.findById(id)
                .map(mapper::toDTOEmail)
                .orElseThrow(() -> new EntityNotFoundException("no user was found for id " + id));
    }
}
