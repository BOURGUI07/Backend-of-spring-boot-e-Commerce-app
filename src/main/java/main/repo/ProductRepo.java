/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.repo;

import java.util.List;
import main.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> , JpaSpecificationExecutor<Product>{
    List<Product> findByCategoryId(Integer id);
    List<Product> findByCategoryName(String name);
    List<Product> findByDiscountActive(Boolean active);
    boolean existsByName(String productName);
    boolean existsBySku(String sku);
    @Override
    Page<Product> findAll(Pageable pageable);
}
