/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.UserDTO;
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
    
    public User toEntity(UserDTO x){
        var u = new User();
        u.setEmail(x.getEmail());
        u.setFirstname(x.getFirstname());
        u.setLastname(x.getLastname());
        u.setPassword(x.getPassword());
        u.setUsername(x.getUsername());
        u.setPhone(x.getPhone());
        u.setRole(x.getRole());
        var orderList = x.getOrderIds();
        if(orderList!=null){
            u.setOrders(orderRepo.findAllById(orderList));
        }
        return u;
    }
    
    public UserDTO toDTO(User u){
        var x = new UserDTO();
        x.setId(u.getId());
        x.setEmail(u.getEmail());
        x.setFirstname(u.getFirstname());
        x.setLastname(u.getLastname());
        x.setUsername(u.getUsername());
        x.setPhone(u.getPhone());
        x.setRole(u.getRole());
        var list = u.getOrders().stream().map(o -> o.getId()).collect(Collectors.toList());
        x.setOrderIds(list);
        return x;
    }
}
