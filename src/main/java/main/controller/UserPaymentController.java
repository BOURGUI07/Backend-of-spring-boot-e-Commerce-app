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
import lombok.RequiredArgsConstructor;
import main.dto.UserPaymentDTO;
import main.page_dtos.UserPaymentDTOPage;
import main.service.UserPaymentService;
import main.util.PaymentProvider;
import main.util.PaymentType;
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
@RequestMapping("/api/user_payments")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
@Tag(name="User Payment", description=" User Payment Controller")
public class UserPaymentController {
    private final UserPaymentService service;
    
    @Operation(summary="Retrieve All user payments", description="Paginated Retrieval for all user payments")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of user payments is empty",content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of user payments List",
                 content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserPaymentDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserPaymentDTO>> findAll(
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
    
    
    @Operation(summary="Get User Payment By Id", description="Retrieve a single User Payment by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="User Payment isn't found",
                content=@Content),
        @ApiResponse(responseCode="200", description="User Payment was successfully Found",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserPaymentDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id",
                content=@Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPaymentDTO> findById(
            @Parameter(description = "Id of the userPayment to retrieve", required = true)
            @PathVariable Integer id){
        var userPayment = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userPayment);
    }
    
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Create a new  User Payment")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="User Payment is successfully created",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserPaymentDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body",
                content=@Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<UserPaymentDTO> create(
            @Parameter(description = "userPayment to create", required = true)
            @Valid @RequestBody  UserPaymentDTO x){
        var userPayment = service.create(x);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(userPayment);
       
    }
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update User Payment")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="User Payment isn't found",content=@Content),
        @ApiResponse(responseCode="200", description="User Payment was successfully Updated",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = UserPaymentDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", content=@Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<UserPaymentDTO> update(
            @Parameter(description = "Id of the userPayment to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedUserPayment details", required = true)
            @Valid @RequestBody UserPaymentDTO x){
        var updatedUserPayment = service.update(id, x);
        
        return ResponseEntity.status(HttpStatus.OK).body(updatedUserPayment);
 
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete User Payment By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="User Payment isn't found",content=@Content),
        @ApiResponse(responseCode="204", description="User Payment was successfully Deleted",content=@Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id",content=@Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the userPayment to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
 
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<UserPaymentDTO> findPaymentByUserId(@PathVariable Integer id){
        
            return ResponseEntity.status(HttpStatus.OK).body(service.findPaymentByUserId(id));
    
        
    }

    @GetMapping("/provider/{providerName}")
    public ResponseEntity<List<UserPaymentDTO>> findPaymentByProvider(@PathVariable PaymentProvider providerName){
        var list = service.findPaymentByProvider(providerName);
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    
    @GetMapping("/type/{typeName}")
    public ResponseEntity<List<UserPaymentDTO>> findPaymentByType(@PathVariable PaymentType typerName){
        var list = service.findPaymentByType(typerName);
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}




