/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import main.util.PaymentProvider;
import main.util.PaymentStatus;

/**
 *
 * @author hp
 */
@Entity
@Table(name="payment_detail")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @OneToOne(mappedBy="paymentDetail")
    private Order order;
    
    @Column(name="amount",nullable=false)
    private final Double amount = order.getTotal();
    
    @Enumerated(EnumType.STRING)
    @Column(name="payment_provider",nullable=false)
    private PaymentProvider paymentProvider;
    
    @Enumerated(EnumType.STRING)
    @Column(name="payment_status",nullable=false)
    private PaymentStatus paymentStatus;
}
