/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.controller;

import com.rest.RestAPis.dao.BookRepository;
import com.rest.RestAPis.dao.ReviewRepository;
import com.rest.RestAPis.dao.UserRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pravin Prajapati
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    
@Autowired
private BookRepository book;

@Autowired
private ReviewRepository review;


@Autowired
private UserRepository user;

@GetMapping("/dashboard")
public Map<String,Long> getTotals()
{
    Map<String,Long> data = new HashMap<>();
    data.put("totalUser",user.count());
    data.put("totalBook",book.count());
    data.put("totalReview", review.count());
    return data;
}



}

