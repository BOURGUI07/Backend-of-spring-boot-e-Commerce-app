/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main.dto.CategoryDTO;
import main.dto.SessionResponseDTO;
import main.dto.UserShoppingSessionDTO;
import main.exception.EntityNotFoundException;
import main.service.SessionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api/sessions")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService service;
    
    @GetMapping
    public ResponseEntity<Page<SessionResponseDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> findById(@PathVariable Integer id){
        var product = service.findById(id);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<SessionResponseDTO> create(@Valid @RequestBody UserShoppingSessionDTO x){
        var createdProduct = service.create(x);
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody  UserShoppingSessionDTO x){
        var updatedProduct = service.update(id, x);
        try{
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }catch(IllegalArgumentException | ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        try{
            service.delete(id);
            return ResponseEntity.noContent().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<SessionResponseDTO> findAddressByUser(@PathVariable Integer id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getSessionByUserId(id));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
