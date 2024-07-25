/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
import main.exception.EntityNotFoundException;
import main.repo.OrderItemRepo;
import main.repo.OrderRepo;
import main.repo.PaymentDetailRepo;
import main.repo.UserRepo;
import main.util.mapper.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepo repo;
    private final UserRepo userRepo;
    private final OrderItemRepo detailRepo;
    private final PaymentDetailRepo paymentRepo;
    private final OrderMapper mapper;
    
    public Page<OrderResponseDTO> findAll(int page, int size){
        var pageable = PageRequest.of(page, size);
        return repo.findAll(pageable).map(mapper::toDTO);
    }
    
    public OrderResponseDTO findById(Integer id){
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> 
            new EntityNotFoundException("Order with id: " + id + " isn't found" ));
    }
    
    
    @Transactional
    public OrderResponseDTO create(OrderDTO x){
        var o = mapper.toEntity(x);
        var saved = repo.save(o);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public OrderResponseDTO update(Integer id,OrderDTO x){
        var o = repo.findById(id).orElseThrow(() -> 
            new EntityNotFoundException("Order with id: " + id + " isn't found" ));
        var list = x.orderItemIds();
        if(list!=null){
            var itemList = detailRepo.findAllById(list);
            itemList.forEach(o::addOrderItem);
            repo.save(o);
            detailRepo.saveAll(itemList);
        }
        paymentRepo.findById(x.paymentDetailId()).ifPresent(o::setPaymentDetail);
        userRepo.findById(x.userId()).ifPresent(o::setUser);
        var saved  = repo.save(o);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public void delete(Integer id){
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public List<OrderResponseDTO> findOrdersByUser(Integer id){
        return repo.findByUserId(id).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

}
