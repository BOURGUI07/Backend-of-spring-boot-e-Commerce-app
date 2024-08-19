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
import main.dto.SessionResponseDTO;
import main.dto.UserShoppingSessionDTO;
import main.page_dtos.SessionResponseDTOPage;
import main.service.SessionService;
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
@RequestMapping("/api/sessions")
@Validated
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
@Tag(name="User Shopping Session", description=" Session Controller")
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class SessionController {
      SessionService service;
    
    @Operation(summary="Retrieve All user shopping sessions", description="Paginated Retrieval for all user shopping sessions")
    @ApiResponses(value={
        @ApiResponse(responseCode="204", description="List of user shopping sessions is empty", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="Successfull Retrieval of user shopping sessions List",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = SessionResponseDTOPage.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SessionResponseDTO>> findAll(
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
    
    
    @Operation(summary="Get shopping session By Id", description="Retrieve a single shopping session by Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="shopping session isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="shopping session was successfully Found",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = SessionResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionResponseDTO> findById(
            @Parameter(description = "Id of the shopping session to retrieve", required = true)
            @PathVariable Integer id){
        var session = service.findById(id);
        
            return ResponseEntity.status(HttpStatus.OK).body(session);
     
    }
    
    @PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Create a new  User Shopping Session")
    @ApiResponses(value={
        @ApiResponse(responseCode="201", description="User Shopping Session is successfully created",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = SessionResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<SessionResponseDTO> create(
            @Parameter(description = "userShoppingSession to create", required = true)
            @Valid @RequestBody UserShoppingSessionDTO x){
        var userShoppingSession = service.create(x);
        
            return ResponseEntity.status(HttpStatus.CREATED).body(userShoppingSession);
       
    }
    
    @PutMapping(value="/{id}",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Update shopping session")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="shopping session isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="200", description="shopping session was successfully Updated",
                content = { @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = SessionResponseDTO.class)) }),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id Or "
                + "a Non Valid Entity Body", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<SessionResponseDTO> update(
            @Parameter(description = "Id of the userShoppingSession to update", required = true)
            @PathVariable Integer id,
            @Parameter(description = "updatedSession details", required = true)
            @Valid @RequestBody  UserShoppingSessionDTO x){
        var updatedSession = service.update(id, x);
        
            return ResponseEntity.status(HttpStatus.OK).body(updatedSession);
      
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary="Delete shopping session By Id")
    @ApiResponses(value={
        @ApiResponse(responseCode="404", description="shopping session isn't found", 
                     content = @Content),
        @ApiResponse(responseCode="204", description="shopping session was successfully Deleted", 
                     content = @Content),
        @ApiResponse(responseCode="400", description="Client Entered a Negative id", 
                     content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id of the userShoppingSession to delete", required = true)
            @PathVariable Integer id){
        
            service.delete(id);
            return ResponseEntity.noContent().build();
        
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<SessionResponseDTO> findAddressByUser(@PathVariable Integer id){
       
            return ResponseEntity.status(HttpStatus.OK).body(service.getSessionByUserId(id));
     
    }
}
