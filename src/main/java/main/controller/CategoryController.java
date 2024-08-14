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
import main.dto.CategoryDTO;
import main.page_dtos.CategoryDTOPage;
import main.service.CategoryService;
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
@RequestMapping("/api/categories")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@Tag(name="Category", description=" Category Controller")
public class CategoryController {
    private final CategoryService service;
    
    @Operation(summary="Retrieve All categories", description="Paginated Retrieval for all categories")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of categories is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of category List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CategoryDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CategoryDTO>> findAll(
            @RequestParam(defaultValue="0")int page,
            @RequestParam (defaultValue="10")int size){
        var result = service.findAll(page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(result);
    }
    
    
    
    @Operation(summary="Get Category By Id", description="Retrieve a single Categoryby Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Category isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Category was successfully Found",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CategoryDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Integer id){
        var product = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(product);
       
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Create a new  Category")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Category is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CategoryDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody  CategoryDTO x){
        var createdProduct = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
       
    }
    
    @PutMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update category")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="category isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="category was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = CategoryDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<CategoryDTO> update(@PathVariable Integer id, @Valid @RequestBody  CategoryDTO x){
        var updatedProduct = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete category By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="category isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="category was successfully Deleted", 
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
    
    @GetMapping(value="/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CategoryDTO>> search(
            @RequestParam(required=false) String name,
            @RequestParam(required=false) String desc,
            @RequestParam(required=false) String productName,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size
    ){
        var result = service.search(name, desc, productName, page, size);
        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
