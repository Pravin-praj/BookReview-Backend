/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.dto;

/**
 *
 * @author Pravin Prajapati
 */


public class ReviewRequest {

    private Long userId;
    private int bookId;
    private int rating;
    private String comments;

    public ReviewRequest() {
    }

    public ReviewRequest(Long userId, int bookId, int rating, String comments) {
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.comments = comments;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
    

