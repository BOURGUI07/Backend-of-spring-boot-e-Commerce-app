/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main.dto.DiscountDTO;
import main.exception.EntityNotFoundException;
import main.service.DiscountService;
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
@RequestMapping("/api/discounts")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService service;
    
    @Operation(summary="Retrieve All discounts", description="Paginated Retrieval for all discounts")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of discounts is empty"),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of discounts List")
    })
    @GetMapping
    public ResponseEntity<Page<DiscountDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    @Operation(summary="Get Discount By Id", description="Retrieve a single Discount by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Discount isn't found"),
        @ApiResponse(responseCode="200", description="Discount was successfully Found"),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DiscountDTO> findById(@PathVariable Integer id){
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
    @Operation(summary="Create a new  Discount")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Discount is successfully created"),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body")
    })
    public ResponseEntity<DiscountDTO> create(@Valid @RequestBody  DiscountDTO x){
        var createdProduct = service.create(x);
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Update discount")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="discount isn't found"),
        @ApiResponse(responseCode="200", description="discount was successfully Updated"),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body")
    })
    public ResponseEntity<DiscountDTO> update(@PathVariable Integer id, @Valid @RequestBody  DiscountDTO x){
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
    @Operation(summary="Delete discount By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="discount isn't found"),
        @ApiResponse(responseCode="204", description="discount was successfully Deleted"),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id")
    })
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
}
