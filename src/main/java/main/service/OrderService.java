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
import main.exception.OptimisticLockException;
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
    private final SalesTaxCalculationService taxService;
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
        //check first if the user retrieved by the userId does exist
        //Now we don't have to check if it does exists, that would be handled by @EntityIdExists
        //check if the orderItem list is neither null nor empty
        //We don't have to that either, it would be handled by @NotEmpty

            //retrieve the orderItem enetities by their respective Ids
            // retrieve the Ids from the order item entities
            //retrieve the products from the database by the product Ids retrieved from the entered orderItem list
            //check if they have the same size, if so we proceed into other procedures, otherwise, at least one product wasn't found
            //we don't have to handle that either, because if id dones't exists it will fail the validation wall
                //Next we gonna check the requested quantity from each product whether it's less than or equal the available inventory quantity
                // for that, we have to know the requested quantity for each product by using a hashmap
               /*Now we have to check that fro every product element in avialable product list,
                has an inventory quantity greater than or equal the request quantity for the same product
                if so, we can proceed, otherwise, at least one requested product quantity exceeds available quantity
                Now we don't have to that either, because that would be handled by @ValidQuantity
               */
                    /*
                        if the requested quantity is raisonalbe, we have then to set the product inventory quantity
                    */
                    /*
                        next, we have to save the product and orderItem
                        add the order to the user order list
                        save both the user and the order
                    */
                    var o = mapper.toEntity(x);
                    var saved = repo.save(o);
                    return mapper.toDTO(saved);
                
            
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
