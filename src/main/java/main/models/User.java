/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import main.util.Role;

/**
 *
 * @author hp
 */
@Entity
@Table(name="users")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name="username",unique=true,nullable=false)
    private String username;
    
    @Column(name="password",unique=true,nullable=false)
    private String password;
    
    @Column(name="firstname")
    private String firstname;
    
    @Column(name="lastname")
    private String lastname;
    
    @Column(name="email")
    private String email;
    
    @Column(name="phone")
    private String phone;
    
    
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;
    
    
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL,orphanRemoval=true)
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();
    
    public void removeOrder(Order order){
        orders.add(order);
        order.setUser(this);
    }
}
