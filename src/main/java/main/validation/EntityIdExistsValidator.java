/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import main.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntityIdExistsValidator implements ConstraintValidator<EntityIdExists, Integer> {

    @Autowired
    private ApplicationContext applicationContext;

    private Class<?> entityClass;

    @Override
    public void initialize(EntityIdExists constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        if (id == null || id<1) {
            return false; 
        }

        var repository = getRepository(entityClass);

        if (repository == null) {
            throw new EntityNotFoundException("No repository found for entity: " + entityClass.getSimpleName());
        }

        return repository.existsById(id);
    }

    private JpaRepository<?, Integer> getRepository(Class<?> entityClass) {
        String repositoryName = entityClass.getSimpleName() + "Repo";
        return (JpaRepository<?, Integer>) applicationContext.getBean(repositoryName);
    }
}

