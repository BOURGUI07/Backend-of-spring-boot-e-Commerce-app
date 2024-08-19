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
import main.dto.UserPaymentDTO;
import main.exception.EntityNotFoundException;
import main.repo.UserPaymentRepo;
import main.repo.UserRepo;
import main.util.PaymentProvider;
import main.util.PaymentType;
import main.util.mapper.UserPaymentMapper;
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
public class UserPaymentService {
      UserRepo userRepo;
      UserPaymentRepo repo;
      UserPaymentMapper mapper;
    @NonFinal Validator validator;
    @Cacheable(value="allUserPayments", key = "'findAll_' + #page + '_' + #size")
    public Page<UserPaymentDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    @Cacheable(value="userPaymentById", key="#id")
    public UserPaymentDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(""
                + "User Payment with id: " + id + " isn't found"));
    }
    
    @Transactional
    @CacheEvict(value={
        "allUserPayments", "userPaymentById"
    }, allEntries=true)
    public UserPaymentDTO create(UserPaymentDTO x){
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
        "allUserPayments", "userPaymentById"
    }, allEntries=true)
    public UserPaymentDTO update(Integer id, UserPaymentDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var p = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""
                + "User Payment with id: " + id + " isn't found"))
        
        .setAccountNumber(x.accountNo())
        .setExpiryDate(x.expiryDate())
        .setPaymentProvider(x.provider())
        .setPaymentType(x.type());
        userRepo.findById(x.userId()).ifPresent(p::setUser);
        var saved = repo.save(p);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    @CacheEvict(value={
        "allUserPayments", "userPaymentById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public UserPaymentDTO findPaymentByUserId(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findByUserId(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(""
                + "User with id: " + id + " isn't found"));
    }
    
    public List<UserPaymentDTO> findPaymentByType(PaymentType t){
        return repo.findByPaymentType(t).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    public List<UserPaymentDTO> findPaymentByProvider(PaymentProvider p){
        return repo.findByPaymentProvider(p).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    @CacheEvict(value={
        "allUserPayments", "userPaymentById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
