/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author hp
 */
@Getter
@Setter
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class OrderFailedEvent extends ApplicationEvent{
    String errorMessage;
    
    public OrderFailedEvent(Object source,String errorMessage) {
        super(source);
        this.errorMessage=errorMessage;
    }
    
}
