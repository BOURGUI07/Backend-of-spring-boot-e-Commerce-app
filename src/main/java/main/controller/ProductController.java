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
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.dto.ProductDTO;
import main.page_dtos.ProductDTOPage;
import main.service.ProductService;
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
@RequestMapping("/api/products")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    
    @Operation(summary="Retrieve All Products", description="Paginated Retrieval for all products")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of products is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of Product List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
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
                     schema = @Schema(implementation = ProductDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<ProductDTO> findById(@PathVariable Integer id){
        var product = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(product);
       
    }
    
    @Operation(summary="Create a new  Product")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Product is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO x){
        var createdProduct = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
      
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Update Product")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Product isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Product was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProductDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @Valid @RequestBody ProductDTO x){
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
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
      
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> search(
            @RequestParam(required=false) String name,
            @RequestParam(required=false) String desc,
            @RequestParam(required=false) Boolean discountStatus,
            @RequestParam(required=false) String categoryName,
            @RequestParam(required=false) Double minPrice,
            @RequestParam(required=false) Double maxPrice,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size
    ){
        var result = service.search(name, desc, discountStatus, categoryName, minPrice, maxPrice, page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
    @GetMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDTO>> findProductsWithCategoryId(@PathVariable Integer id){
        var list = service.findProductsWithCategoryId(id);
        
            if(list.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
      
    }
}
