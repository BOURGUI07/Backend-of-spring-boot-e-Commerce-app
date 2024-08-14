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
import lombok.RequiredArgsConstructor;
import main.dto.UserAddressDTO;
import main.page_dtos.UserAddressDTOPage;
import main.service.UserAddressService;
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
@RequestMapping("/api/addresses")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
@Tag(name="User Address", description=" User Address Controller")
public class UserAddressController {
    private final UserAddressService service;
    
    @Operation(summary="Retrieve All user addresses", description="Paginated Retrieval for all user addresses")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of user addresses is empty",
                content=@Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of user addresses List",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserAddressDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserAddressDTO>> findAll(
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
    
    
    @Operation(summary="Get User Address By Id", description="Retrieve a single User Address by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="User Address isn't found",content=@Content),
        @ApiResponse(responseCode="200", description="User Address was successfully Found",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserAddressDTO.class)) }),
        @ApiResponse(responseCode="400", description="ClUser Addressient Entered a Negative id",content=@Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAddressDTO> findById(
            @Parameter(description = "Id of the userAddress to retrieve", required = true)
            @PathVariable Integer id){
        var userAddress = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(userAddress);
 
    }
    
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Create a new  User Address")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="User Address is successfully created",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserAddressDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body",content=@Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<UserAddressDTO> create(
            @Parameter(description = "userAddress to create", required = true)
            @Valid @RequestBody  UserAddressDTO x){
        var userAddress = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(userAddress);
      
    }
    
    @PutMapping("/{id}")
    @Operation(summary="Update User Address")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="User Address isn't found",content=@Content),
        @ApiResponse(responseCode="200", description="User Address was successfully Updated",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserAddressDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body",content=@Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<UserAddressDTO> update(
            @Parameter(description = "Id of the userAddress to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedUserAddress details", required = true)
            @Valid @RequestBody  UserAddressDTO x){
        var updatedUserAddress = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedUserAddress);
       
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete User Address By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="User Address isn't found",content=@Content),
        @ApiResponse(responseCode="204", description="User Address was successfully Deleted",content=@Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id",content=@Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the userAddress to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
      
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<UserAddressDTO> findAddressByUser(@PathVariable Integer id){
        
            return ResponseEntity.status(HttpStatus.OK).body(service.findAddressByUserId(id));
     
    }
}
