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
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import main.util.PaymentProvider;
import main.util.PaymentType;

/**
 *
 * @author hp
 */
@Entity
@Table(name="user_payment")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class UserPayment extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(name="payment_type")
    private PaymentType paymentType;
    
    
    @Enumerated(EnumType.STRING)
    @Column(name="payment_provider")
    private PaymentProvider paymentProvider;
    
    @Column(name="account_no")
    private Integer AccountNumber;
    
    
    @Column(name="expiry")
    private LocalDate expiryDate;
}
