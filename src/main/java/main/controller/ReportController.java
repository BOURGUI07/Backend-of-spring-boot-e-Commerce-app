/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import main.service.ReportService;
import main.util.PaymentProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;
    
    @Operation(summary="Retrieve all products in a specific category")
    @GetMapping("/product_by_category_name/{category_name}")
    public ResponseEntity<String> exportReport1(@PathVariable String category_name){
        var filepath = "C:\\Users\\hp\\Documents\\NetBeansProjects\\eComMaster\\product_by_category_name.csv";
        service.exportProductsByCategoryNameReport(filepath, category_name);
        return ResponseEntity.ok("Report Generated and Saved to: " + filepath);
    }
    
    
    @Operation(summary="Retrieve all products in a specific category")
    @GetMapping("/active_discount_products")
    public ResponseEntity<String> exportReport2(){
        var filepath = "C:\\Users\\hp\\Documents\\NetBeansProjects\\eComMaster\\active_discount_products.csv";
        service.exportProductsByActiveDiscount(filepath);
        return ResponseEntity.ok("Report Generated and Saved to: " + filepath);
    }
    
    @Operation(summary="Find all products with reviews above a certain rating")
    @GetMapping("/products_by_rating_above/{rating}")
    public ResponseEntity<String> exportReport3(@PathVariable Integer rating){
        var filepath = "C:\\Users\\hp\\Documents\\NetBeansProjects\\eComMaster\\products_by_rating_above.csv";
        service.exportProductsByRatingAboveReport(rating, filepath);
        return ResponseEntity.ok("Report Generated and Saved to: " + filepath);
    }
    
    @GetMapping("/out_of_stock_products")
    @Operation(summary="List all products that are out of stock")
    public ResponseEntity<String> exportReport4(){
        var filepath = "C:\\Users\\hp\\Documents\\NetBeansProjects\\eComMaster\\out_of_stock_products.csv";
        service.exportOutOfStockProductsReport(filepath);
        return ResponseEntity.ok("Report Generated and Saved to: " + filepath);
    }
    
    @Operation(summary="Find all orders that include products from a specific category")
    @GetMapping("/orders_with_category_name/{category_name}")
    public ResponseEntity<String> exportReport5(@PathVariable String category_name){
        var filepath = "C:\\Users\\hp\\Documents\\NetBeansProjects\\eComMaster\\orders_with_category_name.csv";
        service.exportOrdersWhoseProductsOfCategoryReport(filepath, category_name);
        return ResponseEntity.ok("Report Generated and Saved to: " + filepath);
    }
    
    
    @Operation(summary="List all users who have used a specific payment provider")
    @GetMapping("/users_who_used_provider/{provider}")
    public ResponseEntity<String> exportReport56(@PathVariable PaymentProvider provider){
        var filepath = "C:\\Users\\hp\\Documents\\NetBeansProjects\\eComMaster\\users_who_used_provider.csv";
        service.exportUsersWhoUsedProviderReport(provider, filepath);
        return ResponseEntity.ok("Report Generated and Saved to: " + filepath);
    }
    
    @Operation(summary="Retrieve all reviews written by a user for products in a specific category")
    @GetMapping("/reviews_for_category_name/{category_name}")
    public ResponseEntity<String> exportReport7(@PathVariable String category_name){
        var filepath = "C:\\Users\\hp\\Documents\\NetBeansProjects\\eComMaster\\reviews_for_category_name.csv";
        service.exportReviewsForSpecificCategoryReport(category_name, filepath);
        return ResponseEntity.ok("Report Generated and Saved to: " + filepath);
    }
    
    @Operation(summary="Retrieve all reviews written by a user for products in a specific category")
    @GetMapping("/avg_rating_for_product/{productName}")
    public ResponseEntity<Double> avgProductRating(@PathVariable String productName){        
        return ResponseEntity.ok(service.avgRatingOfProduct(productName));
    }
    
    @Operation(summary="Retrieve all reviews written by a user for products in a specific category")
    @GetMapping("/total_cart_qty_for_user/{userId}")
    public ResponseEntity<Integer> totalCartQtyByUser(@PathVariable Integer userId){        
        return ResponseEntity.ok(service.findTotalCartQtyByUser(userId));
    }
}
