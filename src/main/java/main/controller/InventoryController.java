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
import main.dto.InventoryDTO;
import main.exception.EntityNotFoundException;
import main.service.InventoryService;
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
@RequestMapping("/api/inventories")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService service;
    
    @Operation(summary="Retrieve All inventories", description="Paginated Retrieval for all inventories")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of inventories is empty"),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of inventories List")
    })
    @GetMapping
    public ResponseEntity<Page<InventoryDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    @Operation(summary="Get Inventory By Id", description="Retrieve a single Inventory by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Inventory isn't found"),
        @ApiResponse(responseCode="200", description="Inventory was successfully Found"),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> findById(@PathVariable Integer id){
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
    @Operation(summary="Create a new  Inventory")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Inventory is successfully created"),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body")
    })
    public ResponseEntity<InventoryDTO> create(@Valid @RequestBody  InventoryDTO x){
        var createdProduct = service.create(x);
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Update inventory")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="inventory isn't found"),
        @ApiResponse(responseCode="200", description="inventory was successfully Updated"),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body")
    })
    public ResponseEntity<InventoryDTO> update(@PathVariable Integer id, @Valid @RequestBody  InventoryDTO x){
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
    @Operation(summary="Delete inventory By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="inventory isn't found"),
        @ApiResponse(responseCode="204", description="inventory was successfully Deleted"),
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
