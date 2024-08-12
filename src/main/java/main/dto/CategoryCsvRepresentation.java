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
public record CategoryCsvRepresentation(
            @CsvBindByName(column="categoryName")
            String name,
    
            @CsvBindByName(column="categoryDescription")
            String desc
        ) {
    
}
