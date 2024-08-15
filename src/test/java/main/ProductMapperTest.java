/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import main.dto.ProductDTO;
import main.models.Category;
import main.models.Discount;
import main.models.Inventory;
import main.models.OrderItem;
import main.models.Product;
import main.repo.CategoryRepo;
import main.repo.DiscountRepo;
import main.repo.InventoryRepo;
import main.repo.OrderItemRepo;
import main.util.mapper.ProductMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author hp
 */
@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {
    @Mock
    private CategoryRepo categoryRepo;
    @Mock
    private OrderItemRepo detailRepo;
    @Mock
    private DiscountRepo discountRepo;
    @Mock
    private InventoryRepo inventoryRepo;
    @InjectMocks
    private ProductMapper mapper;
    private final Inventory i = new Inventory().setQuantity(50).setId(1);
    private final Product product = new Product()
        .setCategory(null)
        .setDiscount(null)
        .setInventory(i)
        .setDesc("desc")
        .setPrice(10.4)
        .setSku("sku")
        .setName("name")
        .setId(1);
    private ProductDTO x = new ProductDTO(1,"name","desc","sku", 10.4,null,1,null, new ArrayList<>(),null);
    private final Category category = new Category().setId(1);
    private final Discount discount = new Discount().setId(1);
    private final OrderItem orderItem = new OrderItem().setId(1);
    
    
    public ProductMapperTest() {
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
    void test0(){
        when(inventoryRepo.findById(1)).thenReturn(Optional.of(i));
        assertEquals(product,mapper.toEntity(x).setId(1));
        assertNull(mapper.toDTO(product));
    }
    
    @Test
    void test1(){
        product.setCategory(category);
        ProductDTO y = new ProductDTO(1,"name","desc","sku", 10.4,1,1,null, new ArrayList<>(),null);
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(inventoryRepo.findById(1)).thenReturn(Optional.of(i));
        assertEquals(product,mapper.toEntity(y).setId(1));
        assertNull(mapper.toDTO(product));
    }
    
    @Test
    void test2(){
        product.setCategory(category);
        product.setDiscount(discount);
        ProductDTO y = new ProductDTO(1,"name","desc","sku", 10.4,1,1,1, new ArrayList<>(),null);
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(discountRepo.findById(1)).thenReturn(Optional.of(discount));
        when(inventoryRepo.findById(1)).thenReturn(Optional.of(i));
        assertEquals(product,mapper.toEntity(y).setId(1));
        assertNotNull(mapper.toDTO(product));
    }
    
    @Test
    void test3(){
        product.setCategory(category);
        product.setDiscount(discount);
        product.setOrderItems(List.of(orderItem));
        ProductDTO y = new ProductDTO(1,"name","desc","sku", 10.4,1,1,1, List.of(1),null);
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(discountRepo.findById(1)).thenReturn(Optional.of(discount));
        when(inventoryRepo.findById(1)).thenReturn(Optional.of(i));
        when(detailRepo.findAllById(anyList())).thenReturn(List.of(orderItem));
        assertEquals(product,mapper.toEntity(y).setId(1));
        assertNotNull(mapper.toDTO(product));
        assertEquals(y,mapper.toDTO(product));
    }
    
    
}
