/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.RequiredArgsConstructor;
import main.dto.WishListCreationRequest;
import main.dto.WishListResponse;
import main.models.Product;
import main.models.WishList;
import main.repo.UserRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class WishListMapper {
    private final UserRepo repo;
    
    public WishList toEntity(WishListCreationRequest x){
        var w = new WishList()
                .setName(x.name())
                .setVisibility(x.visibility());
        if(x.desc()!=null){
            w.setDesc(x.desc());
        }
        repo.findById(x.userId()).ifPresent(w::setUser);
        return w;
    }
    
    public WishListResponse toDTO(WishList w){
        var list = w.getProducts().stream().map(Product::getId).toList();
        return new WishListResponse(
                w.getId(),
                w.getName(),
                w.getDesc(),
                w.getVisibility(),
                w.getUser().getId(),
                w.getUser().getUsername(),
                list
        );
    }
}
