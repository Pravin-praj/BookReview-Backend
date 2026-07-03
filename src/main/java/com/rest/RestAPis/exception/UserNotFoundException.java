/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.exception;

/**
 *
 * @author Pravin Prajapati
 */
public class UserNotFoundException extends RuntimeException{
    
    public UserNotFoundException(String msg)
    {
        super(msg);
    }
}
