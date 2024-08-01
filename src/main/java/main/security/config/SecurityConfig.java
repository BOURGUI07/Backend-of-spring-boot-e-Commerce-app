/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.security.config;

import main.security.service.CustomAccessDeniedHandler;
import main.security.service.CustomBasicAuthEntryPoint;
import main.util.RoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

/**
 *
 * @author hp
 */
@Configuration
@EnableWebSecurity
@Profile("!production")
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    public CompromisedPasswordChecker passwordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    } 
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.sessionManagement(x-> x.invalidSessionUrl("/invalidSession")
                .maximumSessions(3)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/expiredSession")
        );
        
        http.requiresChannel(x-> x.anyRequest().requiresInsecure());
        
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/invalidSession", "/expiredSession").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/search").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                .anyRequest().authenticated()
        );
        
        http.csrf(x->x.disable());
        
        http.formLogin(withDefaults());
        http.httpBasic(x->x.authenticationEntryPoint(new CustomBasicAuthEntryPoint()));
        http.exceptionHandling(x-> x.accessDeniedHandler(new CustomAccessDeniedHandler()));
        
        return http.build();
    }
}
