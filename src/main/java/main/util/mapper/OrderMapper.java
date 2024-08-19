/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
import main.models.Order;
import main.repo.OrderItemRepo;
import main.repo.PaymentDetailRepo;
import main.repo.UserRepo;
import main.service.SalesTaxCalculationService;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class OrderMapper {
    private final UserRepo userRepo;
    private final OrderItemRepo detailRepo;
    private final PaymentDetailRepo paymentRepo;
    private final SalesTaxCalculationService taxService;
    
    public Order toEntity(OrderDTO x){
        var o = new Order();
        var user = userRepo.findById(x.userId()).get();
        var payment = paymentRepo.findById(x.paymentDetailId()).get();
        payment.setOrder(o);
        o.setPaymentDetail(payment);
        var list = x.orderItemIds();
            var orderItems = detailRepo.findAllById(list);
            orderItems.forEach(o::addOrderItem);
            taxService.calculateTotalOrderPrice(o);
            user.addOrder(o);
            detailRepo.saveAll(orderItems);
            userRepo.save(user);
            paymentRepo.save(payment);
        
        return o;
    }
    
    public OrderResponseDTO toDTO(Order o){
        var list = o.getOrderItems().stream().map(x -> x.getId()).collect(Collectors.toList());
        var user = o.getUser();
        var payment = o.getPaymentDetail();
        return (user != null && payment != null) ? 
                new OrderResponseDTO(o.getId(), user.getId(), o.getTotal(), payment.getId(), list,o.getVersion()) : null;
    }
}

