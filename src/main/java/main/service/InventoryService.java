/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.dto.InventoryDTO;
import main.exception.EntityNotFoundException;
import main.repo.InventoryRepo;
import main.util.mapper.InventoryMapper;
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
public class InventoryService {
    private final InventoryMapper mapper;
    private final InventoryRepo repo;
    
    @Transactional
    @CacheEvict(value={
        "allInventories", "inventoryById"
    }, allEntries=true)
    public InventoryDTO update(Integer id, InventoryDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var o = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory with id: " + id + " isn't found"))
        .setQuantity(x.quantity());
        var saved = repo.save(o);
        return mapper.toDTO(saved);
    }
    @Cacheable(value="inventoryById", key="#id")
    public InventoryDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Inventory with id: " + id + " isn't found"));
    }
    @Cacheable(value="allInventories", key = "'findAll_' + #page + '_' + #size")
    public Page<InventoryDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    @Transactional
    @CacheEvict(value={
        "allInventories", "inventoryById"
    }, allEntries=true)
    public InventoryDTO create(InventoryDTO x){
        var s = mapper.toEntity(x);
        var saved = repo.save(s);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    @CacheEvict(value={
        "allInventories", "inventoryById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    @CacheEvict(value={
        "allInventories", "inventoryById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
