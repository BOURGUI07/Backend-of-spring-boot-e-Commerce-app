/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import main.dto.UserAddressDTO;
import main.exception.EntityNotFoundException;
import main.repo.AddressRepo;
import main.repo.UserRepo;
import main.util.mapper.AddressMapper;
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
public class UserAddressService {
    private final AddressMapper mapper;
    private final AddressRepo repo;
    private final UserRepo urepo;
    private Validator validator;
    
    public UserAddressDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Address with id: " + id + " isn't found"));
    }
    
    public Page<UserAddressDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    public UserAddressDTO create(UserAddressDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var a = mapper.toEntity(x);
        var saved = repo.save(a);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public UserAddressDTO update(Integer id, UserAddressDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var a = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address with id: " + id + " isn't found"));
        a.setAddressLine1(x.addressLine1());
        a.setAddressLine2(x.addressLine2());
        a.setCity(x.city());
        a.setCountry(x.country());
        a.setPostalCode(x.postalcode());
        urepo.findById(x.userId()).ifPresent(a::setUser);
        var saved  = repo.save(a);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public UserAddressDTO findAddressByUserId(Integer id){
        return repo.findByUserId(id).map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " isn't found"));
    }
}
