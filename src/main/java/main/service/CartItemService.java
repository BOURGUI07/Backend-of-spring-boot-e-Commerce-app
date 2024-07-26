/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import main.dto.CartItemDTO;
import main.exception.EntityNotFoundException;
import main.repo.CartItemRepo;
import main.repo.ProductRepo;
import main.repo.SessionRepo;
import main.util.mapper.CartItemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
@Data
public class CartItemService {
    private final CartItemMapper mapper;
    private final CartItemRepo repo;
    private final SessionRepo sessionRepo;
    private final ProductRepo productRepo;
    private Validator validator;
    
    @Transactional
    public CartItemDTO update(Integer id, CartItemDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var o = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart Item with id: " + id + " isn't found"));
        o.setQuantity(x.quantity());
        productRepo.findById(x.productId()).ifPresent(o::setProduct);
        sessionRepo.findById(x.sessionId()).ifPresent(o::setSession);
        var saved = repo.save(o);
        return mapper.toDTO(saved);
    }
    
    public CartItemDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Cart Item with id: " + id + " isn't found"));
    }
    
    public Page<CartItemDTO> finAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    public CartItemDTO create(CartItemDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        
        var s = mapper.toEntity(x);
        var saved = repo.save(s);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }

}
