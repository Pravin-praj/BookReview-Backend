/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.rest.RestAPis.service;

import com.rest.RestAPis.dao.UserRepository;
import com.rest.RestAPis.dto.LoginRequest;
import com.rest.RestAPis.dto.LoginResponse;
import com.rest.RestAPis.entities.User;
import com.rest.RestAPis.exception.UserNotFoundException;
import com.rest.RestAPis.helper.JwtUtil;
import java.util.List;
import java.util.Optional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pravin Prajapati
 */
@Service

public class AuthService
{
    
 @Autowired   
 private JwtUtil jwtUtil;
@Autowired    
private UserRepository repo;    
@Autowired 
private PasswordEncoder passwordEncoder;
@Autowired
private AuthenticationManager authenticationManager;



public List<User> GetALL()
{
    return repo.findAll();
}

public int getCountUser()
{
    return (int) repo.count();
}


public User getuserById(long id)
{
    return repo.findById(id).orElseThrow(()->new UserNotFoundException("User Not Found"));
}

public LoginResponse login(LoginRequest request) {

    authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(
    request.getEmail(),
    request.getPassword()
    ));
    
    
    User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() ->
                    new RuntimeException("User not found"));
    
    if (!user.isEnabled()) {
    throw new RuntimeException("Please verify your email before logging in.");
}
    

String token = jwtUtil.generateToken(
        user.getEmail(),
        user.getRole()
);
    String role=user.getRole();
     Long id=user.getId();
    System.out.println("Token Generated");

    return new LoginResponse(token,user.getRole(),id); 
}
}
