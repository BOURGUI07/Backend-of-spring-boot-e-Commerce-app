/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.sales_tax_service;

import com.example.sales_tax_service.dto.SalesTaxRequest;
import com.example.sales_tax_service.dto.SalesTaxResponse;
import com.example.sales_tax_service.exception.AlreadyExistsException;
import com.example.sales_tax_service.exception.EntityNotFoundException;
import com.example.sales_tax_service.mapper.SalesTaxMapper;
import com.example.sales_tax_service.model.SalesTax;
import com.example.sales_tax_service.repo.SalesTaxRepo;
import com.example.sales_tax_service.service.SalesTaxService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author hp
 */
@ExtendWith(MockitoExtension.class)
public class SalesTaxServiceTest {
    
    @Mock
    private SalesTaxRepo repo;
    @Mock
    private SalesTaxMapper mapper;
    @Mock
    private Validator validator;
    @InjectMocks
    private SalesTaxService service;
    
    
    private SalesTax salesTax = new SalesTax().setCountry("usa").setTaxRate(12.5).setId(1);
    private SalesTaxRequest request = new SalesTaxRequest("usa",12.5);
    private SalesTaxResponse response  = new SalesTaxResponse("usa",12.5,salesTax.getVersion());
    
    public SalesTaxServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        service.setValidator(validator);
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    
    @Test
    void testCreateSalesTaxWithExistingCountry(){
        when(repo.existsByCountryIgnoreCase("usa")).thenReturn(Boolean.TRUE);
        assertThrows(AlreadyExistsException.class,()->{
            service.create(request);
        });
    }
    
    @Test
    void testCreateNullCountry(){
        assertThrows(ConstraintViolationException.class,()->{
            service.create(new SalesTaxRequest(null, 12.5));
        });
    }
    
    @Test
    void testCreateSalesTaxWithNegativeorZeroTaxRate(){
        assertThrows(ConstraintViolationException.class,()->{
            service.create(new SalesTaxRequest("usa", 111.1));
        });
    }
    
    @Test
    void testGetTaxRateForCountry(){
        when(repo.existsByCountryIgnoreCase("usa")).thenReturn(Boolean.TRUE);
        when(repo.getTaxRateForCountry("usa")).thenReturn(12.5);
        assertEquals(12.5,service.getTaxRateForCountry("usa"));
    }
    
    @Test
    void testFindAll(){
        int page = 0;
        int size = 10;
        var pageable = PageRequest.of(page,size);
        var taxPage = new PageImpl<>(List.of(salesTax));
        when(repo.findAll(pageable)).thenReturn(taxPage);
        when(mapper.toDTO(salesTax)).thenReturn(response);
        assertEquals(response,service.findAll(page, size).getContent().get(0));
    }
    
    @Test
    void findByIdNotFound(){
        when(repo.findById(2)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->{
           service.findById(2);
        });
    }
    
    @Test
    void findById(){
        when(repo.findById(1)).thenReturn(Optional.of(salesTax));
        when(mapper.toDTO(salesTax)).thenReturn(response);
        assertEquals(response,service.findById(1));
    }
    
    @Test
    void testCreate(){
        when(mapper.toEntity(request)).thenReturn(salesTax);
        when(repo.save(salesTax)).thenReturn(salesTax);
        when(mapper.toDTO(salesTax)).thenReturn(response);
        assertEquals(response,service.create(request));
        verify(repo,times(1)).save(salesTax);
    }
    
    @Test
    void testUpdate(){
        var request1 = new SalesTaxRequest("uk",11.4);
        var response1 = new SalesTaxResponse("uk",11.4,salesTax.getVersion());
        when(repo.findById(1)).thenReturn(Optional.of(salesTax));
        when(repo.save(salesTax)).thenReturn(salesTax);
        when(mapper.toDTO(salesTax)).thenReturn(response1);
        assertEquals(response1,service.update(1, request1));
        assertEquals("uk",salesTax.getCountry());
        assertEquals(11.4,salesTax.getTaxRate());
        verify(repo,times(1)).save(salesTax);
    }
    
    @Test
    void testDelete(){
        doNothing().when(repo).delete(salesTax);
        when(repo.findById(1)).thenReturn(Optional.of(salesTax));
        service.delete(1);
        verify(repo,times(1)).delete(salesTax);
    }
    
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
