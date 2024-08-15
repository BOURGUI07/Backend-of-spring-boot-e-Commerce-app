/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package main;

import java.util.List;
import java.util.Optional;
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
import main.models.Inventory;
import main.models.Order;
import main.models.OrderItem;
import main.models.PaymentDetail;
import main.models.Product;
import main.models.User;
import main.repo.OrderItemRepo;
import main.repo.OrderRepo;
import main.repo.PaymentDetailRepo;
import main.repo.UserRepo;
import main.service.SalesTaxService;
import main.util.mapper.OrderMapper;
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
    private SalesTaxService service;
    @InjectMocks
    private OrderMapper mapper;
    
    private User user = new User().setId(1);
    private PaymentDetail paymentDetail = new PaymentDetail().setId(1);
    private Order order = new Order().setId(1).setUser(user).setPaymentDetail(paymentDetail).setVersion(1);
    private Inventory inv = new Inventory().setQuantity(50);
    private Product product = new Product().setInventory(inv).setId(1).setPrice(10.0);
    private OrderItem orderItem = new OrderItem(1,order,product,1);
    private OrderDTO x = new OrderDTO(1,1,1,List.of(1));
    private OrderResponseDTO y = new OrderResponseDTO(1,1,10.0,1,List.of(1),1);
    
    
    @Test
    void test0(){
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(paymentRepo.findById(1)).thenReturn(Optional.of(paymentDetail));
        when(detailRepo.findAllById(anyList())).thenReturn(List.of(orderItem));
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
