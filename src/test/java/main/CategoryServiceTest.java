/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package main;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import main.dto.CategoryDTO;
import main.exception.EntityNotFoundException;
import main.models.Category;
import main.models.Product;
import main.repo.CategoryRepo;
import main.repo.ProductRepo;
import main.service.CategoryService;
import main.util.mapper.CategoryMapper;
import main.util.specification.CategorySpecification;
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
public class CategoryServiceTest {
    @Mock
    private  CategoryRepo repo;
    @Mock
    private  ProductRepo prepo;
    @Mock
    private  CategoryMapper mapper;
    @Mock
    private Validator validator;
    
    @InjectMocks
    private CategoryService service;
    
    private Category p = new Category().setDesc("desc").setName("name").setId(1);
    private CategoryDTO x = new CategoryDTO(1,"name","desc",null,null);
    
    public CategoryServiceTest() {
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
        Page<Category> productPage = new PageImpl<>(List.of(p));
        
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
}
