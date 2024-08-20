/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
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
import main.service.SalesTaxCalculationService;
import main.util.PaymentProvider;
import main.util.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author hp
 */
@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {
    @Mock
    private OrderRepo repo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private OrderItemRepo detailRepo;
    @Mock
    private PaymentDetailRepo paymentRepo;
    @Mock
    private SalesTaxCalculationService service;
    @Mock
    private ProductRepo productRepo;
    
    
    @InjectMocks
    private OrderMapper mapper;
    
    private User user = new User().setId(1);
    private Order order = new Order().setId(1);
    private Product product = new Product().setId(1).setPrice(10.0);
    Map map = Map.of(product.getId(), 1);
    private Inventory inv = new Inventory().setQuantity(50).setProduct(product);
    private OrderItem orderItem = new OrderItem(1,order,product,1);
    private OrderDTO x = new OrderDTO(1,1,PaymentProvider.OTHER, map);
    private OrderResponseDTO y = new OrderResponseDTO(1,1,10.0,new ArrayList<>(),1);
    
    public OrderMapperTest() {
    }
    @Test
    void test0(){
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(productRepo.findAllById(anySet())).thenReturn(List.of(product));
        var order1 = mapper.toEntity(x).setId(1);
        assertEquals(order,order1);
        assertEquals(y,mapper.toDTO(order1.setVersion(1)));
    }
    
    
    


    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
