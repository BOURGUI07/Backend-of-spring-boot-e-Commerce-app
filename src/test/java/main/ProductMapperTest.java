/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package main;

import java.util.List;
import java.util.Optional;
import main.dto.ProductRequestDTO;
import main.dto.ProductResponseDTO;
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
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private ProductRequestDTO x = new ProductRequestDTO("name",Optional.of("desc"),"sku", 10.4,Optional.ofNullable(null),1,Optional.ofNullable(null));
    private ProductResponseDTO y = new ProductResponseDTO(1,"name","desc","sku",10.4,Optional.empty(),50,Optional.empty(),List.of(),product.getVersion());
    private final Category category = new Category().setId(1).setName("categoryName");
    private final Discount discount = new Discount().setId(1).setName("discoutName");
    private final OrderItem orderItem = new OrderItem().setId(1);
    
    
    public ProductMapperTest() {
    }

    @Test
    void test0(){
        when(inventoryRepo.findById(1)).thenReturn(Optional.of(i));
        assertEquals(product,mapper.toEntity(x).setId(1));
        assertEquals(y,mapper.toDTO(product));
    }
    
    @Test
    void test1(){
        product.setCategory(category);
        ProductRequestDTO a = new ProductRequestDTO("name",Optional.of("desc"),"sku", 10.4,Optional.of(1),1,Optional.ofNullable(null));
        ProductResponseDTO b = new ProductResponseDTO(1,"name","desc","sku",10.4,Optional.of("categoryName"),50,Optional.empty(),List.of(),product.getVersion());
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(inventoryRepo.findById(1)).thenReturn(Optional.of(i));
        assertEquals(product,mapper.toEntity(a).setId(1));
        assertEquals(b,mapper.toDTO(product));
        

    }
    
    @Test
    void test2(){
        product.setCategory(category);
        product.setDiscount(discount);
        ProductRequestDTO a = new ProductRequestDTO("name",Optional.of("desc"),"sku", 10.4,Optional.of(1),1,Optional.of(1));
        ProductResponseDTO b = new ProductResponseDTO(1,"name","desc","sku",10.4,Optional.of("categoryName"),50,Optional.of("discoutName"),List.of(),product.getVersion());
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(discountRepo.findById(1)).thenReturn(Optional.of(discount));
        when(inventoryRepo.findById(1)).thenReturn(Optional.of(i));
        assertEquals(product,mapper.toEntity(a).setId(1));
        assertEquals(b,mapper.toDTO(product));

    }
    
    @Test
    void test3(){
        product.setCategory(category);
        product.setDiscount(discount);
        product.setOrderItems(List.of(orderItem));
        ProductRequestDTO a = new ProductRequestDTO("name",Optional.of("desc"),"sku", 10.4,Optional.of(1),1,Optional.of(1));
        ProductResponseDTO b = new ProductResponseDTO(1,"name","desc","sku",10.4,Optional.of("categoryName"),50,Optional.of("discoutName"),List.of(1),product.getVersion());
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(discountRepo.findById(1)).thenReturn(Optional.of(discount));
        when(inventoryRepo.findById(1)).thenReturn(Optional.of(i));
        assertEquals(product,mapper.toEntity(a).setId(1).setOrderItems(List.of(orderItem)));
        assertEquals(b,mapper.toDTO(product));
    }
    
    
}
