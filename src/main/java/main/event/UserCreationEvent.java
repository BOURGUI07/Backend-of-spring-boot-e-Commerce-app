/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import main.dto.UserAddressRequest;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author hp
 */
@Getter
@Setter
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class UserCreationEvent extends ApplicationEvent{
    UserAddressRequest addressRequest;
    public UserCreationEvent(Object source,UserAddressRequest addressRequest) {
        super(source);
        this.addressRequest=addressRequest;
    }
    
}
