/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service.event_listeners;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.client.PaymentDetailApiClient;
import main.dto.PaymentDetailDTO;
import main.event.OrderCreationEvent;
import main.models.PaymentDetail;
import main.service.PaymentDetailService;
import main.util.PaymentStatus;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true,level=AccessLevel.PRIVATE)
@Service
public class ProcessPaymentService {
    PaymentDetailApiClient client;
    
    @EventListener
    @Order(1)
    public void processPayment(OrderCreationEvent event){
        var order = event.getOrderRequest();
        var paymentRequest = new PaymentDetailDTO(order.id(),order.provider(),PaymentStatus.PENDING);
        client.createPayment(paymentRequest);
    }
}
