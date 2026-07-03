/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.dao;

import com.rest.RestAPis.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Pravin Prajapati
 */

public interface BookRepository extends JpaRepository<Book, Integer>{

     
    
}
