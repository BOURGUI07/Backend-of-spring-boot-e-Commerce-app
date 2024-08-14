/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package main.repo;

import java.util.List;
import java.util.Optional;
import main.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hp
 */
@Repository
public interface WishListRepo extends JpaRepository<WishList,Integer>{
    Optional<WishList> findByNameIgnoreCase(String wishListName);
    boolean existsByNameIgnoreCase(String wishListName);
    List<WishList> findByUserId(Integer userId);
}
