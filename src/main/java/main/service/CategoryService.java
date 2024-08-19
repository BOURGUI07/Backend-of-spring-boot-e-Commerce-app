/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import main.dto.AddProductsToCategoryRequest;
import main.dto.CategoryRequestDTO;
import main.dto.CategoryResponseDTO;
import main.exception.AlreadyExistsException;
import main.exception.EntityNotFoundException;
import main.exception.OptimisticLockException;
import main.repo.CategoryRepo;
import main.repo.ProductRepo;
import main.util.mapper.CategoryMapper;
import main.util.specification.CategorySpecification;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
@Data
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class CategoryService {
      CategoryRepo repo;
      ProductRepo prepo;
      CategoryMapper mapper;
      CategorySpecification specification;
    @NonFinal Validator validator;
    
    @Cacheable(value="allCategories", key = "'findAll_' + #page + '_' + #size")
    public Page<CategoryResponseDTO> findAll(int page, int size){
        var pageable = PageRequest.of(page,size);
        return repo.findAll(pageable).map(mapper::toDTO);
    }
    
    @Cacheable(value="categoryById", key="#id")
    public CategoryResponseDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() ->
                new EntityNotFoundException("Category with id " + id + " isn't found")
        );
    }
    
    @Transactional
    @CacheEvict(value={
        "allCategories", "categoryById"
    }, allEntries=true)
    public CategoryResponseDTO create(CategoryRequestDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(repo.existsByNameIgnoreCase(x.name())){
            throw new AlreadyExistsException("A category with this name already exists.");
        }
        var c = mapper.toEntity(x);
        var saved = repo.save(c);
        return mapper.toDTO(saved);
        
    }
    
    @Transactional
    @CacheEvict(value={
        "allCategories", "categoryById"
    }, allEntries=true)
    public CategoryResponseDTO update(Integer id, CategoryRequestDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var c = repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category with id " + id + " isn't found"))
        .setName(x.name());
        x.desc().ifPresent(c::setDesc);
        try{
            var saved = repo.save(c);
            return mapper.toDTO(saved);
        }catch(DataIntegrityViolationException e){
            throw new AlreadyExistsException("A category with this name already exists.");
        }catch(ObjectOptimisticLockingFailureException e){
            throw new OptimisticLockException("This category has been updated by another user, Please review the changes");
        }
    }
    
    
    @Transactional
    @CacheEvict(value={
        "allCategories", "categoryById"
    }, allEntries=true)
    public CategoryResponseDTO addProducts(AddProductsToCategoryRequest x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var products = prepo.findAllById(x.productIds());
        try{
            var category = repo.findById(x.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException(""
                + "Category with id: "  + x.categoryId()+ " isn't found"));
            products.forEach(category::addProduct);
            prepo.saveAll(products);
            return mapper.toDTO(repo.save(category));
        }catch(ObjectOptimisticLockingFailureException e){
            throw new OptimisticLockException("This category has been updated by another user, Please review the changes");
        }
    }
    
    @Transactional
    @CacheEvict(value={
        "allCategories", "categoryById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var opC = repo.findById(id);
        if(opC.isPresent()){
            var c = opC.get();
            var list = c.getProducts();
            list.forEach(c::removeProduct);
            prepo.saveAll(list);
            repo.delete(c);
        }
    }
    
    public Page<CategoryResponseDTO> search(String name, String desc, String productName, int page, int size){
        var pageable = PageRequest.of(page, size);
        var spec = Specification.where(specification.hasName(name))
                .and(specification.hasDesc(desc))
                .and(specification.hasProductsWithName(productName));
        var categories = repo.findAll(spec, pageable);
        var dtos = categories.stream().map(mapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, categories.getTotalElements());
    }
    
    @CacheEvict(value={
        "allCategories", "categoryById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
