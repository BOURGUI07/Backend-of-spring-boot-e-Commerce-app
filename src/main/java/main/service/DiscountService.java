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
import main.exception.EntityNotFoundException;
import main.repo.DiscountRepo;
import main.repo.ProductRepo;
import main.util.mapper.DiscountMapper;
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
public class DiscountService {
    private final DiscountRepo repo;
    private final DiscountMapper mapper;
    private final ProductRepo productRepo;
    private Validator validator;
    
    public Page<DiscountDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page, size)).map(mapper::toDTO);
    }
    
    public DiscountDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(""
                + "Discount with id: "  + id + " isn't found"));
    }
    
    @Transactional
    public DiscountDTO create(DiscountDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var d = mapper.toEntity(x);
        var saved = repo.save(d);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public DiscountDTO update(Integer id, DiscountDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var d = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(""
                + "Discount with id: "  + id + " isn't found"));
        d.setActive(x.active());
        d.setDesc(x.desc());
        d.setName(x.name());
        d.setPercent(x.percent());
        var list = x.productIds();
        if(list!=null){
            d.setProducts(productRepo.findAllById(list));
        }
        var saved = repo.save(d);
        return mapper.toDTO(saved);
    }
    
    @Transactional
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
    
    
}