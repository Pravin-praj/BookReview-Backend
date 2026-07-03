/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.dao;

import com.rest.RestAPis.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Pravin Prajapati
 */
public interface UserRepository extends JpaRepository<User,Long>{
  
     Optional<User> findByEmail(String email);
    
}
