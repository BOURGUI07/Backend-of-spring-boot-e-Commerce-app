/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.sales_tax_service;

import com.example.sales_tax_service.model.SalesTax;
import com.example.sales_tax_service.repo.SalesTaxRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 *
 * @author hp
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SalesRepositoryTest {
    
    @Autowired
    private SalesTaxRepo repo;
    private SalesTax salesTax = new SalesTax().setCountry("usa").setTaxRate(12.5);
    private Integer id;
    
    public SalesRepositoryTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        id = repo.save(salesTax).getId();
        
    }
    
    @AfterEach
    public void tearDown() {
        repo.delete(salesTax);
    }
    
    @Test
    void testInferedQuery(){
        var savedSalesTax = repo.findById(id).orElse(null);
        assertNotNull(savedSalesTax);
        assertTrue(repo.existsByCountryIgnoreCase("USA"));
        assertEquals(12.5,repo.getTaxRateForCountry("usa"));
        assertEquals(0.0,repo.getTaxRateForCountry("morocco"));
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
