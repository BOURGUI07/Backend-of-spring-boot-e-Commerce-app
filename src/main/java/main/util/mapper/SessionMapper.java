/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.UserShoppingSessionDTO;
import main.models.UserShoppingSession;
import main.repo.CartItemRepo;
import main.repo.UserRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class SessionMapper {
    private final UserRepo urepo;
    private final CartItemRepo crepo;
    
    public UserShoppingSessionDTO toDTO(UserShoppingSession s){
        var list = s.getCartItems().stream().map(x -> x.getId()).collect(Collectors.toList());
        var user = s.getUser();
        return user!=null ? new UserShoppingSessionDTO(s.getId(),user.getId(),s.getTotal(),list):null;
    }
    
    public UserShoppingSession toEntity(UserShoppingSessionDTO x){
        var s = new UserShoppingSession();
        s.setTotal(x.total());
        urepo.findById(x.userId()).ifPresent(s::setUser);
        var list = x.cartItemIds();
        if(list!=null){
            s.setCartItems(crepo.findAllById(list));
        }
        return s;
    }
}
