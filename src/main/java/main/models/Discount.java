/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
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
@Table(name="discount", indexes = {
    @Index(name = "idx_discount_id", columnList = "id"),
    @Index(name = "idx_discount_active", columnList = "active")})
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = { "products" })
public class Discount extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name="discount_name",unique=true,nullable=false,length=100)
    private String name;
    
    @Column(name="description", length=500)
    private String desc;
    
    @Column(name="discount_percent")
    private Double percent;
    
    @Column(name="active")
    private Boolean  active;
    
    @OneToMany(mappedBy="discount")
    @JsonManagedReference
    private List<Product> products=new ArrayList<>();
    
    
    @Version
    private Integer version;
    
    public void removeProduct(Product p){
        products.remove(p);
        p.setDiscount(null);
    }
    
    public Boolean getActive(){
        return (active==null)? false:active;
    }
}
