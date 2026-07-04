/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.controller;

import com.rest.RestAPis.dao.UserRepository;
import com.rest.RestAPis.dto.LoginRequest;
import com.rest.RestAPis.dto.LoginResponse;
import com.rest.RestAPis.entities.User;
import com.rest.RestAPis.service.AuthService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pravin Prajapati
 */
@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService service;
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getALL()
    {
        List<User> list=service.GetALL();
        if(list.size()==0)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else
        {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id)
    {
        User u=service.getuserById(id);
        if(u==null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(u);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        user.setRole("user");
        User u = repo.save(user);

        if (u == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(u);
    }

    
@PostMapping("/login")
public ResponseEntity<LoginResponse> login(
       @Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(service.login(request));
}
}
