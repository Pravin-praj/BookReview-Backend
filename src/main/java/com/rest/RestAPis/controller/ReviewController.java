package com.rest.RestAPis.controller;

import com.rest.RestAPis.dao.ReviewRepository;
import com.rest.RestAPis.dto.ReviewRequest;
import com.rest.RestAPis.dto.ReviewResponse;
import com.rest.RestAPis.service.ReviewService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService service;
    
    @Autowired
    private ReviewRepository repo;
    

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        return ResponseEntity.ok(service.getAllReviews());
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponse> getOneReview(@PathVariable Long id) {
        return ResponseEntity.ok(service.getReviewById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<ReviewResponse> postData(
            @RequestBody ReviewRequest request) {

        return ResponseEntity.ok(service.addReview(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReviewResponse> updateData(
            @RequestBody ReviewRequest request,
            @PathVariable Long id) {

        return ResponseEntity.ok(service.updateReview(id,request));
    }

    @GetMapping("/myreview/{userId}")
    public ResponseEntity<List<ReviewResponse>> findbyUserId(
            @PathVariable Long userId) {
        
        return ResponseEntity.ok(service.getReviewsByUserId(userId));
    }
    
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReviewResponse>> findbyBookId(
            @PathVariable Long bookId) {
        
        return ResponseEntity.ok(service.getReviewsByBookId(bookId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        service.deleteReview(id);

        return ResponseEntity.ok("Review Deleted Successfully");
    }
}