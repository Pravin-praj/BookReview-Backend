package com.rest.RestAPis.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    public void sendOtp(String toEmail, String otp) {

        try {

            JSONObject body = new JSONObject();

            JSONObject sender = new JSONObject();
            sender.put("name", senderName);
            sender.put("email", senderEmail);

            body.put("sender", sender);

            JSONArray to = new JSONArray();

            JSONObject recipient = new JSONObject();
            recipient.put("email", toEmail);

            to.put(recipient);

            body.put("to", to);

            body.put("subject", "Book Review System - OTP Verification");

            body.put("htmlContent",
                    "<h2>Email Verification</h2>"
                    + "<p>Your OTP is:</p>"
                    + "<h1>" + otp + "</h1>"
                    + "<p>This OTP will expire in 10 minutes.</p>");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .header("api-key", apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code : " + response.statusCode());
            System.out.println("Response : " + response.body());

            if (response.statusCode() == 201) {
                System.out.println("Email sent successfully.");
            } else {
                throw new RuntimeException("Brevo Error : " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}