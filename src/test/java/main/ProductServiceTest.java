/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package main;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import main.dto.ProductDTO;
import main.exception.EntityNotFoundException;
import main.models.Inventory;
import main.models.Product;
import main.repo.CategoryRepo;
import main.repo.DiscountRepo;
import main.repo.InventoryRepo;
import main.repo.OrderItemRepo;
import main.repo.ProductRepo;
import main.service.ProductService;
import main.util.mapper.ProductMapper;
import main.util.specification.ProductSpecification;
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
public class ProductServiceTest {
    @Mock
    private  ProductRepo repo;
    @Mock
    private  CategoryRepo categoryRepo;
    @Mock
    private  DiscountRepo discountRepo;
    @Mock
    private  InventoryRepo invRepo;
    @Mock
    private  OrderItemRepo orepo;
    @Mock
    private  ProductMapper mapper;
    @InjectMocks
    private ProductService service;
    
    private Validator validator;
    
    private ProductDTO x;
    private Product p = new Product();
    private Inventory i = new Inventory();
    
    public ProductServiceTest() {
        i.setId(1);
        i.setQuantity(10);
        x = new ProductDTO(1,"name","desc","sku", 10.4,null,1,null, new ArrayList<>(),null);
        p.setCategory(null);
        p.setDiscount(null);
        p.setInventory(i);
        p.setDesc("desc");
        p.setPrice(10.4);
        p.setSku("sku");
        p.setName("name");
        p.setId(1);
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
    void testCreate(){
        when(mapper.toEntity(x)).thenReturn(p);
        when(repo.save(p)).thenReturn(p);
        when(mapper.toDTO(p)).thenReturn(x);
        
        assertEquals(x, service.create(x));
        
        verify(repo,times(1)).save(p);
    }
    
    @Test
    void testUpdate(){
        when(repo.findById(1)).thenReturn(Optional.of(p));
        when(repo.save(p)).thenReturn(p);
        when(mapper.toDTO(p)).thenReturn(x);
        
        assertEquals(x,service.update(1, x));
        
        verify(repo,times(1)).save(p);
    }
    
    @Test
    void testFindById(){
        when(repo.findById(1)).thenReturn(Optional.of(p));
        when(mapper.toDTO(p)).thenReturn(x);
        
        assertEquals(x,service.findById(1));
    }
    
    @Test 
    void testFindByNegativeId(){
        assertThrows(IllegalArgumentException.class,
                () -> service.findById(-2));
    }
    
    @Test
    void testFindByIdNotFound(){
        when(repo.findById(2)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> service.findById(2));
    }
    
    @Test
    void testFindAll(){
        var pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(p));
        
        when(repo.findAll(pageable)).thenReturn(productPage);
        when(mapper.toDTO(p)).thenReturn(x);
        assertEquals(x, service.findAll(0, 10).getContent().get(0));
    }
    
    @Test
    void testFindAllWithNegativeArguments(){
        int page = -1;
        int size = 10;
        assertThrows(IllegalArgumentException.class, () -> service.findAll(page, size));
    }
    
    @Test
    void testDelete(){
        doNothing().when(repo).delete(p);
        when(repo.findById(1)).thenReturn(Optional.of(p));
        service.delete(1);
        verify(repo,times(1)).delete(p);
    }
    
    @Test
    void testValidation(){
        //Test InventoryId Nullability
        var product1 = new ProductDTO(1,"name","desc","sku", 10.4,null,null,null, new ArrayList<>(),null);
        assertThrows(ConstraintViolationException.class, () -> {
            service.create(product1);
        });
        
        //Test Product Price Positivity
        var product2 = new ProductDTO(1,"name","desc","sku",0.0,null,1,null, new ArrayList<>(),null);
        assertThrows(ConstraintViolationException.class, () -> {
            service.create(product2);
        });
        
        //Test Product Name Blank state
        var product3 = new ProductDTO(1,"","desc","sku",5.0,null,1,null, new ArrayList<>(),null);
        assertThrows(ConstraintViolationException.class, () -> {
            service.create(product3);
        });
        
        //Test Product SKU Blank state
        var product4 = new ProductDTO(1,"name","desc","",5.0,null,1,null, new ArrayList<>(),null);
        assertThrows(ConstraintViolationException.class, () -> {
            service.create(product4);
        });
    }
   
    @Test
    void testProductsWithCategoryId(){
        when(repo.findByCategoryId(1)).thenReturn(List.of(p));
        when(mapper.toDTO(p)).thenReturn(x);
        assertEquals(x, service.findProductsWithCategoryId(1).get(0));
    }
    
   
}
