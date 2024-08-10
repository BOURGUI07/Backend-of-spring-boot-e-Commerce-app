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
import lombok.Data;
import lombok.RequiredArgsConstructor;
import main.dto.OrderItemDTO;
import main.exception.EntityNotFoundException;
import main.exception.InsufficientInventoryException;
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
public class OrderItemService {
    private final OrderItemMapper mapper;
    private final OrderItemRepo repo;
    private final ProductRepo prepo;
    private final OrderRepo orepo;
    private Validator validator;
    
    @Transactional
    @CacheEvict(value={
        "allOrderItems", "orderItemById"
    }, allEntries=true)
    public OrderItemDTO update(Integer id, OrderItemDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var product = prepo.findById(x.productid())
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + x.productid() + "isn't found"));
        if(x.quantity()>product.getInventory().getQuantity()){
            throw new InsufficientInventoryException("Quantity Ordered Exceeds Product Inventory");
        }else{
            var o = repo.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Order Item with id: " + id + " isn't found"))
            .setQuantity(x.quantity());
            prepo.findById(x.productid()).ifPresent(o::setProduct);
            orepo.findById(x.orderId()).ifPresent(o::setOrder);
            var saved = repo.save(o);
            return mapper.toDTO(saved);
        }
        
    }
    @Cacheable(value="orderItemById", key="#id")
    public OrderItemDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Order Item with id: " + id + " isn't found"));
    }
    @Cacheable(value="allOrderItems", key = "'findAll_' + #page + '_' + #size")
    public Page<OrderItemDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    @CacheEvict(value={
        "allOrderItems", "orderItemById"
    }, allEntries=true)
    public OrderItemDTO create(OrderItemDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var product = prepo.findById(x.productid())
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + x.productid() + "isn't found"));
        if(x.quantity()>product.getInventory().getQuantity()){
            throw new InsufficientInventoryException("Quantity Ordered Exceeds Product Inventory");
        }else{
            var s = mapper.toEntity(x);
            var saved = repo.save(s);
            return mapper.toDTO(saved);
        }
        
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
    
    public List<OrderItemDTO> findOrderDetailsforProduct(Integer id){
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
