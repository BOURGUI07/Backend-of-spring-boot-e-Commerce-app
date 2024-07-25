/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.PaymentDetailDTO;
import main.dto.PaymentDetailResponseDTO;
import main.exception.EntityNotFoundException;
import main.repo.OrderRepo;
import main.repo.PaymentDetailRepo;
import main.util.PaymentStatus;
import main.util.mapper.PaymentDetailMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class PaymentDetailService {
    private final OrderRepo orepo;
    private final PaymentDetailRepo repo;
    private final PaymentDetailMapper mapper;
    
    public Page<PaymentDetailResponseDTO> findAll(int page, int size){
        return repo.findAll(PageRequest.of(page,size)).map(mapper::toDTO);
    }
    
    public PaymentDetailResponseDTO findById(Integer id){
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> 
                new EntityNotFoundException("Payment Detail with id: " + id + " isn't found")
        );
    }
    
    @Transactional
    public PaymentDetailResponseDTO create(PaymentDetailDTO x){
        var p = mapper.toEntity(x);
        var saved = repo.save(p);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public PaymentDetailResponseDTO update(Integer id, PaymentDetailDTO x){
        var p  =repo.findById(id).orElseThrow(() -> 
                new EntityNotFoundException("Payment Detail with id: " + id + " isn't found")
        );
        orepo.findById(x.orderId()).ifPresent(p::setOrder);
        p.setPaymentProvider(x.provider());
        p.setPaymentStatus(x.status());
        var saved = repo.save(p);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public void delete(Integer id){
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public List<PaymentDetailResponseDTO> findByPaymentStatus(PaymentStatus status){
        return repo.findByPaymentStatus(status).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    public List<PaymentDetailResponseDTO> findPaymentsWithAmountGreaterThan(Double amount){
        return repo.findByAmountGreaterThanEqual(amount).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
