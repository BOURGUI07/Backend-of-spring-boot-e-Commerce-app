/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.CategoryDTO;
import main.exception.EntityNotFoundException;
import main.repo.CategoryRepo;
import main.repo.ProductRepo;
import main.util.mapper.CategoryMapper;
import main.util.specification.CategorySpecification;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepo repo;
    private final ProductRepo prepo;
    private final CategoryMapper mapper;
    private final CategorySpecification specification;
    
    @Cacheable(value="allCategories", key = "'findAll_' + #page + '_' + #size")
    public Page<CategoryDTO> findAll(int page, int size){
        var pageable = PageRequest.of(page,size);
        return repo.findAll(pageable).map(mapper::toDTO);
    }
    
    @Cacheable(value="categoryById", key="#id")
    public CategoryDTO findById(Integer id){
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() ->
                new EntityNotFoundException("Category with id " + id + " isn't found")
        );
    }
    
    @Transactional
    @CacheEvict(value={
        "allCategories", "categoryById"
    }, allEntries=true)
    public CategoryDTO create(CategoryDTO x){
        var c = mapper.toEntity(x);
        var saved = repo.save(c);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    @CacheEvict(value={
        "allCategories", "categoryById"
    }, allEntries=true)
    public CategoryDTO update(Integer id, CategoryDTO x){
        var c = repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category with id " + id + " isn't found"));
        c.setDesc(x.desc());
        c.setName(x.name());
        var list = x.productIds();
        if(list!=null){
            c.setProducts(prepo.findAllById(list));
        }
        var saved  = repo.save(c);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    @CacheEvict(value={
        "allCategories", "categoryById"
    }, allEntries=true)
    public void delete(Integer id){
        var opC = repo.findById(id);
        if(opC.isPresent()){
            var c = opC.get();
            var list = c.getProducts();
            list.forEach(c::removeProduct);
            prepo.saveAll(list);
            repo.delete(c);
        }
    }
    
    public Page<CategoryDTO> search(String name, String desc, String productName, int page, int size){
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
