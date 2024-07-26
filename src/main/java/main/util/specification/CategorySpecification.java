/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.specification;

import main.models.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class CategorySpecification {
    public Specification<Category> hasName(String name){
        return (root, query, criteriaBuilder) -> {
            if(name==null || name.isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("name"), "%" + name+ "%");
        };
    }
    
    public Specification<Category> hasDesc(String desc){
        return (root, query, criteriaBuilder) -> {
            if(desc==null || desc.isBlank()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("desc"), "%" + desc+ "%");
        };
    }
    
    public Specification<Category> hasProductsWithName(String productName){
        return (root, query, criteriaBuilder) -> {
          if(productName==null || productName.isBlank()){
              return criteriaBuilder.conjunction();
          }  
          var productJoin = root.join("products");
          return criteriaBuilder.equal(productJoin.get("name"), productName);
        };
    }
    
    
}
