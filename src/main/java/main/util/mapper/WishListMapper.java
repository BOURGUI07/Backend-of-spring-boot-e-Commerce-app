/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.WishListCreationRequest;
import main.dto.WishListMergeRequest;
import main.dto.WishListResponse;
import main.exception.EntityNotFoundException;
import main.models.Product;
import main.models.WishList;
import main.repo.UserRepo;
import main.repo.WishListRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class WishListMapper {
      UserRepo repo;
      WishListRepo wrepo;
    
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
    
    public WishList toEntityMerge(WishListMergeRequest x){
        var w = wrepo.findById(x.targetWishId())
                .orElseThrow(() -> new EntityNotFoundException("wish list with id: " + x.targetWishId() + " isn't found"));
        var sourceList = wrepo.findAllById(x.wishListIds());
        if(wrepo.findByUserId(w.getUser().getId()).containsAll(sourceList)){
            sourceList
                .stream()
                .map(WishList::getProducts)
                .forEach(p-> w.getProducts().addAll(p));
        }
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
