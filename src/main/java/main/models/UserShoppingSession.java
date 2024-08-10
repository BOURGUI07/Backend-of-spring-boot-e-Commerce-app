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
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author hp
 */
@Entity
@Table(name="shopping_session")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
public class UserShoppingSession extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @Transient
    private Double total;
    
    @OneToMany(mappedBy="session", cascade=CascadeType.ALL,orphanRemoval=true)
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();
    
    public void removeCartItem(CartItem cartItem){
        cartItems.remove(cartItem);
        cartItem.setSession(null);
    }
    
    public void addCartItem(CartItem c){
        cartItems.add(c);
        c.setSession(this);
        this.recalculateTotal();
    }
    
    private void recalculateTotal() {
        this.total = cartItems.stream()
                               .mapToDouble(item -> item.getProduct().discountedPrice() * item.getQuantity())
                               .sum();
    }
}
