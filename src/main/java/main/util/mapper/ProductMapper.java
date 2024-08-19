/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.ProductRequestDTO;
import main.dto.ProductResponseDTO;
import main.models.Category;
import main.models.Discount;
import main.models.Product;
import main.repo.CategoryRepo;
import main.repo.DiscountRepo;
import main.repo.InventoryRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class ProductMapper {
      CategoryRepo categoryRepo;
      DiscountRepo discountRepo;
      InventoryRepo inventoryRepo;
    
    public Product toEntity(ProductRequestDTO x){
        var p = new Product()
        .setName(x.name())
        .setSku(x.sku())
        .setPrice(x.price());
        x.desc().ifPresent(p::setDesc);
        x.categoryId().ifPresent(id -> {
            categoryRepo.findById(id).ifPresent(p::setCategory);
        });
        x.discountId().ifPresent(id -> {
            discountRepo.findById(id).ifPresent(p::setDiscount);
        });
        inventoryRepo.findById(x.inventoryId()).ifPresent(p::setInventory);
        return p;
    }
    
    public ProductResponseDTO toDTO(Product p){
        var list = p.getOrderItems().stream().map(o -> o.getId()).collect(Collectors.toList());
        var categoryOp = Optional.ofNullable(p.getCategory());
        var discountOp = Optional.ofNullable(p.getDiscount());
        return new ProductResponseDTO(p.getId(),
                p.getName(),
                p.getDesc(),
                p.getSku(),
                p.getPrice(),
                categoryOp.map(Category::getName),
                p.getInventory().getQuantity(),
                discountOp.map(Discount::getName),
                list,
                p.getVersion()
                );
    }
}
