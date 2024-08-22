/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package main;


import java.util.List;
import main.models.Product;
import main.repo.ProductRepo;
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
public class ProductRepositoryTest {
    
    @Autowired
    private ProductRepo repo;
    
    private Product product = new Product()
                                           .setCategory(null)
                                           .setDesc("desc")
                                           .setSku("sku")
                                           .setPrice(5.5).setName("name").setDiscount(null);
    
    private Integer id;
    public ProductRepositoryTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        var saved = repo.save(product);
        id = saved.getId();
    }
    
    @AfterEach
    public void tearDown() {
        repo.delete(product);
        
    }
    
    @Test
    void testCreate(){
        var savedProduct = repo.findById(id).orElse(null);
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getVersion());
        assertNull(savedProduct.getCategory());
        assertNull(savedProduct.getDiscount());
        assertEquals("sku",savedProduct.getSku());
        assertEquals("name",savedProduct.getName());
        assertEquals("desc",savedProduct.getDesc());
        assertEquals(5.5,savedProduct.getPrice());
        assertTrue(savedProduct.getOrderItems().isEmpty());
        assertTrue(savedProduct.getWishLists().isEmpty());
        assertTrue(savedProduct.getReviews().isEmpty());
    }
    
    @Test
    void testUpdate(){
        var savedProduct = repo
                .findById(id)
                .orElse(null)
                .setDesc("description");
        repo.save(savedProduct);
        var updatedProduct= repo.findById(id).orElse(null);
        assertNotNull(updatedProduct);
        assertEquals("description",updatedProduct.getDesc());
        
    }
    
    @Test
    void testInferredQueries(){
        assertTrue(repo.findByCategoryName("name").isEmpty());
        assertTrue(repo.findByCategoryId(1).isEmpty());
        assertTrue(repo.findByDiscountActive(Boolean.FALSE).isEmpty());
    }
    
    @Test
    void testFindAll(){
        var savedProduct = repo.findById(id).orElse(null);
        assertNotNull(savedProduct);
        assertEquals(List.of(savedProduct),repo.findAll());
        assertEquals(List.of(savedProduct),repo.findAllById(List.of(id)));
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
