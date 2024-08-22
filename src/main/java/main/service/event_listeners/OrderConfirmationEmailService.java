/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service.event_listeners;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import main.client.ProductApiClient;
import main.client.UserApiClient;
import main.dto.OrderDTO;
import main.event.OrderProcessedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true,level=AccessLevel.PRIVATE)
@Service
@Slf4j
public class OrderConfirmationEmailService {
     JavaMailSender mailSender;
     UserApiClient client;
     ProductApiClient productClient;
     @Value("${spring.mail.username}")
     @NonFinal String emailSender;
     
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
     public void sendEmail(OrderProcessedEvent event){
         sendOrderEmailConfirmation(event.getProcessedOrder());
     }
     
     @Async
     private void sendOrderEmailConfirmation(OrderDTO order){
         var userId = order.userId();
         var user = client.findUserById(userId);
         try{
             var message = new SimpleMailMessage();
             var subject = "Order Confirmation";
         
            var body = new StringBuilder("Hi, " + user.firstname() + " " + user.lastname()+"\n");
            body.append("Thank you for your order, here are the order details\n");
            body.append("""
                     Product_Name\tProduct_Desc\tProduct_Quantity
                     """);
            var map = order.productIdQtyMap();
            for(var productId:map.keySet()){
                 var product = productClient.findProductById(productId);
                body.append(product.name()).append("\t").append(product.desc()).append("\t").append(map.get(productId)).append("\n");
            }
            message.setFrom(emailSender);
            message.setTo(user.email());
            message.setSubject(subject);
            message.setText(body.toString());
            mailSender.send(message);
            log.info("Mail Sent Sucessfully");
         }catch(MailException ex){
             log.info("Failure Occured While Sending email");
         }
     }
}
