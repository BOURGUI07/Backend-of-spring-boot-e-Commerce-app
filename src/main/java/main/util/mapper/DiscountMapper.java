/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import main.dto.DiscountResponseDTO;
import main.dto.DiscountRequestDTO;
import main.models.Discount;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
@Service
public class DiscountMapper {
    
    public Discount toEntity(DiscountRequestDTO x){
        var d = new Discount()
        .setName(x.name())
        .setPercent(x.percent());
        x.active().ifPresent(d::setActive);
        x.desc().ifPresent(d::setDesc);
        return d;
    }
    
    public DiscountResponseDTO toDTO(Discount d){
        var list = d.getProducts().stream().map(x -> x.getId()).collect(Collectors.toList());
        return new DiscountResponseDTO(d.getId(),d.getName(),d.getDesc(),d.getPercent(),d.getActive(),list,d.getVersion());
    }
}
