/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.dto.OrderItemDTO;
import main.page_dtos.OrderItemDTOPage;
import main.service.OrderItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/orderitems")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
@Tag(name="Order Item", description=" Order Item Controller")

public class OrderItemController {
    private final OrderItemService service;
    
    @Operation(summary="Retrieve All order items", description="Paginated Retrieval for all order items")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of order items is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of order items List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderItemDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<OrderItemDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    @Operation(summary="Get Order Item By Id", description="Retrieve a single Order Item by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Order Item isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Order Item was successfully Found",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderItemDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemDTO> findById(@PathVariable Integer id){
        var product = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(product);
       
    }
    
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Create a new  OrderItem")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="OrderItem is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderItemDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body"
                + " Or Ordered Quantity That Exceeds Product Inventory", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<OrderItemDTO> create(@Valid @RequestBody  OrderItemDTO x){
        var createdProduct = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        
    }
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update order item")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="order item isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="order item was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderItemDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body "
                + "Or Ordered Quantity That Exceeds Product Inventory", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<OrderItemDTO> update(@PathVariable Integer id, @Valid @RequestBody  OrderItemDTO x){
        var updatedProduct = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
       
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete order item By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="order item isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="order item was successfully Deleted", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
        
    }
    
    @GetMapping("/product/{id}")
    public ResponseEntity<List<OrderItemDTO>> findOrderDetailsWithProductId(@PathVariable Integer id){
        var list = service.findOrderDetailsforProduct(id);
        
            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
        
    }
}
