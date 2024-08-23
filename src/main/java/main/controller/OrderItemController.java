/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.OrderItemCreationRequest;
import main.dto.OrderItemResponse;
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
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class OrderItemController {
      OrderItemService service;
    
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
    public ResponseEntity<Page<OrderItemResponse>> findAll(
            @Parameter(description = "The page number to retrieve", example = "0")
            @RequestParam(defaultValue = "0") int page,
        
            @Parameter(description = "The number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size){
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
                     schema = @Schema(implementation = OrderItemResponse.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderItemResponse> findById(
            @Parameter(description = "Id of the orderItem to retrieve", required = true)
            @PathVariable Integer id){
        var orderItem = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(orderItem);
       
    }
    
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Create a new  OrderItem")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="OrderItem is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderItemResponse.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body"
                + " Or Ordered Quantity That Exceeds Product Inventory", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<OrderItemResponse> create(
            @Parameter(description = "orderItem to create", required = true)
            @Valid @RequestBody  OrderItemCreationRequest x){
        var orderItem = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
        
    }
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update order item")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="order item isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="order item was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderItemResponse.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body "
                + "Or Ordered Quantity That Exceeds Product Inventory", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<OrderItemResponse> update(
            @Parameter(description = "Id of the orderItem to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedOrderItme details", required = true)
            @Valid @RequestBody  OrderItemCreationRequest x){
        var updatedOrderItme = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedOrderItme);
       
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
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the orderItem to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
        
    }
    
    @GetMapping("/products/{productId}")
    public ResponseEntity<List<OrderItemResponse>> findOrderDetailsWithProductId(@PathVariable Integer productId){
        var list = service.findOrderDetailsforProduct(productId);
        
            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
        
    }
}
