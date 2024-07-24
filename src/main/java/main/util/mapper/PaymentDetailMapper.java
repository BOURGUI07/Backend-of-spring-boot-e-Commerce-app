/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.RequiredArgsConstructor;
import main.dto.PaymentDetailDTO;
import main.models.PaymentDetail;
import main.repo.OrderRepo;
import main.repo.PaymentDetailRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class PaymentDetailMapper {
    private final PaymentDetailRepo repo;
    private final OrderRepo orepo;
    
    public PaymentDetail toEntity(PaymentDetailDTO x){
        var d = new PaymentDetail();
        d.setAmount(x.amount());
        d.setPaymentProvider(x.provider());
        d.setPaymentStatus(x.status());
        orepo.findById(x.orderId()).ifPresent(d::setOrder);
        return d;
    }
    
    public PaymentDetailDTO toDTO(PaymentDetail d){
        var order = d.getOrder();
        return order!=null ? new PaymentDetailDTO(d.getId(),order.getId(),d.getAmount(),
        d.getPaymentProvider(),d.getPaymentStatus()):null;
    }
}
