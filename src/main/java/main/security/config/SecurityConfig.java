/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.security.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import main.security.service.CustomAccessDeniedHandler;
import main.security.service.CustomBasicAuthEntryPoint;
import main.security.service.JwtAuthenticationFilter;
import main.security.service.UserUsernamePasswordAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author hp
 */
@Configuration
@EnableWebSecurity
@Profile("!production")
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;
    private final UserDetailsService service;
    private final UserUsernamePasswordAuthProvider  provider;
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
                .requestMatchers(HttpMethod.GET, "/api/categories/search").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .anyRequest().authenticated()
        );
        
        http.csrf(x->x.disable());
        
        http.formLogin(withDefaults());
        http.httpBasic(x->x.authenticationEntryPoint(new CustomBasicAuthEntryPoint()));
        http.exceptionHandling(x-> x.accessDeniedHandler(new CustomAccessDeniedHandler()));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(provider);

        return http.build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
   
     
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
