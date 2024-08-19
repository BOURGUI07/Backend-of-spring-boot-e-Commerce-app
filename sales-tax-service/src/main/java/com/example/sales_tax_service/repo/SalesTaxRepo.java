/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.sales_tax_service.repo;

import com.example.sales_tax_service.model.SalesTax;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface SalesTaxRepo extends JpaRepository<SalesTax,Integer>{
    @Query(value = """
        SELECT COALESCE(tax_rate, 0.0)
        FROM sales_tax
        WHERE country = :country
        """, nativeQuery = true)
    public Double getTaxRateForCountry(@Param("country") String country);
    boolean existsByCountryIgnoreCase(String country);
    @Override
    Page<SalesTax> findAll(Pageable pageable);
}
