/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.dto.SessionResponseDTO;
import main.dto.UserShoppingSessionDTO;
import main.exception.EntityNotFoundException;
import main.repo.CartItemRepo;
import main.repo.SessionRepo;
import main.repo.UserRepo;
import main.util.mapper.SessionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class SessionService {
    private final UserRepo urepo;
    private final CartItemRepo crepo;
    private final SessionRepo repo;
    private final SessionMapper mapper;
    
    public Page<SessionResponseDTO> findAll(int page, int size){
        var p = PageRequest.of(page,size);
        return repo.findAll(p).map(mapper::toDTO);
    }
    
    public SessionResponseDTO findById(Integer id){
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() ->
            new EntityNotFoundException("User Session with id: " + id + " isn't found"));
    }
    
    @Transactional
    public SessionResponseDTO create(UserShoppingSessionDTO x){
        var s = mapper.toEntity(x);
        var saved = repo.save(s);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public SessionResponseDTO update(Integer id, UserShoppingSessionDTO x){
        var s = repo.findById(id).orElseThrow(() ->
            new EntityNotFoundException("User Session with id: " + id + " isn't found"));
        
        urepo.findById(x.userId()).ifPresent(s::setUser);
        var list = x.cartItemIds();
        if(list!=null){
            var list1 = crepo.findAllById(list);
            list1.forEach(s::addCartItem);
        }
        var saved = repo.save(s);
        return mapper.toDTO(saved);
    }
    
    @Transactional
    public void delete(Integer id){
        repo.findById(id).ifPresent(repo::delete);
    }
    
    public SessionResponseDTO getSessionByUserId(Integer id){
        return repo.findByUserId(id).map(mapper::toDTO).orElseThrow(() -> 
        new EntityNotFoundException("User with id: "+ id + " isn't found"));
    }
}
