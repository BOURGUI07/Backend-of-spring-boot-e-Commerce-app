/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import main.dto.OrderDTO;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author hp
 */
@Getter
@Setter
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class OrderCreationEvent extends ApplicationEvent{
    OrderDTO orderRequest;
    
    public OrderCreationEvent(Object source, OrderDTO x) {
        super(source);
        this.orderRequest=x;
    }
    
}
