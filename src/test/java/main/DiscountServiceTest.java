/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package main;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import main.dto.AddProductsToDiscountRequest;
import main.dto.DiscountRequestDTO;
import main.dto.DiscountResponseDTO;
import main.models.Discount;
import main.models.Product;
import main.repo.DiscountRepo;
import main.repo.ProductRepo;
import main.service.DiscountService;
import main.util.mapper.DiscountMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anySet;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author hp
 */
@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {
    @Mock
    private  DiscountRepo repo;
    @Mock
    private  DiscountMapper mapper;
    @Mock
    private  ProductRepo productRepo;
    @InjectMocks
    private DiscountService service;
    private Validator validator;
    
    private DiscountRequestDTO request = new DiscountRequestDTO("name",Optional.of("desc"),5.0,Optional.of(Boolean.FALSE));
    private Discount d= new Discount().setId(1).setActive(Boolean.FALSE).setDesc("desc").setName("name").setPercent(5.0);
    private DiscountResponseDTO response = new DiscountResponseDTO(1,"name","desc",5.0,Boolean.FALSE,List.of(),d.getVersion());
    
    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        service.setValidator(validator);
    }
    
    @Test
    void testCreate(){
        when(mapper.toEntity(request)).thenReturn(d);
        when(repo.save(d)).thenReturn(d);
        when(mapper.toDTO(d)).thenReturn(response);
        assertEquals(response, service.create(request));
        verify(repo,times(1)).save(d);
    }
    
    @Test
    void testFindById(){
        when(repo.findById(1)).thenReturn(Optional.of(d));
        when(mapper.toDTO(d)).thenReturn(response);
        assertEquals(response,service.findById(1));
    }
    
    @Test
    void testUpdate(){
        var request1 = new DiscountRequestDTO("name1",Optional.of("desc"),7.0,Optional.of(Boolean.FALSE));
        var response1 = new DiscountResponseDTO(1,"name1","desc",7.0,Boolean.FALSE,List.of(),d.getVersion());
        d.setName("name1").setPercent(7.0);
        when(repo.findById(1)).thenReturn(Optional.of(d));
        when(repo.save(d)).thenReturn(d);
        when(mapper.toDTO(d)).thenReturn(response1);
        assertEquals(response1,service.update(1, request1));
        verify(repo,times(1)).save(d);
        
    }
    
    @Test
    void testDelete(){
        doNothing().when(repo).delete(d);
        when(repo.findById(1)).thenReturn(Optional.of(d));
        service.delete(1);
        verify(repo,times(1)).delete(d);
    }
    
    @Test
    void testAddProducts(){
        var product = new Product().setId(1);
        var  addProductRequest = new AddProductsToDiscountRequest(1,Set.of(1));
        d.addProduct(product);
        var response1 = new DiscountResponseDTO(1,"name","desc",5.0,Boolean.FALSE,List.of(1),d.getVersion());
        when(productRepo.findAllById(anySet())).thenReturn(List.of(product));
        when(repo.findById(1)).thenReturn(Optional.of(d));
        when(repo.save(d)).thenReturn(d);
        when(mapper.toDTO(d)).thenReturn(response1);
        assertEquals(response1,service.addProducts(addProductRequest));
        verify(repo,times(1)).save(d);
        
    }
    
    
}
