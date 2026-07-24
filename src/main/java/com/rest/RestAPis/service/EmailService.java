/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.service;

/**
 *
 * @author Pravin Prajapati
 */
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    
    @Value("${spring.mail.host}")
private String host;

@Value("${spring.mail.port}")
private String port;

@Value("${spring.mail.username}")
private String username;

@PostConstruct
public void checkMailConfig() {
    System.out.println("HOST = " + host);
    System.out.println("PORT = " + port);
    System.out.println("USERNAME = " + username);
}
    
    
    
   public void sendOtp(String toEmail, String otp) {

    try {

        System.out.println("Sending OTP to: " + toEmail);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Book Review System - Email Verification");
        message.setText("Your OTP is: " + otp);

        System.out.println("Before mailSender.send()");

        mailSender.send(message);

        System.out.println("After mailSender.send()");
        System.out.println("Email sent successfully.");

    } catch (Exception e) {
        System.out.println("EMAIL ERROR");
        e.printStackTrace();
    }
}
}