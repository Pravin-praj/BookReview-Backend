/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.rest.RestAPis.service;

import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Pravin Prajapati
 */
public interface CloudinaryImageService {
    
    public Map upload(MultipartFile file);
}
