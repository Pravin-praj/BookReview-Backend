/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.dao;

import com.rest.RestAPis.entities.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Pravin Prajapati
 */
public interface ReviewRepository extends JpaRepository<Review,Long> {
        
    
    public List<Review> findByUserId(Long id);
    public List<Review> findByBookId(Long id);
    
}
