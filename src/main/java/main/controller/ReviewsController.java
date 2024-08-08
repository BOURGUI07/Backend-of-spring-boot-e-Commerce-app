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
import main.dto.ProductDTO;
import main.dto.ReviewsRequestDTO;
import main.dto.ReviewsResponseDTO;
import main.dto.ReviewsUpdateRequestDTO;
import main.page_dtos.ReviewsDTOPage;
import main.service.ReviewsService;
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
@RequestMapping("/api/reviews")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsService service;
    
    @Operation(summary="Retrieve All Reviews by rpdouct id", description="Paginated Retrieval for all reviews by product id")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of reviews is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of Review List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ReviewsDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping("/product_id/{productId}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<Page<ReviewsResponseDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size,
            @PathVariable Integer productId){
        var result = service.findAll(page, size,productId);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    @Operation(summary="Retrieve All Reviews by product name ", description="Paginated Retrieval for all reviews by product name")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of reviews is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of Review List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ReviewsDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping("/product_name/{productName}")
    public ResponseEntity<Page<ReviewsResponseDTO>> findAllByName(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size,
            @PathVariable String productName){
        var result = service.findAll(page, size,productName);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    
    @Operation(summary="Get Review By Id", description="Retrieve a single Review by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Review isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Review was successfully Found",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<ReviewsResponseDTO> findById(@PathVariable Integer id){
        var review = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(review);
       
    }
    
    @Operation(summary="Create a new  Review")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Review is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ReviewsResponseDTO> create(@Valid @RequestBody ReviewsRequestDTO x){
        var createdReview = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
      
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Update Review")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Review isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Review was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ReviewsResponseDTO> update(@PathVariable Integer id, @Valid @RequestBody ReviewsUpdateRequestDTO x){
        var updatedProduct = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
      
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete Review By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Review isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="Review was successfully Deleted", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
      
    }
}
