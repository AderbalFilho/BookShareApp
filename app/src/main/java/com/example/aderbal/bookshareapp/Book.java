package com.example.aderbal.bookshareapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Book {
    public String title;
    public String author;
    public String publisher;
    public int year;
    public int nPages;
    public String category;
    public String key;
    public boolean borrowed = false;
    public String borrowedTo;
    public List<String> solicitations = new ArrayList<>();

    public Book() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Book(String title, String author, String publisher, int year, int nPages, String category) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.nPages = nPages;
        this.category = category;
    }

    public Book(String title, String author, String publisher, int year, int nPages, String category, String bookKey) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.nPages = nPages;
        this.category = category;
        this.key = bookKey;
    }

    public Book(String title, String author, String publisher, int year, int nPages, String category, String bookKey, List<String> solicitations) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.nPages = nPages;
        this.category = category;
        this.key = bookKey;
        this.solicitations = solicitations;
    }
}
