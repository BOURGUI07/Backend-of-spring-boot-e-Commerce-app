/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.mapper;

import lombok.RequiredArgsConstructor;
import main.dto.ReviewsRequestDTO;
import main.dto.ReviewsResponseDTO;
import main.models.Reviews;
import main.repo.ProductRepo;
import main.repo.UserRepo;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
public class ReviewsMapper {
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    
    public Reviews toEntity(ReviewsRequestDTO x){
        var r = new Reviews()
        .setRating(x.rating());
        x.content().ifPresent(r::setContent);
        x.title().ifPresent(r::setTitle);
         userRepo.findById(x.userId()).ifPresent(u->{
            r.setUser(u);
            userRepo.save(u);
        });
        productRepo.findById(x.productId()).ifPresent(p->{
            r.setProduct(p);
            productRepo.save(p);
        });
        return r;
    }
    
    public ReviewsResponseDTO toDTO(Reviews r){
        return new ReviewsResponseDTO(
                r.getId(),
                r.getUser().getUsername(),
                r.getProduct().getName(),
                r.getRating(),
                r.getTitle(),
                r.getContent());
    }
}
