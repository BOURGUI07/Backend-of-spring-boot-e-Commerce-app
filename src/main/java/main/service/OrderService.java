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
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
import main.event.OrderCreationEvent;
import main.exception.EntityNotFoundException;
import main.exception.OptimisticLockException;
import main.repo.OrderItemRepo;
import main.repo.OrderRepo;
import main.repo.PaymentDetailRepo;
import main.repo.ProductRepo;
import main.repo.UserRepo;
import main.util.mapper.OrderMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
@Data
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class OrderService {
      OrderRepo repo;
      UserRepo userRepo;
      OrderItemRepo detailRepo;
      PaymentDetailRepo paymentRepo;
      OrderMapper mapper;
      ProductRepo productRepo;
      SalesTaxCalculationService taxService;
      ProductAvailability productAvailability;
      ApplicationEventPublisher eventPublisher;
    @NonFinal Validator validator;

    
    @Transactional
    @CacheEvict(value={
        "allOrders", "orderById","findOrdersByUsers"
    }, allEntries=true)
    public OrderResponseDTO create(OrderDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var user= userRepo.findById(x.userId());
        if(user.isPresent()){
            var areProductsAvailable = productAvailability.checkAvailability(x);
            if(areProductsAvailable){
                eventPublisher.publishEvent(new OrderCreationEvent(this, x));
                var o = mapper.toEntity(x);
                var saved = repo.save(o);
                return mapper.toDTO(saved);
            }else{
                throw new RuntimeException("Products aren't available for this order");
            }
        }else{
            throw new EntityNotFoundException("user with id " + x.userId() + " isn't found");
        }  
    }
    
    
    
    @Cacheable(value="allOrders", key = "'findAll_' + #page + '_' + #size",unless="#result.isEmpty()")
    public Page<OrderResponseDTO> findAll(int page, int size){
        var pageable = PageRequest.of(page, size);
        return repo.findAll(pageable).map(mapper::toDTO);
    }
    @Cacheable(value="orderById", key="#id", condition="#id!=null && #id>0",unless = "#result == null")
    public OrderResponseDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> 
            new EntityNotFoundException("Order with id: " + id + " isn't found" ));
    }
    
    @Transactional
    @CacheEvict(value={
        "allOrders", "orderById","findOrdersByUsers"
    }, allEntries=true)
    public OrderResponseDTO update(Integer id,OrderDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var o = repo.findById(id).orElseThrow(() -> 
            new EntityNotFoundException("Order with id: " + id + " isn't found" ));
            taxService.calculateTotalOrderPrice(o);
        userRepo.findById(x.userId()).ifPresent(o::setUser);
        try{
            var saved  = repo.save(o);
            return mapper.toDTO(saved);
        }catch(ObjectOptimisticLockingFailureException e){
            throw new OptimisticLockException("This Order has been updated by another user, Please review the changes");
        }
        
    }
    
    @Transactional
    @CacheEvict(value={
        "allOrders", "orderById","findOrdersByUsers"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    @Cacheable(value="findOrdersByUsers",key="#id",condition="#id!=null && #id>0")
    public List<OrderResponseDTO> findOrdersByUser(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findByUserId(id).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    @CacheEvict(value={
        "allOrders", "orderById","findOrdersByUsers"
    }, allEntries=true)
    public void clearCache(){
        
    }

}
