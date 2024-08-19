/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.sales_tax_service.service;

import com.example.sales_tax_service.dto.SalesTaxRequest;
import com.example.sales_tax_service.dto.SalesTaxResponse;
import com.example.sales_tax_service.exception.AlreadyExistsException;
import com.example.sales_tax_service.exception.EntityNotFoundException;
import com.example.sales_tax_service.exception.OptimisticLockException;
import com.example.sales_tax_service.mapper.SalesTaxMapper;
import com.example.sales_tax_service.repo.SalesTaxRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

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
@Service
@RequiredArgsConstructor
public class SalesTaxService {
    private final SalesTaxRepo repo;
    private Validator validator;
    private final SalesTaxMapper mapper;
    
    @Transactional
    @CacheEvict(value={
        "allSalesTax", "salesTaxById","salesTaxByCountry"
    }, allEntries=true)
    public SalesTaxResponse create(SalesTaxRequest x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(repo.existsByCountryIgnoreCase(x.country())){
            throw new AlreadyExistsException("A salesTax with this country name already exists.");
        }
        var salesTax = mapper.toEntity(x);
        var saved = repo.save(salesTax);
        return mapper.toDTO(saved);
    }
    
    
    
    
    @Transactional
    @CacheEvict(value={
        "allSalesTax", "salesTaxById","salesTaxByCountry"
    }, allEntries=true)
    public SalesTaxResponse update(Integer id, SalesTaxRequest x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var salesTax = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SalesTax with id: " + id + " isn't found"));
        try{
            var saved = salesTax.setCountry(x.country()).setTaxRate(x.taxRate());
            return mapper.toDTO(saved);
        }catch(DataIntegrityViolationException e){
            throw new AlreadyExistsException("A salesTax with this country name already exists.");
        }catch(ObjectOptimisticLockingFailureException e){
            throw new OptimisticLockException("This salesTax has been updated by another user. Please review the changes.");
        }
    }
    
    
    @Cacheable(value="salesTaxById", key="#id")
    public SalesTaxResponse findById(Integer id){
        var salesTax = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SalesTax with id: " + id + " isn't found"));
        return mapper.toDTO(salesTax);
    }
    
    @Cacheable(value="salesTaxByCountry", key="#country")
    public Double getTaxRateForCountry(String country){
        if(!repo.existsByCountryIgnoreCase(country)){
            throw new EntityNotFoundException("SalesTax with country: " + country + " isn't found");
        }
        if(country.isBlank()){
            throw new IllegalArgumentException("SalesTax country shouldn't be blank!");
        }
        return repo.getTaxRateForCountry(country);
    }
    
    
    
    @Cacheable(value="allSalesTax", key = "'findAll_' + #page + '_' + #size")
    public Page<SalesTaxResponse> findAll(int page, int size){
        var pageable = PageRequest.of(page,size);
        return repo.findAll(pageable).map(mapper::toDTO);
    }
    
    
    
    @Transactional
    @CacheEvict(value={
        "allSalesTax", "salesTaxById","salesTaxByCountry"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    
    
    @CacheEvict(value={
        "allSalesTax", "salesTaxById","salesTaxByCountry"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
