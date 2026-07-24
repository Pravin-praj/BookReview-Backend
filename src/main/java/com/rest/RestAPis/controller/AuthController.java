/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.controller;

import com.rest.RestAPis.dao.UserRepository;
import com.rest.RestAPis.dto.ForgotPasswordRequest;
import com.rest.RestAPis.dto.LoginRequest;
import com.rest.RestAPis.dto.LoginResponse;
import com.rest.RestAPis.dto.OtpRequest;
import com.rest.RestAPis.dto.ResetPasswordRequest;
import com.rest.RestAPis.entities.User;
import com.rest.RestAPis.service.AuthService;
import com.rest.RestAPis.service.EmailService;
import com.rest.RestAPis.service.OtpService;
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
     @Autowired
    private OtpService otpService;
 @Autowired
    private EmailService emailService;

     
     
     
     
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
public ResponseEntity<?> signup(@RequestBody User user) {

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("USER");
    user.setEnabled(false);

    User savedUser = repo.save(user);

    String otp = otpService.generateOtp();

    otpService.saveOtp(savedUser.getEmail(), otp);

    emailService.sendOtp(savedUser.getEmail(), otp);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body("Registration successful. Please verify your email using the OTP sent to your email.");
}
 


@PostMapping("/forgot-password")
public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {

    User user = repo.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    String otp = otpService.generateOtp();

    otpService.saveOtp(user.getEmail(), otp);

    emailService.sendOtp(user.getEmail(), otp);

    return ResponseEntity.ok("OTP sent successfully.");

}


@PostMapping("/verify-otp")
public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {

    boolean verified = otpService.verifyOtp(
            request.getEmail(),
            request.getOtp()
    );

    if (!verified) {

        return ResponseEntity
                .badRequest()
                .body("Invalid or Expired OTP");

    }

    User user = repo.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new RuntimeException("User Not Found"));

    user.setEnabled(true);

    repo.save(user);

    return ResponseEntity.ok("Email Verified Successfully");

}


@PostMapping("/reset-password")
public ResponseEntity<?> resetPassword(
        @RequestBody ResetPasswordRequest request) {

    boolean verified = otpService.verifyOtp(
            request.getEmail(),
            request.getOtp()
    );

    if (!verified) {

        return ResponseEntity.badRequest()
                .body("Invalid or Expired OTP");

    }

    User user = repo.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    user.setPassword(
            passwordEncoder.encode(request.getNewPassword())
    );

    repo.save(user);

    return ResponseEntity.ok("Password changed successfully.");

}


@PostMapping("/login")
public ResponseEntity<LoginResponse> login(
       @Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(service.login(request));
}
}
