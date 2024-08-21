/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.UserAddressDTO;
import main.exception.EntityNotFoundException;
import main.repo.AddressRepo;
import main.repo.UserRepo;
import main.util.mapper.AddressMapper;
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
public class UserAddressService {
      AddressMapper mapper;
      AddressRepo repo;
      UserRepo urepo;
    @NonFinal Validator validator;
    @Cacheable(value="userAddressById", key="#id", condition="#id!=null && #id>0",unless = "#result == null")
    public UserAddressDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Address with id: " + id + " isn't found"));
    }
    @Cacheable(value="allUserAddresses", key = "'findAll_' + #page + '_' + #size",unless="#result.isEmpty()")
    public Page<UserAddressDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    @CacheEvict(value={
        "allUserAddresses", "userAddressById"
    }, allEntries=true)
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
    @CacheEvict(value={
        "allUserAddresses", "userAddressById"
    }, allEntries=true)
    public UserAddressDTO update(Integer id, UserAddressDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var a = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address with id: " + id + " isn't found"))
        .setAddressLine1(x.addressLine1())
        .setCity(x.city())
        .setCountry(x.country())
        .setPostalCode(x.postalcode());
        x.addressLine2().ifPresent(a::setAddressLine2);
        urepo.findById(x.userId()).ifPresent(a::setUser);
        var saved  = repo.save(a);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    @CacheEvict(value={
        "allUserAddresses", "userAddressById"
    }, allEntries=true)
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
    
    @CacheEvict(value={
        "allUserAddresses", "userAddressById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
