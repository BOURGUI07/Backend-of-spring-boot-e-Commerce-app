/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import main.exception.EntityNotFoundException;
import main.repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

public class ValidIdValidator implements ConstraintValidator<ValidId, Integer> {

    public ValidIdValidator() {
    }
    
    
    @Override
    public void initialize(ValidId constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        if (id == null) {
            return false;
        }
        
        return id >= 1;
    }

}



