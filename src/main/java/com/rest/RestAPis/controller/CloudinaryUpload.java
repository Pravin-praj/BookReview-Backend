/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.controller;

import com.rest.RestAPis.service.CloudinaryImageService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Pravin Prajapati
 */
@RestController
@RequestMapping("/cloudinary/upload")
public class CloudinaryUpload {
    
    @Autowired
    private CloudinaryImageService service;
            
            
    @PostMapping
    public ResponseEntity<Map> uploadImage(@RequestParam("image") MultipartFile file)
    {
        
        Map data=this.service.upload(file);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
    
}
