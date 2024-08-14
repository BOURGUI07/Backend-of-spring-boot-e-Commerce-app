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
import main.dto.DiscountDTO;
import main.exception.AlreadyExistsException;
import main.exception.EntityNotFoundException;
import main.exception.OptimisticLockException;
import main.repo.DiscountRepo;
import main.repo.ProductRepo;
import main.util.mapper.DiscountMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
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
public class DiscountService {
    private final DiscountRepo repo;
    private final DiscountMapper mapper;
    private final ProductRepo productRepo;
    private Validator validator;
    
    @Cacheable(value="allDiscounts", key = "'findAll_' + #page + '_' + #size")
    public Page<DiscountDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    @Cacheable(value="discountById", key="#id")
    public DiscountDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(""
                + "Discount with id: "  + id + " isn't found"));
    }
    
    @Transactional
    @CacheEvict(value={
        "allDiscounts", "discountById"
    }, allEntries=true)
    public DiscountDTO create(DiscountDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(repo.existsByNameIgnoreCase(x.name())){
            throw new AlreadyExistsException("discount with this name already exists");
        }
        var d = mapper.toEntity(x);
        var saved = repo.save(d);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    @CacheEvict(value={
        "allDiscounts", "discountById"
    }, allEntries=true)
    public DiscountDTO update(Integer id, DiscountDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var d = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""
                + "Discount with id: "  + id + " isn't found"))
        .setActive(x.active())
        .setDesc(x.desc())
        .setName(x.name())
        .setPercent(x.percent());
        var list = x.productIds();
        if(list!=null){
            d.setProducts(productRepo.findAllById(list));
        }
        try{
            var saved  = repo.save(d);
            return mapper.toDTO(saved);
        }catch(DataIntegrityViolationException e){
            throw new AlreadyExistsException("A discount with this name already exists.");
        }catch(ObjectOptimisticLockingFailureException e){
            throw new OptimisticLockException("This discount has been updated by another user, Please review the changes");
        }
    }
    
    @Transactional
    @CacheEvict(value={
        "allDiscounts", "discountById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var d = repo.findById(id);
        if(d.isPresent()){
            var dd = d.get();
            var list = dd.getProducts();
            list.forEach(dd::removeProduct);
            productRepo.saveAll(list);
            repo.delete(dd);
        }
    }
    
    @CacheEvict(value={
        "allDiscounts", "discountById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
