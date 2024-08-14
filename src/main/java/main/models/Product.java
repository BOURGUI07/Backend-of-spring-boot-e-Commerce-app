/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
@Table(name="product", indexes = {
    @Index(name = "idx_product_id", columnList = "id"),
    @Index(name = "idx_product_name", columnList = "product_name"),
    @Index(name = "idx_product_price", columnList = "price"),
    @Index(name = "idx_product_desc", columnList = "description"),
    @Index(name = "idx_category_id", columnList = "category_id"),
    @Index(name = "idx_discount_id", columnList = "discount_id"),
    @Index(name = "idx_inventory_id", columnList = "inventory_id")
})
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = { "orderItems" })
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name="product_name",nullable=false,unique=true,length=100)
    private String name;
    
    @Column(name="description",length=500)
    private String desc;
    
    @Column(name="sku", nullable=false, unique=true, length=16)
    private String sku;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="category_id")
    private Category category;
    
    @OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
    @JoinColumn(name="inventory_id")
    private Inventory inventory;
    
    @Column(name="price")
    private Double price;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="discount_id")
    private Discount discount;
    
    @OneToMany(mappedBy="product", cascade=CascadeType.ALL,orphanRemoval=true)
    @JsonManagedReference
    private List<OrderItem> orderItems=new ArrayList<>();
    
    
    @OneToMany(mappedBy="product", cascade=CascadeType.ALL,orphanRemoval=true)
    @JsonManagedReference
    private List<Reviews> reviews = new ArrayList<>();
    
    @Version
    private Integer version;
    
    @ManyToMany(mappedBy="products")
    @JsonBackReference
    private List<WishList> wishLists = new ArrayList<>();
    
    public void addWishList(WishList w){
        wishLists.add(w);
        w.addProduct(this);
    }
    
    public void removeWishList(WishList w){
        wishLists.remove(w);
        w.removeProduct(this);
    }
    
    public void addOrderItem(OrderItem orderItem){
        if(orderItem.getQuantity()<=inventory.getQuantity()){
            orderItems.add(orderItem);
            orderItem.setProduct(this);
        }
    }
    
    public void addReview(Reviews r){
        reviews.add(r);
    }
    
    public Double discountedPrice(){
        Double actualPrice = (price!=null) ? price :0.0;
        return (discount!=null && discount.getActive())? actualPrice*(1-discount.getPercent()):actualPrice;
    }
    
    public Inventory getInventory(){
        return inventory==null? new Inventory().setQuantity(0):inventory;
    }
}
