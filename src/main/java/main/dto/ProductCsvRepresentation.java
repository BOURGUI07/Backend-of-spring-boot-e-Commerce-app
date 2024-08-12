/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import com.opencsv.bean.CsvBindByName;

/**
 *
 * @author hp
 */

public record ProductCsvRepresentation(
        @CsvBindByName(column="productName")
         String name,
        @CsvBindByName(column="productDescription")
         String desc,
        @CsvBindByName(column="productSku")
         String sku,
        @CsvBindByName(column="productPrice")
         Double price
        ) {
    
}
