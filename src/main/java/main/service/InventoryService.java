/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.InventoryDTO;
import main.dto.InventoryUpdateRequest;
import main.exception.EntityNotFoundException;
import main.exception.OptimisticLockException;
import main.repo.InventoryRepo;
import main.util.mapper.InventoryMapper;
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
public class InventoryService {
      InventoryMapper mapper;
      InventoryRepo repo;
      @NonFinal Validator validator;
      
      
    @Transactional
    @CacheEvict(value={
        "allInventories", "inventoryById","inventoryByProductId"
    }, allEntries=true)
    public InventoryDTO update(InventoryUpdateRequest x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var inventory = repo.findByProductId(x.productId())
                .orElseThrow(() -> new EntityNotFoundException("No inventory was found for product id " + x.productId()))
                ;
        inventory.decreaseQtyBy(x.quantityToBeSubstracted());
        try{
            var saved = repo.save(inventory);
            return mapper.toDTO(saved);
        }catch(ObjectOptimisticLockingFailureException e){
            throw new OptimisticLockException("This discount has been updated by another user, Please review the changes");
        }
    }
    
    
    @Cacheable(value="inventoryById", key="#id")
    public InventoryDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Inventory with id: " + id + " isn't found"));
    }
    
    
    @Cacheable(value="inventoryByProductId", key="#productId")
    public InventoryDTO findByProductId(Integer productId){
        if(productId<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findByProductId(productId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Inventory with productId: " + productId + " isn't found"));
    }
    
    
    @Cacheable(value="allInventories", key = "'findAll_' + #page + '_' + #size")
    public Page<InventoryDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    @CacheEvict(value={
        "allInventories", "inventoryById","inventoryByProductId"
    }, allEntries=true)
    public InventoryDTO create(InventoryDTO x){
        var s = mapper.toEntity(x);
        var saved = repo.save(s);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    @CacheEvict(value={
        "allInventories", "inventoryById","inventoryByProductId"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    @CacheEvict(value={
        "allInventories", "inventoryById","inventoryByProductId"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
