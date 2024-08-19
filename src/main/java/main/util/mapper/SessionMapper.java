/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.SessionResponseDTO;
import main.dto.UserShoppingSessionDTO;
import main.models.UserShoppingSession;
import main.repo.CartItemRepo;
import main.repo.UserRepo;
import org.springframework.stereotype.Service;
import main.repo.UserShoppingSessionRepo;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class SessionMapper {
      UserShoppingSessionRepo repo;
      UserRepo urepo;
      CartItemRepo crepo;
    
    public SessionResponseDTO toDTO(UserShoppingSession s){
        var list = s.getCartItems().stream().map(x -> x.getId()).collect(Collectors.toList());
        var user = s.getUser();
        return user!=null ? new SessionResponseDTO(s.getId(),user.getId(),s.getTotal(),list):null;
    }
    
    public UserShoppingSession toEntity(UserShoppingSessionDTO x){
        var s = new UserShoppingSession();
        urepo.findById(x.userId()).ifPresent(s::setUser);
        var list = x.cartItemIds();
        if(list!=null){
            var cartlist = crepo.findAllById(list);
            cartlist.forEach(s::addCartItem);
            crepo.saveAll(cartlist);
            repo.save(s);
        }
        return s;
    }
}
