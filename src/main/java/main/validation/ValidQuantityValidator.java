/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import lombok.RequiredArgsConstructor;
import main.exception.EntityNotFoundException;
import main.repo.ProductRepo;

/**
 *
 * @author hp
 */
@RequiredArgsConstructor
class ValidQuantityValidator implements ConstraintValidator<ValidQuantity, Object>{
    private final ProductRepo repo;
    private String productIdField;
    private String quantityField;
    
    @Override
    public void initialize(ValidQuantity constraintAnnotation) {
        this.productIdField = constraintAnnotation.productIdField();
        this.quantityField = constraintAnnotation.quantityField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cvc) {
         try {
            Field productIdAttribute = value.getClass().getDeclaredField(this.productIdField);
            Field quantityAttribute = value.getClass().getDeclaredField(this.quantityField);

            productIdAttribute.setAccessible(true);
            quantityAttribute.setAccessible(true);

            Integer productId = (Integer) productIdAttribute.get(value);
            Integer requestedQuantity = (Integer) quantityAttribute.get(value);

            if (productId == null || requestedQuantity == null) {
                return false;
            }

            var product = repo.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " isn't found"));

            return  requestedQuantity>1 && product != null && requestedQuantity <= product.getInventory().getQuantity();

        } catch (Exception e) {
            return false;
        }
    }
    
}
