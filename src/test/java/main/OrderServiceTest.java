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
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
import main.exception.EntityNotFoundException;
import main.models.Inventory;
import main.models.Order;
import main.models.OrderItem;
import main.models.Product;
import main.models.User;
import main.repo.OrderItemRepo;
import main.repo.OrderRepo;
import main.repo.PaymentDetailRepo;
import main.repo.ProductRepo;
import main.repo.UserRepo;
import main.service.OrderService;
import main.service.SalesTaxService;
import main.util.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
public class OrderServiceTest {
    @Mock
    private  OrderRepo repo;
    @Mock
    private  UserRepo userRepo;
    @Mock
    private  OrderItemRepo detailRepo;
    @Mock
    private  PaymentDetailRepo paymentRepo;
    @Mock
    private  OrderMapper mapper;
    @Mock
    private ProductRepo productRepo;
    @Mock
    private SalesTaxService taxService;
    @InjectMocks
    private OrderService service;
    
    private Validator validator;
    private User user = new User(1,"username","password",
                                    "firstname","lastname",
                                    "email","phone",null,
                                        new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),null);
    
    private Order  p =new Order().setId(1).setUser(user);
    private Inventory inv = new Inventory().setQuantity(50);
    private Product product = new Product().setInventory(inv).setId(1);
    private OrderItem orderItem = new OrderItem(1,p,product,1);
    private OrderDTO x = new OrderDTO(1,1,1,List.of(1));
    private OrderResponseDTO y = new OrderResponseDTO(1,1,0.0,1,null,null);
    
    public OrderServiceTest() {
    }
    

    
    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        service.setValidator(validator);
        
    }
@Test
void testCreate() {
    // Mock user
    when(userRepo.findById(1)).thenReturn(Optional.of(user));

    // Mock order items
    List<OrderItem> orderItems = List.of(orderItem);
    when(detailRepo.findAllById(x.orderItemIds())).thenReturn(orderItems);

    // Mock products
    List<Product> products = List.of(product);
    when(productRepo.findAllById(anyList())).thenReturn(products);

    // Mock saving
    when(productRepo.saveAll(anyList())).thenReturn(products);
    when(detailRepo.saveAll(anyList())).thenReturn(orderItems);
    when(userRepo.save(any(User.class))).thenReturn(user);
    when(repo.save(any(Order.class))).thenReturn(p);

    // Mock mapping
    when(mapper.toEntity(x)).thenReturn(p);
    when(mapper.toDTO(p)).thenReturn(y);

    // Execute and verify
    assertEquals(y, service.create(x));

    verify(userRepo, times(1)).findById(1);
    verify(detailRepo, times(1)).findAllById(x.orderItemIds());
    verify(productRepo, times(1)).findAllById(anyList());
    verify(productRepo, times(1)).saveAll(anyList());
    verify(detailRepo, times(1)).saveAll(anyList());
    verify(userRepo, times(1)).save(any(User.class));
    verify(repo, times(1)).save(any(Order.class));
    verify(mapper, times(1)).toEntity(x);
    verify(mapper, times(1)).toDTO(p);
}
    
    @Test
    void testUpdate(){
        when(repo.findById(1)).thenReturn(Optional.of(p));
        when(repo.save(p)).thenReturn(p);
        when(mapper.toDTO(p)).thenReturn(y);
        
        assertEquals(y,service.update(1, x));
        
        verify(repo,times(1)).save(p);
    }
    
    @Test
    void testFindById(){
        when(repo.findById(1)).thenReturn(Optional.of(p));
        when(mapper.toDTO(p)).thenReturn(y);
        
        assertEquals(y,service.findById(1));
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
        Page<Order> productPage = new PageImpl<>(List.of(p));
        
        when(repo.findAll(pageable)).thenReturn(productPage);
        when(mapper.toDTO(p)).thenReturn(y);
        assertEquals(y, service.findAll(0, 10).getContent().get(0));
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
    void testFindOrdersByUser(){
        when(repo.findByUserId(1)).thenReturn(List.of(p));
        when(mapper.toDTO(p)).thenReturn(y);
        assertEquals(y,service.findOrdersByUser(1).get(0));
    }
}
