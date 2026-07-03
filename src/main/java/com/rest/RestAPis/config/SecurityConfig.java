/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.config;

import com.rest.RestAPis.helper.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Pravin Prajapati
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    
    @Autowired
private JwtFilter jwtFilter;
    
    @Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http) throws Exception {

    return http
            .cors(cors->{})
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**","/cloudinary/**")
                .permitAll()
                .requestMatchers("/swagger-ui/**","/v3/api-docs/**")
                .permitAll()
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/book/**")
                .hasAnyRole("USER","ADMIN")
                .requestMatchers("/review/**")
                .hasAnyRole("USER","ADMIN")
                .anyRequest()
                .authenticated()
        )
        .sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
        .addFilterBefore(
                jwtFilter,
                    UsernamePasswordAuthenticationFilter.class)
        .build();
}
    
    @Bean
public AuthenticationManager authenticationManager(
        AuthenticationConfiguration config)
        throws Exception {

    return config.getAuthenticationManager();
}
    
    @Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}
