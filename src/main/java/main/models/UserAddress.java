/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

/**
 *
 * @author hp
 */
@Entity
@Table(name="user_address")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @Column(name="address_line1")
    private String addressLine1;
    
    @Column(name="address_line2")
    private String addressLine2;
    
    @Column(name="city")
    private String city;
    
    @Column(name="postalcode")
    private String postalCode;
    
    @Column(name="country")
    private String country;
}
