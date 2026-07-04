/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.service;

import com.rest.RestAPis.dao.AuthorRepository;
import com.rest.RestAPis.dao.BookRepository;
import com.rest.RestAPis.dto.BookRequest;
import com.rest.RestAPis.entities.Author;
import com.rest.RestAPis.entities.Book;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Pravin Prajapati
 */
@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private CloudinaryImageService cloudinaryService;

    public Page<Book> getBooks(int page,int size) {
        Pageable pageble=PageRequest.of(page,size);
        
        return repo.findAll(pageble);
    }

    
    public int getTotalBook()
    {
        return (int) repo.count();
    }
    
    
public Book addBook(
            String bookName,
            double price,
            String firstName,
            String lastName,
            String language,
            MultipartFile image) {

        Map data = cloudinaryService.upload(image);

        String imageUrl =
                data.get("secure_url").toString();

        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setLanguage(language);

        Book book = new Book();
        book.setBookName(bookName);
        book.setPrice(price);
        book.setAuthor(author);
        book.setImageUrl(imageUrl);

        return repo.save(book);
    }


    public Optional<Book> findById(int id) {
        return repo.findById(id);
    }

   public Book updateData(
        int id,
        String bookName,
        double price,
        String firstName,
        String lastName,
        String language,
        MultipartFile image) {

   Book book = repo.findById(id).orElseThrow();

book.setBookName(bookName);
book.setPrice(price);

book.getAuthor().setFirstName(firstName);
book.getAuthor().setLastName(lastName);
book.getAuthor().setLanguage(language);

if(image != null && !image.isEmpty()){
    Map data = cloudinaryService.upload(image);
    book.setImageUrl(data.get("secure_url").toString());
}

return repo.save(book);
}
   
   
   
    public void delete(int id) {
        repo.deleteById(id);
    }

//    public Book uploadBookImage(
//            int bookId,
//            MultipartFile file) {
//
//        Book book = repo.findById(bookId)
//                .orElseThrow(()
//                        -> new RuntimeException("Book not found"));
//
//        Map data = cloudinaryService.upload(file);
//
//        String imageUrl
//                = data.get("secure_url").toString();
//
//        book.setImageUrl(imageUrl);
//
//        return repo.save(book);
//    }
}
