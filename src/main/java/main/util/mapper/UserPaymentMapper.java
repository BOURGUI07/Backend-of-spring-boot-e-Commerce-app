/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.RequiredArgsConstructor;
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
public class UserPaymentMapper {
    private final UserRepo userRepo;
    
    
    public UserPayment toEntity(UserPaymentDTO x){
        var p = new UserPayment();
        p.setAccountNumber(x.accountNo());
        p.setPaymentProvider(x.provider());
        p.setExpiryDate(x.expiryDate());
        p.setPaymentType(x.type());
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
