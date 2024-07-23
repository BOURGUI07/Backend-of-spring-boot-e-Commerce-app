/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author hp
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
public class BaseEntity implements Serializable{
    @CreatedDate
    @Column(name="createdAt", nullable=false,updatable=false)
    @JsonIgnore
    private Instant createdAt;
    
    
    @LastModifiedDate
    @Column(name="lastModifiedAt")
    @JsonIgnore
    private Instant lastModifiedAt;
}
