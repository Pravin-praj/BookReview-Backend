/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.controller;

import com.rest.RestAPis.dto.BookRequest;
import com.rest.RestAPis.entities.Book;
import com.rest.RestAPis.service.BookService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Pravin Prajapati
 */
@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/me")
    public String getName(Authentication auth) {
        return auth.getName();
    }

    @GetMapping("/books")
    public ResponseEntity<Page<Book>> getBooks( 
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        Page<Book> list = service.getBooks(page,size);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }

     @GetMapping("/total")
    public ResponseEntity<Integer> getTotalBook()
    {
        int data =service.getTotalBook();
        return ResponseEntity.ok(data);
    }
    
    
    @PostMapping("/save")
 public ResponseEntity<Book> addBook(
            @RequestParam String bookName,
            @RequestParam double price,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String language,
            @RequestParam MultipartFile image) {

        Book book = service.addBook(
                bookName,
                price,
                firstName,
                lastName,
                language,
                image);

        return ResponseEntity.ok(book);
//System.out.println("bookName = " + bookName);
//    System.out.println("price = " + price);
//    System.out.println("firstName = " + firstName);
//    System.out.println("lastName = " + lastName);
//    System.out.println("language = " + language);
//    System.out.println("image = " + (image != null ? image.getOriginalFilename() : null));

  
    }


    @GetMapping("/books/{id}")
    public ResponseEntity<Optional<Book>> getBookbyId(@PathVariable int id) {

        Optional<Book> b = service.findById(id);
        if (b.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(b);
    }

   @PutMapping("/update/{id}")
public ResponseEntity<Book> updateBook(
        @PathVariable int id,
        @RequestParam String bookName,
        @RequestParam double price,
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String language,
        @RequestParam(required = false) MultipartFile image) {

    Book book = service.updateData(
            id,
            bookName,
            price,
            firstName,
            lastName,
            language,
            image);

    return ResponseEntity.ok(book);
}

    @DeleteMapping("/delete/{id}")
    public void deleteData(@PathVariable int id)
    {
        service.delete(id);
      
    }
    
    
    
    
    
    
}
