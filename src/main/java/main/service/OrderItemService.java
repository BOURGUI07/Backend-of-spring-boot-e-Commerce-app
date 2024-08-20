/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.OrderItemCreationRequest;
import main.dto.OrderItemResponse;
import main.exception.EntityNotFoundException;
import main.repo.OrderItemRepo;
import main.repo.OrderRepo;
import main.repo.ProductRepo;
import main.util.mapper.OrderItemMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class OrderItemService {
      OrderItemMapper mapper;
      OrderItemRepo repo;
      ProductRepo prepo;
      OrderRepo orepo;
    @NonFinal Validator validator;
    
    @Transactional
    @CacheEvict(value={
        "allOrderItems", "orderItemById"
    }, allEntries=true)
    public OrderItemResponse update(Integer id, OrderItemCreationRequest x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
            var o = repo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Order Item with id: " + id + " isn't found"))
            .setQuantity(x.quantity());
            prepo.findById(x.productid()).ifPresent(o::setProduct);
            orepo.findById(x.orderId()).ifPresent(o::setOrder);
            var saved = repo.save(o);
            return mapper.toDTO(saved);
        
    }
    @Cacheable(value="orderItemById", key="#id")
    public OrderItemResponse findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Order Item with id: " + id + " isn't found"));
    }
    @Cacheable(value="allOrderItems", key = "'findAll_' + #page + '_' + #size")
    public Page<OrderItemResponse> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    @CacheEvict(value={
        "allOrderItems", "orderItemById"
    }, allEntries=true)
    public OrderItemResponse create(OrderItemCreationRequest x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
            var s = mapper.toEntity(x);
            var saved = repo.save(s);
            return mapper.toDTO(saved);
        
    }
    
    @Transactional
    @CacheEvict(value={
        "allOrderItems", "orderItemById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public List<OrderItemResponse> findOrderDetailsforProduct(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findByProductId(id).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    @CacheEvict(value={
        "allOrderItems", "orderItemById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
