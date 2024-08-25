/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.sales_tax_service.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author hp
 */
@Aspect
@Component
@Slf4j
public class LoggingAdvice {
    @Pointcut(value="execution(* com.example.sales_tax_service.controller.SalesTaxController.*(..))")
    public void myPointCut(){
        
    }
    
    @Around("myPointCut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable{
        var mapper = new ObjectMapper();
        var methodName = pjp.getSignature().getName();
        var className = pjp.getTarget().getClass().getName();
        var args = pjp.getArgs();
        log.info("""
                 Method invoked {}() in class {} with arguments: {}
                 """,methodName,className,mapper.writeValueAsString(args));
        var object = pjp.proceed();
        log.info("""
                 Method {} in class {} returned: {}
                 """,methodName,className,mapper.writeValueAsString(object));
        return object;
    }
}
