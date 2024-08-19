/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.UserPaymentDTO;
import main.models.UserPayment;
import main.repo.UserRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class UserPaymentMapper {
      UserRepo userRepo;
    
    
    public UserPayment toEntity(UserPaymentDTO x){
        var p = new UserPayment()
        .setAccountNumber(x.accountNo())
        .setPaymentProvider(x.provider())
        .setExpiryDate(x.expiryDate())
        .setPaymentType(x.type());
        userRepo.findById(x.userId()).ifPresent(p::setUser);
        return p;
    }
    
    public UserPaymentDTO toDTO(UserPayment p){
        var user = p.getUser();
        return user!=null ? new UserPaymentDTO(p.getId(),user.getId(),p.getPaymentType(),p.getPaymentProvider(),
                p.getAccountNumber(),p.getExpiryDate()
        ):null;
    }
}
