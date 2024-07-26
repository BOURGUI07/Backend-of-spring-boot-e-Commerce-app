/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.repo;

import java.util.List;
import main.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface OrderRepo extends JpaRepository<Order,Integer>, JpaSpecificationExecutor<Order>{
    List<Order> findByUserId(Integer id);
    @Override
    Page<Order> findAll(Pageable pageable);
}
