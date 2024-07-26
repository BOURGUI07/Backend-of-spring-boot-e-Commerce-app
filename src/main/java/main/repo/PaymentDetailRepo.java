/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.repo;

import java.util.List;
import main.models.PaymentDetail;
import main.util.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface PaymentDetailRepo extends JpaRepository<PaymentDetail,Integer>{
    List<PaymentDetail> findByPaymentStatus(PaymentStatus status);
    List<PaymentDetail> findByAmountGreaterThanEqual(Double amount);
    @Override
    Page<PaymentDetail> findAll(Pageable pageable);
}
