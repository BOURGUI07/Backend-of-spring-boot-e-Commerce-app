/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.page_dtos;

import java.util.List;
import main.dto.CartItemDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author hp
 */
public class CartItemDTOPage extends PageImpl<CartItemDTO>{
    public CartItemDTOPage(List<CartItemDTO> content, int page, int size, long total) {
        super(content, PageRequest.of(page, size), total);
    }

    public CartItemDTOPage(List<CartItemDTO> content) {
        super(content);
    }
}
