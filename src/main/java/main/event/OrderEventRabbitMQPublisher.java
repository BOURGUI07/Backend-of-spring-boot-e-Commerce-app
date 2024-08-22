/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.event;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class OrderEventRabbitMQPublisher {
     RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.email.name}")
    @NonFinal String emailExchange;

    @Value("${rabbitmq.binding.email.name}")
    @NonFinal String emailRoutingKey;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishOrderEvent(OrderProcessedEvent event) {
        rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, event);
        log.info("Order event sent to RabbitMQ: {}", event.getProcessedOrder());
    }
}
