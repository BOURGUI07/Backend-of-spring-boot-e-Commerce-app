/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.sales_tax_service.mapper;

import com.example.sales_tax_service.dto.SalesTaxRequest;
import com.example.sales_tax_service.dto.SalesTaxResponse;
import com.example.sales_tax_service.model.SalesTax;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class SalesTaxMapper {
    public SalesTax toEntity(SalesTaxRequest x){
        return new SalesTax().setCountry(x.country()).setTaxRate(x.taxRate());
    }
    public SalesTaxResponse toDTO(SalesTax s){
        return new SalesTaxResponse(s.getCountry(),s.getTaxRate(),s.getVersion());
    }
}
