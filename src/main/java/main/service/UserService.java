/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import lombok.RequiredArgsConstructor;
import main.dto.UserRegistrationRequestDTO;
import main.dto.UserRegistrationResponseDTO;
import main.repo.UserRepo;
import main.util.mapper.AdminUserMapper;
import main.util.mapper.UserMapper;
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
    
    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO x){
        var user = mapper.toEntity(x);
        var saved = repo.save(user);
        return mapper.toDTO(saved);
    }
    
    public UserRegistrationResponseDTO registerAdmin(UserRegistrationRequestDTO x){
        var user = adminMapper.toEntity(x);
        var saved = repo.save(user);
        return adminMapper.toDTO(saved);
    }
}
