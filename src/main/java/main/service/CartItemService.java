/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
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
public class CartItemService {
    private final CartItemMapper mapper;
    private final CartItemRepo repo;
    private final SessionRepo sessionRepo;
    private final ProductRepo productRepo;
    
    @Transactional
    public CartItemDTO update(Integer id, CartItemDTO x){
        var o = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart Item with id: " + id + " isn't found"));
        o.setQuantity(x.quantity());
        productRepo.findById(x.productId()).ifPresent(o::setProduct);
        sessionRepo.findById(x.sessionId()).ifPresent(o::setSession);
        var saved = repo.save(o);
        return mapper.toDTO(saved);
    }
    
    public CartItemDTO findById(Integer id){
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Cart Item with id: " + id + " isn't found"));
    }
    
    public Page<CartItemDTO> finAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    public CartItemDTO create(CartItemDTO x){
        var s = mapper.toEntity(x);
        var saved = repo.save(s);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public void delete(Integer id){
        repo.findById(id).ifPresent(repo::delete);
    }

}
