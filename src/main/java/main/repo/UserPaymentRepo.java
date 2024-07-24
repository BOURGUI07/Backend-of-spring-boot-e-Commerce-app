/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.repo;

import java.util.List;
import java.util.Optional;
import main.models.UserPayment;
import main.util.PaymentProvider;
import main.util.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface UserPaymentRepo extends JpaRepository<UserPayment,Integer>{
    List<UserPayment> findByPaymentProvider(PaymentProvider provider);
    List<UserPayment> findByPaymentType(PaymentType type);
    Optional<UserPayment> findByUserId(Integer id);
}
