/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service.event_listeners;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.client.UserAddressApiClient;
import main.dto.UserAddressDTO;
import main.dto.UserAddressRequest;
import main.event.UserCreationEvent;
import org.springframework.context.event.EventListener;
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
public class UserAddressCreationService {
    UserAddressApiClient client;
    
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void acceptEvent(UserCreationEvent event){
        createUserAddress(event.getAddressRequest());
    }
    
    @Async
    private void createUserAddress(UserAddressRequest addressRequest) {
        client.createUserAddress(addressRequest);
    }
}
