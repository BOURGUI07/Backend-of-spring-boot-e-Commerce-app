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
import main.repo.OrderItemRepo;
import main.repo.OrderRepo;
import main.repo.ProductRepo;
import main.util.mapper.OrderItemMapper;
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
    public OrderItemDTO update(Integer id, OrderItemDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var o = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Order Item with id: " + id + " isn't found"));
        o.setQuantity(x.quantity());
        prepo.findById(x.productid()).ifPresent(o::setProduct);
        orepo.findById(x.orderId()).ifPresent(o::setOrder);
        var saved = repo.save(o);
        return mapper.toDTO(saved);
    }
    
    public OrderItemDTO findById(Integer id){
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Order Item with id: " + id + " isn't found"));
    }
    
    public Page<OrderItemDTO> finAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    public OrderItemDTO create(OrderItemDTO x){
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
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public List<OrderItemDTO> findOrderDetailsforProduct(Integer id){
        return repo.findByProductId(id).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
