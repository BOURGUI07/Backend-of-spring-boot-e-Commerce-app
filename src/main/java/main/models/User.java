/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User extends BaseEntity implements UserDetails{
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
    
    
    @JoinColumn(name="role")
    @OneToOne
    private Role role;
    
    
    @OneToMany(mappedBy="user", cascade=CascadeType.ALL,orphanRemoval=true)
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();
    
    public void addOrder(Order order){
        orders.add(order);
        order.setUser(this);
    }
 

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+this.getRole().getName().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
