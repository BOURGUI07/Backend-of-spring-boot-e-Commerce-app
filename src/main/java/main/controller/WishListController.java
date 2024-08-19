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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.WishListCreationRequest;
import main.dto.WishListMergeRequest;
import main.dto.WishListResponse;
import main.dto.WishListUpdateRequest;
import main.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api/wishlists")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@Tag(name="WishList", description=" WishList Controller")
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class WishListController {
      WishListService service;
    
    
    @Operation(summary="Create a new  wish list")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="wishlist is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = WishListResponse.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WishListResponse> create(
            @Parameter(description = "wishlist to create", required = true)
            @Valid @RequestBody WishListCreationRequest x){
        var salesTax = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(salesTax);
      
    }
    
    @Operation(summary="Create a new  wishList")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="wishList is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = WishListResponse.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PutMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WishListResponse> merge(
            @Parameter(description = "wish list merge request body", required = true)
            @Valid @RequestBody WishListMergeRequest x){
        var salesTax = service.merge(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(salesTax);
      
    }
    
    
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update wishlist")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="wishlist isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="wishlist was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = WishListResponse.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<WishListResponse> update(
            @Parameter(description = "Id of the wishlist to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedWishlist details", required = true)
            @Valid @RequestBody WishListUpdateRequest x){
        var wishlist = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(wishlist);
      
    }
    
    
    
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete wishlist By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="wishlist isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="wishlist was successfully Deleted", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the wishlist to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
      
    }
    
    
    
    @PatchMapping("/add/product_id/{pid}/wishlist_id/{wid}")
    @Operation(summary="add product to wish list", description="add retrieved product by id to a wish list retireved by id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="wishlist isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="product was successfully addded to wishlist", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<WishListResponse> addProductToWishList(
            @Parameter(description = "Id of the product to add", required = true)
            @PathVariable Integer pid,
            
            @Parameter(description = "Id of the wishlist", required = true)
            @PathVariable Integer wid){
            
        return ResponseEntity.ok(service.addProductToWishList(pid, wid));
    }
    
    
    
    
    @PatchMapping("/remove/product_id/{pid}/wishlist_id/{wid}")
    @Operation(summary="remove product from a wish list", description="remove retrieved product by id from a wish list retireved by id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="wishlist isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="product was successfully removed to wishlist", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<WishListResponse> removeProductFromWishList(
            @Parameter(description = "Id of the product to remove", required = true)
            @PathVariable Integer pid,
            
            @Parameter(description = "Id of the wishlist", required = true)
            @PathVariable Integer wid){
            
        return ResponseEntity.ok(service.removeProductFromWishList(pid, wid));
    }
}
