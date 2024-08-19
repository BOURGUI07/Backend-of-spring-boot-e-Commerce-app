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
import main.dto.PaymentDetailDTO;
import main.dto.PaymentDetailResponseDTO;
import main.page_dtos.PaymentDetailResponseDTOPage;
import main.service.PaymentDetailService;
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
@RequestMapping("/api/payment_details")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
@Tag(name="Payment Detail", description=" Payment Detail Controller")
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class PaymentDetailController {
      PaymentDetailService service;
    
    @Operation(summary="Retrieve All payment details", description="Paginated Retrieval for all payment details")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of payment details is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of payment details List",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = PaymentDetailResponseDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PaymentDetailResponseDTO>> findAll(
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
    
    
    @Operation(summary="Get Payment Detail By Id", description="Retrieve a single Payment Detail by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="Payment Detail isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Payment Detail was successfully Found",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = PaymentDetailResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDetailResponseDTO> findById(
            @Parameter(description = "Id of the paymentDetail to retrieve", required = true)
            @PathVariable Integer id){
        var paymentDetail = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(paymentDetail);
        
    }
    
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Create a new  Payment Detail")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="Payment Detail is successfully created",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = PaymentDetailResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<PaymentDetailResponseDTO> create(
            @Parameter(description = "paymentDetail to create", required = true)
            @Valid @RequestBody  PaymentDetailDTO x){
        var paymentDetail = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(paymentDetail);
       
    }
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update payment detail")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="payment detail isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="payment detail was successfully Updated",content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = PaymentDetailResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<PaymentDetailResponseDTO> update(
            @Parameter(description = "Id of the paymentDetail to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedPaymentDetail details", required = true)
            @Valid @RequestBody  PaymentDetailDTO x){
        var updatedPaymentDetail = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedPaymentDetail);
        
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete payment detail By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="payment detail isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="payment detail was successfully Deleted", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the paymentDetail to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
       
    }
}
