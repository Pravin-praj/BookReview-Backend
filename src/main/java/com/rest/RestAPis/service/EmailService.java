/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.service;

/**
 *
 * @author Pravin Prajapati
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Book Review System - Email Verification");

        message.setText(
                "Hello,\n\n"
                + "Your OTP for email verification is : " + otp
                + "\n\nThis OTP is valid for 5 minutes."
                + "\n\nThank You!"
        );

        mailSender.send(message);
    }
}
