/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import main.dto.SalesTaxRequest;
import main.dto.SalesTaxResponse;
import main.exception.AlreadyExistsException;
import main.exception.EntityNotFoundException;
import main.exception.OptimisticLockException;
import main.models.Order;
import main.repo.AddressRepo;
import main.repo.TaxRepo;
import main.util.mapper.SalesTaxMapper;
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
    private final TaxRepo repo;
    private final AddressRepo addressRepo;
    private Validator validator;
    private final SalesTaxMapper mapper;
    
    public void calculateTotalOrderPrice(Order o){
        /*
            to accuratley calculate the total order price
            we have to first check if the user retrieved by the request userId is existent
            we have to find the country of the order
            and since for every country has its own taxtRate, we gonna extract the taxrate based on the country
            then we have to change the taxRate from like 56.2% to like 0.562
            then we gonna multiply the result by the order total non-taxedPrice
            after that we gonna add the result to the order non-taxed total
            the result is the taxed total order price
        */
        if(o.getUser()!=null){
            o.setTotal(o.getTotal() + o.getTotal()*changeTax(repo.getTaxRateForCountry(findOrderCountry(o))));
        }
    }
    
    private Double changeTax(Double taxtRate){
        return taxtRate/100;
    }
    
    private String findOrderCountry(Order o){
        return (o.getUser()!=null) ? addressRepo.findByUserId(o.getUser().getId()).get().getCountry():null;
    }
    
    
    
    @Transactional
    @CacheEvict(value={
        "allSalesTax", "salesTaxById"
    }, allEntries=true)
    public SalesTaxResponse create(SalesTaxRequest x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(repo.existsByCountry(x.country())){
            throw new AlreadyExistsException("");
        }
        var salesTax = mapper.toEntity(x);
        var saved = repo.save(salesTax);
        return mapper.toDTO(saved);
    }
    
    
    
    
    @Transactional
    @CacheEvict(value={
        "allSalesTax", "salesTaxById"
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
    
    
    
    @Cacheable(value="allSalesTax", key = "'findAll_' + #page + '_' + #size")
    public Page<SalesTaxResponse> findAll(int page, int size){
        var pageable = PageRequest.of(page,size);
        return repo.findAll(pageable).map(mapper::toDTO);
    }
    
    
    
    @Transactional
    @CacheEvict(value={
        "allSalesTax", "salesTaxById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    
    
    @CacheEvict(value={
        "allSalesTax", "salesTaxById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
