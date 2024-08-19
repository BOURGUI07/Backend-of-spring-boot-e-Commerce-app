/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import main.dto.CategoryCsvRepresentation;
import main.dto.ProductCsvRepresentation;
import main.models.Category;
import main.models.Product;
import main.repo.CategoryRepo;
import main.repo.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class CsvUploadService {
      CategoryRepo repo;
      ProductRepo prepo;
    
    
    public Integer uploadCategories(MultipartFile file) throws IOException{
        var categories = parseCategoryCsv(file);
        return repo.saveAll(categories).size();
    }

    private Set<Category> parseCategoryCsv(MultipartFile file) throws IOException {
        try(var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            var strategy = new HeaderColumnNameMappingStrategy<CategoryCsvRepresentation>();
            strategy.setType(CategoryCsvRepresentation.class);
            CsvToBean<CategoryCsvRepresentation> csvToBean = 
                    new CsvToBeanBuilder<CategoryCsvRepresentation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse()
                    .stream()
                    .map(x-> new Category()
                            .setDesc(x.desc())
                            .setName(x.name()))
                    .collect(Collectors.toSet());
                    
              
        }
    }
    
    public Integer uploadProducts(MultipartFile file) throws IOException{
        var products = parseProductCsv(file);
        return prepo.saveAll(products).size();
    }

    private Set<Product> parseProductCsv(MultipartFile file) throws IOException {
        try(var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
             var strategy = new HeaderColumnNameMappingStrategy<ProductCsvRepresentation>();
            strategy.setType(ProductCsvRepresentation.class);
            CsvToBean<ProductCsvRepresentation> csvToBean = 
                    new CsvToBeanBuilder<ProductCsvRepresentation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse()
                    .stream()
                    .map(x-> new Product()
                            .setDesc(x.desc())
                            .setName(x.name())
                            .setPrice(x.price())
                            .setSku(x.sku()))
                    .collect(Collectors.toSet());
        }
    }
}
