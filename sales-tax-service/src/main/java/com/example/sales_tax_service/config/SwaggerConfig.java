/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.sales_tax_service.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author hp
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi(){
        Contact contact = new Contact();
	contact.setEmail("younessbourgui07@gmail.com");
	contact.setName("Youness Bourgui");
	contact.setUrl("https://my-awesome-api.com");

	Server localServer = new Server();
	localServer.setUrl("http://localhost:9090");
	localServer.setDescription("Server URL in Local environment");

	Server productionServer = new Server();
	productionServer.setUrl("https://my-awesome-api.com");
	productionServer.setDescription("Server URL in Production environment");
		
	License mitLicense = new License()
			.name("MIT License")
			.url("https://choosealicense.com/licenses/mit/");

	Info info = new Info()
			.title("sales tax API")
			.contact(contact)
			.version("1.0")
			.description("This API exposes endpoints for sales tax api")
			.termsOfService("https://my-awesome-api.com/terms")
			.license(mitLicense);

	return new OpenAPI()
			.info(info)
			.servers(List.of(localServer, productionServer));
    }
}
