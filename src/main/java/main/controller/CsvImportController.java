/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import main.service.CsvUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api/upload_csv_file")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
@Tag(name="CSV file upload", description=" uploading csv files of product and category data")
public class CsvImportController {
    private final CsvUploadService service;
    
    
    @PostMapping(value="/categories",consumes={"multipart/form-data"})
    public ResponseEntity<Integer> uploadCategories(
            @Parameter(description = "csv file to upload category data from", required = true)
            @RequestPart("file") MultipartFile file
    ) throws IOException{
        return ResponseEntity.ok(service.uploadCategories(file));
    }
    
    @PostMapping(value="/products",consumes={"multipart/form-data"})
    public ResponseEntity<Integer> uploadProducts(
            @Parameter(description = "csv file to upload product data from", required = true)
            @RequestPart("file") MultipartFile file
    ) throws IOException{
        return ResponseEntity.ok(service.uploadProducts(file));
    }
}
