/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.service;

import com.cloudinary.Cloudinary;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Pravin Prajapati
 */
@Service
public class CloudinaryImageImpl implements CloudinaryImageService{

    @Autowired
    private Cloudinary helper;
    
    @Override
    public Map upload(MultipartFile file) {
        try {
            Map data=this.helper.uploader().upload(file.getBytes(), Map.of());
            return data;
        } catch (IOException ex) {
           throw new RuntimeException("image uploading failed");
        }
        
    }
    
}
