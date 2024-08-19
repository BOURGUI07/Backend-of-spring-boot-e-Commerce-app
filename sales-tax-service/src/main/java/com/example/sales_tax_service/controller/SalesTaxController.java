/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.sales_tax_service.controller;

import com.example.sales_tax_service.dto.SalesTaxRequest;
import com.example.sales_tax_service.dto.SalesTaxResponse;
import com.example.sales_tax_service.page_dtos.SalesTaxDTOPage;
import com.example.sales_tax_service.service.SalesTaxService;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/sales_taxes")
@Validated
@CrossOrigin(origins = "http://localhost:9090")
@RequiredArgsConstructor
@Tag(name="Sales Tax", description=" Sales Tax Controller")
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class SalesTaxController {
      SalesTaxService service;
    
    @Operation(summary="Retrieve All sales tax", description="Paginated Retrieval for all sales tax")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of sales tax is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of sales tax List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = SalesTaxDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SalesTaxResponse>> findAll(
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
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SalesTaxResponse> findById(
            @Parameter(description = "Id of the salesTax to retrieve", required = true)
            @PathVariable Integer id){
        var salesTax = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(salesTax);
       
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
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SalesTaxResponse> create(
            @Parameter(description = "salesTax to create", required = true)
            @Valid @RequestBody SalesTaxRequest x){
        var salesTax = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(salesTax);
      
    }
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<SalesTaxResponse> update(
            @Parameter(description = "Id of the salesTax to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedSalesTax details", required = true)
            @Valid @RequestBody SalesTaxRequest x){
        var updatedSalesTax = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedSalesTax);
      
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
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the salesTax to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
      
    }
    
    
    
    
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="sales tax isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="sales taxRate was successfully Retrieved",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = Double.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered blank country", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @Operation(summary="Get Tax Rate for a specific country")
    @GetMapping("/country/{country}")
    public ResponseEntity<Double> getTaxRateForCountry(
            @Parameter(description = "country of the salesTaxRate to retrieve", required = true)
            @PathVariable String country){
        return ResponseEntity.ok(service.getTaxRateForCountry(country));
    }
}
