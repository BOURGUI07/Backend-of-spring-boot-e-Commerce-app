/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.repo;

import java.util.List;
import java.util.Optional;
import main.models.UserShoppingSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface SessionRepo extends JpaRepository<UserShoppingSession,Integer>{
    Optional<UserShoppingSession> findByUserId(Integer id);
    List<UserShoppingSession> findByTotalBetween(Double minTotal,Double maxTotal);
    @Override
    Page<UserShoppingSession> findAll(Pageable pageable);
}
