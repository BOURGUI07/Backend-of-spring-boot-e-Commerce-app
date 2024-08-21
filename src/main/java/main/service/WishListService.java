/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.WishListCreationRequest;
import main.dto.WishListMergeRequest;
import main.dto.WishListResponse;
import main.dto.WishListUpdateRequest;
import main.exception.AlreadyExistsException;
import main.exception.EntityNotFoundException;
import main.repo.ProductRepo;
import main.repo.WishListRepo;
import main.util.mapper.WishListMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class WishListService {
      ProductRepo prepo;
      WishListRepo repo;
      WishListMapper mapper;
    @NonFinal Validator validator;
    
    @Transactional
    public WishListResponse create(WishListCreationRequest x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(repo.existsByNameIgnoreCase(x.name())){
            throw new AlreadyExistsException("wishlist with this name already exists");
        }
        var w = mapper.toEntity(x);
        var saved = repo.save(w);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public WishListResponse merge(WishListMergeRequest x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var mergedWishList = mapper.toEntityMerge(x);
        var savedMergedWishList = repo.save(mergedWishList);
        return mapper.toDTO(savedMergedWishList);
    }
    
    
    @Transactional
    public WishListResponse update(Integer id, WishListUpdateRequest x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(id<1){
            throw new IllegalArgumentException("id must be positive");
        }
        var w = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""
                        + "wish list with id: " + id + " isn't found"))
                .setName(x.name())
                .setVisibility(x.visibility());
                x.desc().ifPresent(w::setDesc);
        
        try{
            var saved = repo.save(w);
            return mapper.toDTO(saved);
        }catch(DataIntegrityViolationException e){
            throw new AlreadyExistsException("A product with this name already exists.");
        } 
    }
    
    @Transactional
    public void delete(Integer id){
        if(id<1){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    @Transactional
    public WishListResponse addProductToWishList(Integer productId, Integer wishListId){
        if(productId<1 || wishListId<1){
            throw new IllegalArgumentException("id must be positive");
        }
        var w = repo.findById(wishListId)
                .orElseThrow(() -> new EntityNotFoundException(""
                        + "wish list with id: " + wishListId + " isn't found"));
        prepo.findById(productId).ifPresent(w::addProduct);
        prepo.findById(productId).ifPresent(prepo::save);
        var savedWishList = repo.save(w);
        return mapper.toDTO(savedWishList);
    }
    
    @Transactional
    public WishListResponse removeProductFromWishList(Integer productId, Integer wishListId){
        if(productId<1 || wishListId<1){
            throw new IllegalArgumentException("id must be positive");
        }
        var w = repo.findById(wishListId)
                .orElseThrow(() -> new EntityNotFoundException(""
                        + "wish list with id: " + wishListId + " isn't found"));
        prepo.findById(productId).ifPresent(w::removeProduct);
        prepo.findById(productId).ifPresent(prepo::save);
        var savedWishList = repo.save(w);
        return mapper.toDTO(savedWishList);
    }
}
