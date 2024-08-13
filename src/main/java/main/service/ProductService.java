/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import main.dto.ProductDTO;
import main.exception.AlreadyExistsException;
import main.exception.EntityNotFoundException;
import main.exception.OptimisticLockException;
import main.repo.CategoryRepo;
import main.repo.DiscountRepo;
import main.repo.InventoryRepo;
import main.repo.OrderItemRepo;
import main.repo.ProductRepo;
import main.util.mapper.ProductMapper;
import main.util.specification.ProductSpecification;
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
public class ProductService {
    private final ProductRepo repo;
    private final CategoryRepo categoryRepo;
    private final DiscountRepo discountRepo;
    private final InventoryRepo invRepo;
    private final OrderItemRepo orepo;
    private final ProductMapper mapper;
    private final ProductSpecification specification;
    private Validator validator;
    
    @Cacheable(value="allProducts", key = "'findAll_' + #page + '_' + #size")
    public Page<ProductDTO> findAll(int page, int size){
        var pageable = PageRequest.of(page,size);
        return repo.findAll(pageable).map(mapper::toDTO);
    }
    
    @Cacheable(value="productById", key="#id")
    public ProductDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var opProduct = repo.findById(id);
        return opProduct.map(mapper::toDTO).orElseThrow(
                () -> new EntityNotFoundException("Product with id " + id + " isn't found"));
    }
    
    @Transactional
    @CacheEvict(value={
        "allProducts", "productById"
    }, allEntries=true)
    public ProductDTO create(ProductDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(repo.existsByName(x.name()) || repo.existsBySku(x.sku())){
            throw new AlreadyExistsException("product with either input name or input sku already exists");
        }
        var product = mapper.toEntity(x);
        
            var saved = repo.save(product);
            return mapper.toDTO(saved);
        
    }
    
    @Transactional
    @CacheEvict(value={
        "allProducts", "productById"
    }, allEntries=true)
    public ProductDTO update(Integer id, ProductDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var product = repo.findById(id).orElseThrow(() -> 
            new EntityNotFoundException("Product with id " + id + " isn't found"))
                .setDesc(x.desc())
                .setName(x.name())
                .setSku(x.sku())
                .setPrice(x.price());
        if(x.categoryId()!=null){
            categoryRepo.findById(x.categoryId()).ifPresent(product::setCategory);
        }
        if(x.discountId()!=null){
            discountRepo.findById(x.discountId()).ifPresent(product::setDiscount);
        }
        invRepo.findById(x.inventoryId()).ifPresent(product::setInventory);
        var list = x.orderItemsIds();
        if(list!=null){
            product.setOrderItems(orepo.findAllById(list));
        }
        try{
            var saved = repo.save(product);
            return mapper.toDTO(saved);
        }catch(DataIntegrityViolationException e){
            throw new AlreadyExistsException("A product with this name already exists.");
        }catch(ObjectOptimisticLockingFailureException e){
            throw new OptimisticLockException("This product has been updated by another user. Please review the changes.");
        }
    }
    
    @Transactional
    @CacheEvict(value={
        "allProducts", "productById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public List<ProductDTO> findProductsWithCategoryId(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        return repo.findByCategoryId(id).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    public Page<ProductDTO> search(String name, String desc, Boolean discountStatus, String categoryName, Double minPrice, Double maxPrice, int page, int size){
        var pageable = PageRequest.of(page, size);
        var spec = Specification.where(specification.hasName(name)
                .and(specification.hasDesc(desc))
                .and(specification.hasCategoryName(categoryName))
                .and(specification.hasDiscountStatus(discountStatus))
                .and(specification.hasPriceBetween(minPrice, maxPrice))
        );
        
        var products = repo.findAll(spec, pageable);
        var productDTOs = products.stream().map(mapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(productDTOs,pageable, products.getTotalElements());
    }
    @CacheEvict(value={
        "allProducts", "productById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
