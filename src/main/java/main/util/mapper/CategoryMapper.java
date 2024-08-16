/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.CategoryRequestDTO;
import main.dto.CategoryResponseDTO;
import main.models.Category;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class CategoryMapper {
    
    public Category toEntity(CategoryRequestDTO x){
        var c = new Category()
        .setName(x.name());
        x.desc().ifPresent(c::setDesc);
        return c;
    }
    
    public CategoryResponseDTO toDTO(Category c){
        var list = c.getProducts().stream().map(p -> p.getId()).collect(Collectors.toList());
        return new CategoryResponseDTO(c.getId(),c.getName(),c.getDesc(),list,c.getVersion());
    }
}
