/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.ProductDTO;
import main.exception.EntityNotFoundException;
import main.repo.CategoryRepo;
import main.repo.DiscountRepo;
import main.repo.InventoryRepo;
import main.repo.OrderItemRepo;
import main.repo.ProductRepo;
import main.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepo repo;
    private final CategoryRepo categoryRepo;
    private final DiscountRepo discountRepo;
    private final InventoryRepo invRepo;
    private final OrderItemRepo orepo;
    private final ProductMapper mapper;
    
    
    public Page<ProductDTO> findAll(int page, int size){
        var pageable = PageRequest.of(page,size);
        return repo.findAll(pageable).map(mapper::toDTO);
    }
    
    public ProductDTO findById(Integer id){
        var opProduct = repo.findById(id);
        return opProduct.map(mapper::toDTO).orElseThrow(
                () -> new EntityNotFoundException("Product with id " + id + " isn't found"));
    }
    
    @Transactional
    public ProductDTO create(ProductDTO x){
        var product = mapper.toEntity(x);
        var savedProduct = repo.save(product);
        return mapper.toDTO(savedProduct);
    }
    
    @Transactional
    public ProductDTO update(Integer id, ProductDTO x){
        var product = repo.findById(id).orElseThrow(() -> 
            new EntityNotFoundException("Product with id " + id + " isn't found"));
        categoryRepo.findById(x.categoryId()).ifPresent(product::setCategory);
        invRepo.findById(x.inventoryId()).ifPresent(product::setInventory);
        discountRepo.findById(x.discountId()).ifPresent(product::setDiscount);
        product.setDesc(x.desc());
        product.setName(x.name());
        product.setSku(x.sku());
        product.setPrice(x.price());
        var list = x.orderItemsIds();
        if(list!=null){
            product.setOrderItems(orepo.findAllById(list));
        }
        var savedProduct = repo.save(product);
        return mapper.toDTO(savedProduct);
    }
    
    @Transactional
    public void delete(Integer id){
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public List<ProductDTO> findProductsWithCategoryId(Integer id){
        return repo.findByCategoryId(id).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    
}
