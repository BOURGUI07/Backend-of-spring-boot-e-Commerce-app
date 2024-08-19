/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.sales_tax_service.page_dtos;

import com.example.sales_tax_service.dto.SalesTaxResponse;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author hp
 */
public class SalesTaxDTOPage extends PageImpl<SalesTaxResponse>{
    public SalesTaxDTOPage(List<SalesTaxResponse> content, int page, int size, long total) {
        super(content, PageRequest.of(page, size), total);
    }

    public SalesTaxDTOPage(List<SalesTaxResponse> content) {
        super(content);
    }
}
