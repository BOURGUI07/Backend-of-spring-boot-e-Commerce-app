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
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
import main.exception.EntityNotFoundException;
import main.service.OrderService;
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
@RequestMapping("/api/orders")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    
    @Operation(summary="Retrieve All orders", description="Paginated Retrieval for all orders")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of orders is empty"),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of orders List")
    })
    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    
    @Operation(summary="Get Order By Id", description="Retrieve a single Order by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Order isn't found"),
        @ApiResponse(responseCode="200", description="Order was successfully Found"),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Integer id){
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
    @Operation(summary="Create a new  Order")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Order is successfully created"),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body")
    })
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody  OrderDTO x){
        var createdProduct = service.create(x);
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Update order")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="order isn't found"),
        @ApiResponse(responseCode="200", description="order was successfully Updated"),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body")
    })
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody  OrderDTO x){
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
    @Operation(summary="Delete order By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="order isn't found"),
        @ApiResponse(responseCode="204order", description="order was successfully Deleted"),
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
    
    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderResponseDTO>> findOrdersWithUserId(@PathVariable Integer id){
        var list = service.findOrdersByUser(id);
        try{
            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
