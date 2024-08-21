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
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.PaymentDetailDTO;
import main.dto.PaymentDetailResponseDTO;
import main.exception.EntityNotFoundException;
import main.exception.OptimisticLockException;
import main.repo.OrderRepo;
import main.repo.PaymentDetailRepo;
import main.util.PaymentStatus;
import main.util.mapper.PaymentDetailMapper;
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
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class PaymentDetailService {
      OrderRepo orepo;
      PaymentDetailRepo repo;
      PaymentDetailMapper mapper;
    @NonFinal Validator validator;
    
    
    
    @Cacheable(value="allPaymentDetails", key = "'findAll_' + #page + '_' + #size",unless="#result.isEmpty()")
    public Page<PaymentDetailResponseDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page,size)).map(mapper::toDTO);
    }
    @Cacheable(value="paymentDetailById", key="#id", condition="#id!=null && #id>0",unless = "#result == null")
    public PaymentDetailResponseDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> 
                new EntityNotFoundException("Payment Detail with id: " + id + " isn't found")
        );
    }
    
    @Transactional
    @CacheEvict(value={
        "allPaymentDetails", "paymentDetailById"
    }, allEntries=true)
    public PaymentDetailResponseDTO create(PaymentDetailDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var p = mapper.toEntity(x);
        var saved = repo.save(p);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    @CacheEvict(value={
        "allPaymentDetails", "paymentDetailById"
    }, allEntries=true)
    public PaymentDetailResponseDTO update(Integer id, PaymentDetailDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var p  =repo.findById(id).orElseThrow(() -> 
                new EntityNotFoundException("Payment Detail with id: " + id + " isn't found")
        );
        orepo.findById(x.orderId()).ifPresent(p::setOrder);
        p.setPaymentProvider(x.provider());
        p.setPaymentStatus(x.status());
        try{
            var saved = repo.save(p);
            return mapper.toDTO(saved);
        }catch(ObjectOptimisticLockingFailureException e){
            throw new OptimisticLockException("This Payment Detail has been updated by another user, Please review the changes");
        }
    }
    
    @Transactional
    @CacheEvict(value={
        "allPaymentDetails", "paymentDetailById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public List<PaymentDetailResponseDTO> findByPaymentStatus(PaymentStatus status){
        return repo.findByPaymentStatus(status).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    public List<PaymentDetailResponseDTO> findPaymentsWithAmountGreaterThan(Double amount){
        return repo.findByAmountGreaterThanEqual(amount).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    @CacheEvict(value={
        "allPaymentDetails", "paymentDetailById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
