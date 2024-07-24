/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.ProductDTO;
import main.models.Product;
import main.repo.CategoryRepo;
import main.repo.DiscountRepo;
import main.repo.InventoryRepo;
import main.repo.OrderItemRepo;
import main.repo.ProductRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class ProductMapper {
    private final ProductRepo repo;
    private final CategoryRepo categoryRepo;
    private final OrderItemRepo detailRepo;
    private final DiscountRepo discountRepo;
    private final InventoryRepo inventoryRepo;
    
    public Product toEntity(ProductDTO x){
        var p = new Product();
        var category = categoryRepo.findById(x.categoryId());
        category.ifPresent(p::setCategory);
        var discount = discountRepo.findById(x.discountId());
        discount.ifPresent(p::setDiscount);
        var inventory = inventoryRepo.findById(x.inventoryId());
        inventory.ifPresent(p::setInventory);
        var list = x.orderItemsIds();
        if(list!=null){
            p.setOrderItems(detailRepo.findAllById(list));
        }
        p.setDesc(x.desc());
        p.setName(x.name());
        p.setSku(x.sku());
        p.setPrice(x.price());
        return p;
    }
    
    public ProductDTO toDTO(Product p){
        var list = p.getOrderItems().stream().map(o -> o.getId()).collect(Collectors.toList());
        return (p.getCategory()!=null && p.getDiscount()!=null && p.getInventory()!=null)?
                new ProductDTO(p.getId(),p.getName(),p.getDesc(),p.getSku(),p.getPrice(),p.getCategory().getId(),
                p.getInventory().getId(),p.getDiscount().getId(),list):null;
    }
}
