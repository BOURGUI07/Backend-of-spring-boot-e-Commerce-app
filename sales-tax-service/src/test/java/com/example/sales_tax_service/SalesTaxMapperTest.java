/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.sales_tax_service;

import com.example.sales_tax_service.dto.SalesTaxRequest;
import com.example.sales_tax_service.dto.SalesTaxResponse;
import com.example.sales_tax_service.mapper.SalesTaxMapper;
import com.example.sales_tax_service.model.SalesTax;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author hp
 */
@ExtendWith(MockitoExtension.class)
public class SalesTaxMapperTest {
    @InjectMocks
    private SalesTaxMapper mapper;
    private SalesTax salesTax = new SalesTax().setCountry("usa").setTaxRate(12.5);
    private SalesTaxRequest request = new SalesTaxRequest("usa",12.5);
    private SalesTaxResponse response  = new SalesTaxResponse("usa",12.5,salesTax.getVersion());
    
    public SalesTaxMapperTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    void test(){
        assertEquals(salesTax,mapper.toEntity(request));
        assertEquals(response,mapper.toDTO(salesTax));
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
