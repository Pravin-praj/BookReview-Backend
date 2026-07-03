package com.rest.RestAPis.service;

import com.rest.RestAPis.dao.BookRepository;
import com.rest.RestAPis.dao.ReviewRepository;
import com.rest.RestAPis.dao.UserRepository;
import com.rest.RestAPis.dto.ReviewRequest;
import com.rest.RestAPis.dto.ReviewResponse;
import com.rest.RestAPis.entities.Book;
import com.rest.RestAPis.entities.Review;
import com.rest.RestAPis.entities.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookRepository bookRepo;

    public ReviewResponse addReview(ReviewRequest request) {

        User user = userRepo.findById(request.getUserId()).orElseThrow();
        Book book = bookRepo.findById(request.getBookId()).orElseThrow();

        Review review = new Review();
        review.setRating(request.getRating());
        review.setComments(request.getComments());
        review.setUser(user);
        review.setBook(book);

        Review saved = reviewRepo.save(review);

        return convertToResponse(saved);
    }

    public List<ReviewResponse> getAllReviews() {

        return reviewRepo.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ReviewResponse getReviewById(Long id) {

        Review review = reviewRepo.findById(id).orElseThrow();

        return convertToResponse(review);
    }

    public ReviewResponse updateReview(Long id, ReviewRequest request) {

        Review review = reviewRepo.findById(id).orElseThrow();

        review.setRating(request.getRating());
        review.setComments(request.getComments());

        Review updated = reviewRepo.save(review);

        return convertToResponse(updated);
    }

    public void deleteReview(Long id) {
        reviewRepo.deleteById(id);
    }

    public List<ReviewResponse> getReviewsByUserId(Long userId) {

        return reviewRepo.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

       public List<ReviewResponse> getReviewsByBookId(Long bookId) {

        return reviewRepo.findByBookId(bookId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
  private ReviewResponse convertToResponse(Review review)
{
    ReviewResponse response = new ReviewResponse();

    response.setId(review.getId());
    response.setRating(review.getRating());
    response.setComments(review.getComments());
    response.setCreatedAt(review.getCreatedAt());

    if(review.getUser() != null)
    {
        response.setUserId(review.getUser().getId());
        response.setReviewerName(review.getUser().getName());
    }

    if(review.getBook() != null)
    {
        response.setBookId(review.getBook().getId());
        response.setBookName(review.getBook().getBookName());
    }

    return response;
}
}