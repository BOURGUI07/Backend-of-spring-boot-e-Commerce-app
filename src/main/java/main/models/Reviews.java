/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name="reviews", indexes = {
    @Index(name = "idx_product_id", columnList = "product_id"),
    @Index(name = "idx_rating", columnList = "rating")})
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@DynamicInsert
@DynamicUpdate
public class Reviews extends BaseEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="user_id", updatable=false)
    private User user;
    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="product_id", updatable=false)
    private Product product;
    
    @Column(name="title")
    private String title;
    
    @Column(name="content")
    private String content;
    
    @Column(name="rating", nullable=false)
    private Integer rating;
    
    public String getTitle(){
        return title!=null?title:"";
    }
    
    public String getContent(){
        return content!=null?content:"";
    }
    
    public void setUser(User user){
        user.addReview(this);
        this.user = user;
    }
    
    public void setProduct(Product product){
        product.addReview(this);
        this.product=product;
    }
}
