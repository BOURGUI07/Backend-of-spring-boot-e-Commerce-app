package com.example.sales_tax_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SalesTaxServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesTaxServiceApplication.class, args);
	}

}
