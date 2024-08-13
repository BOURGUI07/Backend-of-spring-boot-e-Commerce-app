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
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
import main.exception.EntityNotFoundException;
import main.exception.InsufficientInventoryException;
import main.exception.OptimisticLockException;
import main.models.OrderItem;
import main.repo.OrderItemRepo;
import main.repo.OrderRepo;
import main.repo.PaymentDetailRepo;
import main.repo.ProductRepo;
import main.repo.UserRepo;
import main.util.mapper.OrderMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class OrderService {
    private final OrderRepo repo;
    private final UserRepo userRepo;
    private final OrderItemRepo detailRepo;
    private final PaymentDetailRepo paymentRepo;
    private final OrderMapper mapper;
    private final ProductRepo productRepo;
    private final SalesTaxService taxService;
    private Validator validator;
    
    
    
    
    @Cacheable(value="allOrders", key = "'findAll_' + #page + '_' + #size")
    public Page<OrderResponseDTO> findAll(int page, int size){
        var pageable = PageRequest.of(page, size);
        return repo.findAll(pageable).map(mapper::toDTO);
    }
    @Cacheable(value="orderById", key="#id")
    public OrderResponseDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> 
            new EntityNotFoundException("Order with id: " + id + " isn't found" ));
    }
    
    
    @Transactional
    @CacheEvict(value={
        "allOrders", "orderById"
    }, allEntries=true)
    public OrderResponseDTO create(OrderDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var user = userRepo.findById(x.userId())
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + x.userId() + " isn't found"));
        if(x.orderItemIds()!=null && !x.orderItemIds().isEmpty()){
            var orderItems = detailRepo.findAllById(x.orderItemIds());
            var desiredProductIds = orderItems.stream().map(e -> e.getProduct().getId()).toList();
            var availableProducts = productRepo.findAllById(desiredProductIds);
            if(desiredProductIds.size()==availableProducts.size()){
                var map = orderItems.stream().collect(Collectors.toMap(OrderItem::getProduct, OrderItem::getQuantity));
               
                var isOrderedQuantityAvailable = availableProducts.stream().allMatch(p -> p.getInventory().getQuantity()>=map.get(p));
                if(isOrderedQuantityAvailable){
                    availableProducts.forEach(p -> p.getInventory().setQuantity(p.getInventory().getQuantity()-map.get(p)));
                    productRepo.saveAll(availableProducts);
                    detailRepo.saveAll(orderItems);
                    var o = mapper.toEntity(x);
                    user.addOrder(o);
                    userRepo.save(user);
                    var saved = repo.save(o);
                    return mapper.toDTO(saved);
                }else{
                    throw new InsufficientInventoryException("At Least One Product Ordered Quantity Exceeds Inventory");
                }
            }else{
                throw new EntityNotFoundException("At least One Product Wasn't Found");
            }
        }else{
            throw new IllegalArgumentException("Either the order item list is null or empty");
        }
        
    }
    
    @Transactional
    @CacheEvict(value={
        "allOrders", "orderById"
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
        var list = x.orderItemIds();
        if(list!=null){
            var itemList = detailRepo.findAllById(list);
            itemList.forEach(o::addOrderItem);
            taxService.calculateTotalOrderPrice(o);
            repo.save(o);
            detailRepo.saveAll(itemList);
        }
        paymentRepo.findById(x.paymentDetailId()).ifPresent(o::setPaymentDetail);
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
        "allOrders", "orderById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public List<OrderResponseDTO> findOrdersByUser(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findByUserId(id).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    @CacheEvict(value={
        "allOrders", "orderById"
    }, allEntries=true)
    public void clearCache(){
        
    }

}
