/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.CategoryDTO;
import main.models.Category;
import main.repo.ProductRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class CategoryMapper {
    private final ProductRepo productRepo;
    
    public Category toEntity(CategoryDTO x){
        var c = new Category()
        .setDesc(x.desc())
        .setName(x.name());
        var list = x.productIds();
        if(list!=null){
            c.setProducts(productRepo.findAllById(list));
        }
        return c;
    }
    
    public CategoryDTO toDTO(Category c){
        var list = c.getProducts().stream().map(p -> p.getId()).collect(Collectors.toList());
        return new CategoryDTO(c.getId(),c.getName(),c.getDesc(),list,c.getVersion());
    }
}
