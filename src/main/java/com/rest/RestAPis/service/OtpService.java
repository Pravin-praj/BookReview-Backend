/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.service;

/**
 *
 * @author Pravin Prajapati
 */

import com.rest.RestAPis.dao.EmailOtpRepository;
import com.rest.RestAPis.entities.EmailOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private EmailOtpRepository otpRepository;

    // Generate 6 digit OTP
    public String generateOtp() {

        Random random = new Random();

        int number = 100000 + random.nextInt(900000);

        return String.valueOf(number);
    }

    // Save OTP
    public void saveOtp(String email, String otp) {

        Optional<EmailOtp> existingOtp = otpRepository.findByEmail(email);

        if (existingOtp.isPresent()) {
            otpRepository.delete(existingOtp.get());
        }

        EmailOtp emailOtp = new EmailOtp();

        emailOtp.setEmail(email);
        emailOtp.setOtp(otp);
        emailOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(emailOtp);
    }

    // Verify OTP
    public boolean verifyOtp(String email, String otp) {

        Optional<EmailOtp> optionalOtp = otpRepository.findByEmail(email);

        if (optionalOtp.isEmpty()) {
            return false;
        }

        EmailOtp emailOtp = optionalOtp.get();

        if (emailOtp.getExpiryTime().isBefore(LocalDateTime.now())) {

            otpRepository.delete(emailOtp);

            return false;
        }

        if (!emailOtp.getOtp().equals(otp)) {

            return false;
        }

        otpRepository.delete(emailOtp);

        return true;
    }

}
