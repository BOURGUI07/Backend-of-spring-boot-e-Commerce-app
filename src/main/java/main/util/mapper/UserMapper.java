/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.UserRegistrationDTO;
import main.dto.UserResponseDTO;
import main.models.User;
import main.repo.OrderRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class UserMapper {
    private final OrderRepo orderRepo;
    
    public User toEntity(UserRegistrationDTO x){
        var u = new User();
        u.setEmail(x.email());
        u.setFirstname(x.firstname());
        u.setLastname(x.lastname());
        u.setPassword(x.password());
        u.setUsername(x.username());
        u.setPhone(x.phone());
        u.setRole(x.role());
        var orderList = x.orderIds();
        if(orderList!=null){
            u.setOrders(orderRepo.findAllById(orderList));
        }
        return u;
    }
    
    public UserResponseDTO toDTO(User u){
        var list = u.getOrders().stream().map(o -> o.getId()).collect(Collectors.toList());
        return new UserResponseDTO(u.getId(),u.getUsername(),u.getFirstname(),u.getLastname(),u.getEmail(),u.getPhone(),u.getRole(),list);
    }
}
