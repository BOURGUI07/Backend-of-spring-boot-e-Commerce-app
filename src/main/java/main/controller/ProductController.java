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
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.ProductRequestDTO;
import main.dto.ProductResponseDTO;
import main.page_dtos.ProductDTOPage;
import main.service.ProductService;
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
@RequestMapping("/api/products")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@Tag(name="Product", description=" Product Controller")
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class ProductController {
      ProductService service;
    
    @Operation(summary="Retrieve All Products", description="Paginated Retrieval for all products")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of products is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of Product List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
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
    
    
    
    @Operation(summary="Retrieve Products by Set of Ids")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of products is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of Product List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductResponseDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/product_ids",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseDTO>> findAllByIds(
            @Parameter(description = "The Set of products Ids to find")
            @RequestBody Set<Integer> ids){
        var result = service.findAllByIds(ids);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    
    
    
    
    @Operation(summary="Get Product By Id", description="Retrieve a single product by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Product isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Product was successfully Found",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<ProductResponseDTO> findById(
            @Parameter(description = "Id of the product to retrieve", required = true)
            @PathVariable Integer id){
        var product = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(product);
       
    }
    
    @Operation(summary="Create a new  Product")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Product is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<ProductResponseDTO> create(
            @Parameter(description = "product to create", required = true)
            @Valid @RequestBody ProductRequestDTO x){
        var createdProduct = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
      
    }
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update Product")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Product isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Product was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<ProductResponseDTO> update(
            @Parameter(description = "Id of the product to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedProduct details", required = true)
            @Valid @RequestBody ProductRequestDTO x){
        var updatedProduct = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
      
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete Product By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Product isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="Product was successfully Deleted", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the product to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
      
    }
    
    @GetMapping(value="/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProductResponseDTO>> search(
            @Parameter(description = "product name to search for", required = false)
            @RequestParam(required=false) String name,
            
            @Parameter(description = "product desc to search for", required = false)
            @RequestParam(required=false) String desc,
            
            @Parameter(description = "product discount status to search for", required = false)
            @RequestParam(required=false) Boolean discountStatus,
            
            @Parameter(description = "category name to search for", required = false)
            @RequestParam(required=false) String categoryName,
            
            @Parameter(description = "product min price to search for", required = false)
            @RequestParam(required=false) Double minPrice,
            
            @Parameter(description = "product max price to search for", required = false)
            @RequestParam(required=false) Double maxPrice,
            
            @Parameter(description = "The page number to retrieve", example = "0")
            @RequestParam(defaultValue = "0") int page,
        
            @Parameter(description = "The number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size
    ){
        var result = service.search(name, desc, discountStatus, categoryName, minPrice, maxPrice, page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    @GetMapping(value="/categories/{categoryId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductResponseDTO>> findProductsWithCategoryId(
            @Parameter(description = "Id of the category to retrieve products for", required = true)
            @PathVariable Integer categoryId){
        var list = service.findProductsWithCategoryId(categoryId);
        
            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
      
    }
}
