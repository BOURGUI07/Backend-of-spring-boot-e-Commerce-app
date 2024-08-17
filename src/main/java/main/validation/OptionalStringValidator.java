/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 *
 * @author hp
 */
@Component
public class OptionalStringValidator implements ConstraintValidator<ValidOptionalString, Optional<String>>{
    private int max;
    @Override
    public void initialize(ValidOptionalString constraintAnnotation) {
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Optional<String> t, ConstraintValidatorContext cvc) {
        return t.map(str -> str.length() <= max).orElse(true);
    }
    
}
