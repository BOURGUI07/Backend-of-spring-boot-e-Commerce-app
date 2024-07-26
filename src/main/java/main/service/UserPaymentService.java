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
import main.dto.UserPaymentDTO;
import main.exception.EntityNotFoundException;
import main.repo.UserPaymentRepo;
import main.repo.UserRepo;
import main.util.PaymentProvider;
import main.util.PaymentType;
import main.util.mapper.UserPaymentMapper;
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
public class UserPaymentService {
    private final UserRepo userRepo;
    private final UserPaymentRepo repo;
    private final UserPaymentMapper mapper;
    private Validator validator;
    
    public Page<UserPaymentDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    public UserPaymentDTO findById(Integer id){
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(""
                + "User Payment with id: " + id + " isn't found"));
    }
    
    @Transactional
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
    public UserPaymentDTO update(Integer id, UserPaymentDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var p = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""
                + "User Payment with id: " + id + " isn't found"));
        
        p.setAccountNumber(x.accountNo());
        p.setExpiryDate(x.expiryDate());
        p.setPaymentProvider(x.provider());
        p.setPaymentType(x.type());
        userRepo.findById(x.userId()).ifPresent(p::setUser);
        var saved = repo.save(p);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public void delete(Integer id){
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public UserPaymentDTO findPaymentByUserId(Integer id){
        return repo.findByUserId(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(""
                + "User with id: " + id + " isn't found"));
    }
    
    public List<UserPaymentDTO> findPaymentByType(PaymentType t){
        return repo.findByPaymentType(t).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    public List<UserPaymentDTO> findPaymentByProvider(PaymentProvider p){
        return repo.findByPaymentProvider(p).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
