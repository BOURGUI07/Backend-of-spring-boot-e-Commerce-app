/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import main.dto.ReviewsRequestDTO;
import main.dto.ReviewsResponseDTO;
import main.dto.ReviewsUpdateRequestDTO;
import main.exception.EntityNotFoundException;
import main.repo.ReviewsRepo;
import main.util.mapper.ReviewsMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
@RequiredArgsConstructor
@Setter
public class ReviewsService {
    private final ReviewsRepo repo;
    private final ReviewsMapper mapper;
    private Validator validator;
    
    
    @Cacheable(value="allReviewsOf_a_Product", key = "'findAll_' + #page + '_' + #size + '_' + #productId")
    public Page<ReviewsResponseDTO> findAll(int page, int size, Integer productId){
        var pageable = PageRequest.of(page, size);
        return repo.findByProductId(pageable, productId).map(mapper::toDTO);
    }
    
    
    @Cacheable(value="reviewById", key="#id")
    public ReviewsResponseDTO findById(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var review = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review with id: " + id + " isn't found"));
        return mapper.toDTO(review);
    }
    
    
    @Transactional
    @CacheEvict(value={
        "allReviewsOf_a_Product", "reviewById"
    }, allEntries=true)
    public ReviewsResponseDTO create(ReviewsRequestDTO x){
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var review = mapper.toEntity(x);
        var savedReview = repo.save(review);
        return mapper.toDTO(savedReview);
    }
    
    
    
    @Transactional
    @CacheEvict(value={
        "allReviewsOf_a_Product", "reviewById"
    }, allEntries=true)
    public ReviewsResponseDTO update(Integer id, ReviewsUpdateRequestDTO x){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var violations = validator.validate(x);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
        var review = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review with id: " + id + " isn't found"));
        review.setRating(x.rating());
        review.setTitle(x.title());
        review.setContent(x.content());
        var savedReview = repo.save(review);
        return mapper.toDTO(savedReview);
    }
    
    
    @Transactional
    @CacheEvict(value={
        "allReviewsOf_a_Product", "reviewById"
    }, allEntries=true)
    public void delete(Integer id){
        if(id<=0){
            throw new IllegalArgumentException("id must be positive");
        }
        var review = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review with id: " + id + " isn't found"));
        repo.delete(review);
    }
    
    @CacheEvict(value={
        "allProducts", "productById"
    }, allEntries=true)
    public void clearCache(){
        
    }
}
