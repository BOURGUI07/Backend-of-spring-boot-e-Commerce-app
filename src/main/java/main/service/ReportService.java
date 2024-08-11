/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import lombok.RequiredArgsConstructor;
import main.util.PaymentProvider;
import main.util.csv.CsvReportUtil;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class ReportService {
    private final GlobalService globalService;
    private final CsvReportUtil csvService;
    
    public void exportProductsByCategoryNameReport(String filepath, String categoryName){
        var report = globalService.findProductsByCategoryName(categoryName);
        csvService.writeProductToCsv(report, filepath);
    }
    
    public void exportProductsByActiveDiscount(String filepath){
        var report = globalService.findProductsByActiveDiscount();
        csvService.writeProductDiscountToCsv(report, filepath);
    }
    
    public void exportProductsByRatingAboveReport(Integer rating, String filepath){
        if(rating<1 || rating>5){
            throw new IllegalArgumentException("Rating should be between 1 and 5");
        }
        var report = globalService.findProductsByRatingAbove(rating);
        csvService.writeProductToCsv(report, filepath);
    }
    
    public void exportOutOfStockProductsReport(String filepath){
        var report = globalService.findOutOfStockProducts();
        csvService.writeProductToCsv(report, filepath);
    }
    
    public Double avgRatingOfProduct(String productName){
        return globalService.findAvgRatingForProduct(productName);
    }
    
    public Integer findTotalCartQtyByUser(Integer userId){
        return globalService.findTotalCartQtyByUser(userId);
    }
    
    public void exportReviewsForSpecificCategoryReport(String categoryName,String filepath){
        var report = globalService.findReviewsForSpecificCategory(categoryName);
        csvService.writeReviewsToCsv(report, filepath);
    }
    
    public void exportUsersWhoUsedProviderReport(PaymentProvider p,String filepath){
        var report = globalService.findUsersWhoUsedProvider(p);
        csvService.writeUserIdNameToCsv(report, filepath);
    }
    
    public void exportOrdersWhoseProductsOfCategoryReport(String categoryName,String filepath){
        var report = globalService.findOrdersWhoseProductsOfCategory(categoryName);
        csvService.writeOrderProductToCsv(report, filepath);
    }
    
    public void exportOrdersAmountsByUserReport(Integer userId,String filepath){
        var report = globalService.findOrdersAmountsByUser(userId);
        csvService.writeOrderQtyToCsv(report, filepath);
    }
    
    
}
