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
import lombok.RequiredArgsConstructor;
import main.dto.SalesTaxRequest;
import main.dto.SalesTaxResponse;
import main.page_dtos.SalesTaxDTOPage;
import main.service.SalesTaxService;
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
@RequestMapping("/api/sales_taxes")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
@Tag(name="Sales Tax", description=" Sales Tax Controller")
public class SalesTaxController {
    private final SalesTaxService service;
    
    @Operation(summary="Retrieve All sales tax", description="Paginated Retrieval for all sales tax")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of sales tax is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of sales tax List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = SalesTaxDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<SalesTaxResponse>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    
    @Operation(summary="Get sales tax By Id", description="Retrieve a single sales tax by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="sales tax isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="sales tax was successfully Found",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = SalesTaxResponse.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SalesTaxResponse> findById(@PathVariable Integer id){
        var product = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(product);
       
    }
    
    @Operation(summary="Create a new  sales tax")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="sales tax is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = SalesTaxResponse.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PostMapping
    public ResponseEntity<SalesTaxResponse> create(@Valid @RequestBody SalesTaxRequest x){
        var createdProduct = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
      
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Update sales tax")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="sales tax isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="sales tax was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = SalesTaxResponse.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<SalesTaxResponse> update(@PathVariable Integer id, @Valid @RequestBody SalesTaxRequest x){
        var updatedProduct = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
      
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete sales tax By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="sales tax isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="sales tax was successfully Deleted", 
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
}
