/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.OrderDTO;
import main.models.Order;
import main.repo.OrderItemRepo;
import main.repo.OrderRepo;
import main.repo.PaymentDetailRepo;
import main.repo.UserRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class OrderMapper {
    private final OrderRepo repo;
    private final UserRepo userRepo;
    private final OrderItemRepo detailRepo;
    private final PaymentDetailRepo paymentRepo;
    
    
    public Order toEntity(OrderDTO x){
        var o = new Order();
        o.setTotal(x.total());
        userRepo.findById(x.userId()).ifPresent(o::setUser);
        paymentRepo.findById(x.paymentDetailId()).ifPresent(o::setPaymentDetail);
        var list = x.orderItemIds();
        if(list!=null){
            o.setOrderItems(detailRepo.findAllById(list));
        }
        return o;
    }
    
    public OrderDTO toDTO(Order o){
        var list = o.getOrderItems().stream().map(x -> x.getId()).collect(Collectors.toList());
        var user = o.getUser();
        var payment = o.getPaymentDetail();
        return (user!=null && payment!=null)? new OrderDTO(o.getId(),user.getId(),o.getTotal(),payment.getId(),list):null;
    }
}
