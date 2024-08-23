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
import main.dto.OrderDTO;
import main.dto.OrderResponseDTO;
import main.page_dtos.OrderResponseDTOPage;
import main.service.OrderService;
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
@RequestMapping("/api/orders")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
@Tag(name="Order", description=" Order Controller")
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class OrderController {
      OrderService service;
    
    @Operation(summary="Retrieve All orders", description="Paginated Retrieval for all orders")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of orders is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of orders List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderResponseDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<OrderResponseDTO>> findAll(
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
    
    
    
    @Operation(summary="Get Order By Id", description="Retrieve a single Order by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Order isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Order was successfully Found",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponseDTO> findById(
            @Parameter(description = "Id of the order to retrieve", required = true)
            @PathVariable Integer id){
        var order = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(order);
        
    }
    
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Create a new  Order")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Order is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content)
    })
    public ResponseEntity<OrderResponseDTO> create(
            @Parameter(description = "order to create", required = true)
            @Valid @RequestBody  OrderDTO x){
        var order = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        
    }
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update order")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="order isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="order was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = OrderResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<OrderResponseDTO> update(
            @Parameter(description = "Id of the order to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedOrder details", required = true)
            @Valid @RequestBody  OrderDTO x){
        var updatedOrder = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
        
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete order By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="order isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204order", description="order was successfully Deleted", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the order to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
        
    }
    
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> findOrdersWithUserId(@PathVariable Integer userId){
        var list = service.findOrdersByUser(userId);
        
            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
        
    }
}
