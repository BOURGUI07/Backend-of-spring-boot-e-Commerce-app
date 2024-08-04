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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main.dto.CartItemDTO;
import main.page_dtos.CartItemDTOPage;
import main.service.CartItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/cartitems")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
public class CartItemController {
    private final CartItemService service;
    
    @Operation(summary="Retrieve All CartItems", description="Paginated Retrieval for all cart items")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of cart item is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of cart item List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CartItemDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<CartItemDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    @Operation(summary="Get Cart Item By Id", description="Retrieve a single Cart Item by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Cart Item isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Cart Item was successfully Found",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CartItemDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> findById(@PathVariable Integer id){
        var product = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(product);
      
    }
    
    @PostMapping
    @Operation(summary="Create a new  CartItem")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="CartItem is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CartItemDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<CartItemDTO> create(@Valid @RequestBody  CartItemDTO x){
        var createdProduct = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
      
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Update cart item")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="cart item isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="cart item was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CartItemDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<CartItemDTO> update(@PathVariable Integer id, @Valid @RequestBody  CartItemDTO x){
        var updatedProduct = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        
    }
    
    @Operation(summary="Delete cart item By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="cart item isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="cart item was successfully Deleted", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
        
    }
}
